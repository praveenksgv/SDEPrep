# Chronus Matching Service
## Functional and Technical Design Document

## 1. Overview

The Chronus Matching Service is a Go-based microservice responsible for calculating compatibility scores between users in mentoring programs.

The primary use case is **Mentee ↔ Mentor matching**. A secondary use case exists for **Mentee ↔ Circle matching**, where a Circle represents a group. The same general matching architecture supports both, although the detailed Circle-matching behavior is not documented here because the exact implementation is no longer fully known.

The service was designed around three important requirements:

- Matching rules should be **configurable per program**, rather than hardcoded inside the matching service.
- Match calculations can become computationally expensive because matching multiple mentees against multiple mentors creates a Cartesian-product workload.
- Matching is an **intermittent workload**, not something requiring continuous computation.

For cost efficiency and elastic compute, the service was therefore implemented in **Go and deployed on AWS Lambda**.

---

## 2. High-Level Functional Model

The primary entities are:

```text
Mentee
Mentor
Circle / Group
```

Matching normally happens between:

```text
Mentee <-> Mentor
```

and in some workflows:

```text
Mentee <-> Circle
```

A program contains users and has an associated **Match Configuration** describing how the two sides should be compared.

For example:

```text
Mentee.city      <-> Mentor.city
Weight: 1.0

Mentee.gender    <-> Mentor.gender
Weight: 0.5
```

The matching engine evaluates the configured field pairs and produces an overall score for a mentor-mentee pair.

---

## 3. Separation Between the Monolith and Matching Service

One of the important architectural decisions was to prevent the matching microservice from owning business concepts such as profile questions.

The existing Chronus monolithic application was responsible for:

```text
Profile questions
Profile answers
Match configurations
Matching-document creation
Matching-document updates
Program/business logic
```

The Go matching service was responsible primarily for:

```text
Loading matching data
Applying the match configuration
Calculating match scores
Handling bulk computation
Writing calculated scores to Redis
Refreshing/invalidation when requested
```

This separation meant that the matching service did not need to know that a particular field meant:

```text
City
Gender
Interest
Industry
Goal
etc.
```

It simply received data describing which fields should be compared.

Conceptually:

```text
              Chronus Monolith
                    |
          +---------+---------+
          |                   |
          v                   v
 Match Configuration    Matching Documents
          |                   |
          +---------+---------+
                    |
                  MySQL
                    |
                    v
            Matching Service
                    |
                    v
             Calculate Score
                    |
                    v
                  Redis
```

This allowed the matching engine to remain comparatively generic and independent of profile-management business logic.

---

## 4. Matching Documents

The matching engine did not perform matching directly by querying and interpreting raw profile-question and profile-answer tables during every calculation.

Instead, the monolithic application generated a **Matching Document** for each user.

There is:

> **One Matching Document per user**

It is not created separately for every match configuration.

A matching document contains the user's profile answers in a JSON representation stored in MySQL. The remembered shape is intentionally simple: conceptually, a row/document is primarily associated with a `user_id` and a JSON payload containing the user's profile answers, without much additional information.

The matching document is **not specific to a Match Configuration**. The same per-user document can be used by the matching engine with the Match Configuration applicable to the user's program.

Conceptually:

```json
{
  "city": "Bangalore",
  "gender": "Male",
  "interest": "Engineering"
}
```

The actual production structure may have been somewhat different, but conceptually the document contained the profile values required by matching.

When a user's profile changed, the **monolith was responsible for updating the Matching Document**.

This design helped keep the matching microservice independent of the profile-question implementation.

An important remembered motivation was that the matching service should not depend on hardcoded profile-question names or on the monolith's profile-question schema. Profile-question names/definitions could change over time. The monolith therefore remained responsible for creating both the Match Configuration and the Matching Document, while the Go service consumed those representations and focused only on matching.

The field/question identifiers or names represented in the Match Configuration correspond to fields represented in the Matching Document. This allows the service to locate the configured values in the JSON rather than embedding business-specific questions in Go code.

Instead of the Go service having business logic such as:

```go
if question.Name == "City" {
    ...
}

if question.Name == "Gender" {
    ...
}
```

the Match Configuration told the service what data from one matching document should be compared with data from another.

This also avoided tightly coupling the matching service to mutable profile-question definitions and the schema used by the monolithic application.

### 4.1 Historical / Final-State Data-Access Note

The remembered evolution is important:

- The broader application data lived in MySQL and included profile questions, profile answers, and Match Configurations.
- In the **final matching design**, the Go matching service was not intended to repeatedly interpret raw profile-question/profile-answer records for score calculation.
- Instead, the monolith generated and updated the per-user Matching Document JSON, and the matching service used that prepared document together with the Match Configuration.
- This separation was intended to keep the matching microservice independent from profile-question business logic and avoid hardcoding mutable question definitions in Go.

---

## 5. Match Configuration

A Match Configuration defines one or more field-pair comparisons between the two entities. A configuration can therefore contain multiple comparisons, each with its own relative weight.

The fields do **not necessarily need to represent the same conceptual question**.

For example:

```text
Mentee.desired_industry
        <->
Mentor.current_industry
```

is valid as long as the Match Configuration explicitly defines that mapping.

Similarly:

```text
Mentee.city
        <->
Mentor.location
```

could be configured.

The matching service therefore does not decide which fields logically belong together. That knowledge exists in the configuration generated/managed by the monolithic application.

---

## 6. Match Configuration and Programs

Chronus contained multiple programs.

Users belong to programs, and a program has a matching configuration that should be used for users participating in that program.

For the documented design, we can consider there to be:

```text
One relevant/default Match Configuration per program
```

When matching users participating in that program, that configuration is used.

The service therefore does not arbitrarily select matching rules; it operates using the configuration associated with the relevant program/workflow.

---

## 7. Weighting Model

Each comparison in the Match Configuration has a weight between:

```text
0 and 1
```

The weights are **relative values**.

They are **not required to sum to 1** or 100%.

For example:

```text
City:
weight = 1.0

Gender:
weight = 0.5
```

Total configured weight:

```text
1.0 + 0.5 = 1.5
```

Therefore the effective contribution is:

```text
City:
1.0 / 1.5
= 66.67%

Gender:
0.5 / 1.5
= 33.33%
```

This makes it possible for administrators to express relative importance without manually normalizing values to exactly 100%.

---

## 8. Match Score Calculation

For the documented example, matching can be considered an **exact match**.

The remembered behavior for this simplified case is whole-value equality rather than substring/contains matching. For example, if one value is the single string `"engineering and football"` and the other value is `"football"`, that should **not** count as an exact match merely because the word `football` appears in both.

A configured criterion therefore produces:

```text
1 -> matching value
0 -> non-matching value
```

A missing field is also considered a non-match and receives:

```text
0
```

The general score can be represented as:

```text
                     Σ(fieldMatch × fieldWeight)
Final Match Score = ------------------------------
                           Σ(fieldWeight)
```

For example:

```text
Configuration

City:
weight = 1.0

Gender:
weight = 0.5
```

Mentee:

```text
City   = Bangalore
Gender = Male
```

Mentor:

```text
City   = Bangalore
Gender = Female
```

Calculation:

```text
City matches:
1 × 1.0 = 1.0

Gender does not match:
0 × 0.5 = 0

Final:
1.0 / 1.5
= 0.6667
= 66.67%
```

If a required value were missing:

```text
City = NULL
```

then that comparison contributes zero rather than being removed from the denominator.

---

## 9. More Advanced Matching Strategies

The source-code structure visible in the provided screenshots contains files such as:

```text
sc_array_x.go
sc_collection_x.go
sc_location_x.go
sc_ordered_array_x.go
sc_set_matching.go
sc_single_choice_x.go
sc_text_x.go
score.go
```

This indicates that the actual production system supported, or was designed to support, different matching strategies depending on field/data type.

However, because the exact behavior of those algorithms is currently not known, this document does not claim specific formulas for them.

For explaining the architecture, exact-match scoring is sufficient.

The important architectural point remains:

> Different field types could be handled by specialized scoring implementations while the overall weighted-score calculation remained configuration driven.

---

## 10. Match Score Storage

After calculation, match scores were stored in **Redis**.

For mentor-mentee matching, the conceptual Redis structure was:

```text
match_score:{mentee_id}:{mentor_id}
```

For example:

```text
match_score:1234:9812
```

with a value such as:

```text
0.82
```

The service therefore had a very inexpensive lookup path once the calculation had completed.

Conceptually:

```text
GET match_score:1234:9812
             |
             v
            0.82
```

Instead of recalculating or performing relational queries every time a score needed to be displayed.

For the purpose of this design, mentee and mentor IDs can be treated as sufficient to uniquely identify a pair.

---

## 11. MySQL vs Redis Responsibilities

The two storage technologies served very different purposes.

### MySQL

MySQL represented the source data required for computation.

The matching service read information such as:

```text
Match Configurations
Matching Documents
Relevant entity/program information
```

from MySQL.

### Redis

Redis stored the calculated match scores to provide extremely fast score retrieval.

The simplified flow was therefore:

```text
               MySQL
                 |
      Matching Documents
      Match Configuration
                 |
                 v
         Matching Service
                 |
           calculate
                 |
                 v
               Redis
                 |
          Match Scores
```

The Go service used:

```text
sqlx.DB
```

for relational database access and:

```text
redis.ClusterClient
```

for Redis access.

These are visible directly in the provided implementation screenshots.

---

## 12. On-Demand Matching

The system did not blindly compute every possible match score continuously.

Scores were generated based on product behavior.

There were several important computation patterns.

### 12.1 Individual Mentee-Mentor Match

Suppose a mentee opens the profile page of one mentor.

The system may need the score for only:

```text
Mentee A <-> Mentor B
```

Rather than calculating scores between the mentee and every mentor unnecessarily, the required pair can be calculated and stored.

Conceptually:

```text
Mentee opens Mentor profile
           |
           v
Need match score
           |
           v
Calculate Mentee A × Mentor B
           |
           v
Redis
```

---

## 13. Mentor Listing Matching

When a mentee opens the mentor-listing experience, the system needs match scores against the available mentors.

For example:

```text
Mentee A

        × Mentor 1
        × Mentor 2
        × Mentor 3
        × ...
```

This can trigger matching between the mentee and the relevant mentor population.

Because this may take some time, computation runs asynchronously.

The web page itself is **not blocked waiting for matching to finish**.

Instead:

```text
Mentor listing requested
          |
          v
Page loads immediately
          |
          +--------------------+
          |                    |
          v                    v
Display mentors          Start matching
with score placeholders       asynchronously
                               |
                               v
                         Write scores
                           to Redis
                               |
                               v
UI polls/checks status --------+
          |
          v
Replace placeholders with scores
```

The user can therefore interact with the page while matching continues in the background.

The UI shows that match scores are being calculated and displays a placeholder until the actual score becomes available.

A polling/asynchronous mechanism checks whether score computation has completed. The page does not wait for the matching calculation before rendering; it shows a placeholder/status indicating that matching is running, and starts using/displaying the score once it becomes available.

---

## 14. Profile Updates and Score Invalidation

A calculated score is dependent on the profile information that existed at the time of calculation.

Therefore, when a user changes their profile, previously calculated scores involving that user may no longer be valid.

The monolithic application updates the user's Matching Document and triggers a **matching refresh request** to the matching service.

The remembered behavior is that existing Redis match-score records affected by that profile change are deleted/invalidated so stale scores are not reused. The exact low-level mechanism used to locate/delete every affected Redis key is not currently confirmed.

Conceptually:

```text
User edits profile
       |
       v
Monolith updates profile
       |
       v
Monolith updates Matching Document
       |
       v
Send refresh request
       |
       v
Matching Service
       |
       v
Invalidate/delete affected Redis score(s)
```

The scores can then be recalculated when required.

This provides an important architectural property:

```text
Invalidate when source data changes
            +
Recompute when required
```

rather than eagerly recalculating the entire matching universe after every individual profile edit.

---

## 15. Admin Views

Chronus also supported the concept of an **Admin View**.

An Admin View is essentially a filtered collection of users.

For example, an administrator might create a view containing:

```text
Users where:
city = Bangalore
```

or perhaps:

```text
Age within a particular range
Program membership satisfies some criteria
Other profile/filter conditions
```

A view therefore represents a dynamically or statically selected population of users.

Administrators could then trigger bulk matching between two Admin Views.

For example:

```text
Admin View A
5,000 mentees

        ×

Admin View B
1,000 mentors
```

produces potentially:

```text
5,000 × 1,000
= 5,000,000
```

mentor-mentee comparisons.

This bulk use case is one of the major reasons the matching service required a scalable computation architecture.

---

## 16. Cartesian-Product Nature of Bulk Matching

At scale, matching is essentially an `M × N` problem.

If there are:

```text
10,000 mentees
1,000 mentors
```

then:

```text
10,000 × 1,000
= 10,000,000 candidate pairs
```

may need to be evaluated.

Each pair then potentially evaluates several configured matching criteria.

Therefore, even though an individual comparison may be inexpensive, the total amount of computation can become significant.

This is the central scaling challenge addressed by the Go/Lambda architecture.

---

## 17. Why AWS Lambda Was Used

Matching was not a continuously active workload.

There could be periods where very little matching was needed, followed by bursts caused by:

```text
Mentor-listing requests
Profile refreshes
Admin bulk matching
Other match calculation workflows
```

Running continuously provisioned matching servers would therefore mean paying for compute resources even when no matching work existed.

AWS Lambda was chosen primarily for **cost efficiency and workload elasticity**.

The architecture allowed Chronus to:

```text
Scale up when matching work exists
+
Avoid continuously running matching compute when idle
```

This is a particularly appropriate serverless use case because the workload is:

```text
intermittent
+
parallelizable
+
compute intensive during bursts
```

---

## 18. AWS Lambda Entry Point

The screenshots show that the matching application uses:

```go
"github.com/aws/aws-lambda-go/events"

runtime "github.com/aws/aws-lambda-go/lambda"
```

and starts using:

```go
runtime.Start(handleRequest)
```

The request handler accepts an API Gateway request and returns an API Gateway response.

Conceptually:

```text
API Gateway
     |
     v
AWS Lambda
     |
     v
handleRequest()
```

The request contains an action, and the service routes the operation according to that action.

The code structure shows a central:

```text
requesthandler
```

package responsible for request processing.

---

## 19. Initialization

During Lambda startup, the service determines the environment using:

```text
MATCHING_ENV
```

and establishes connections to:

```text
MySQL
Redis Cluster
```

The screenshots show global references similar to:

```go
DB                 *sqlx.DB
redisClusterClient *redis.ClusterClient
matchingENV        string
```

The Lambda then initializes its dependencies before accepting requests.

Conceptually:

```text
Lambda starts
    |
    v
Read MATCHING_ENV
    |
    v
Create DB connection
    |
    v
Create Redis client
    |
    v
Start Lambda handler
```

---

## 20. Action-Based Request Handling

The service extracts an action from the incoming request.

The structure visible in the implementation is conceptually:

```go
requestedAction := request.QueryStringParameters[...]
```

followed by behavior similar to:

```text
if action exists
    if trial run
        handle trial run
    else
        RequestHandler.HandleRequest(...)
else
    return error
```

This allowed one Lambda entry point to route different matching operations internally.

---

## 21. Trial Run

The application contains a special **Trial Run** operation.

The purpose of the Trial Run is **not to preview matching quality or experiment with match configurations**.

Instead, it serves as a service/connectivity check.

A trial request verifies that:

```text
Matching service receives requests
MySQL is accessible
Redis is accessible
```

Conceptually:

```text
Trial Request
      |
      v
Lambda reachable?
      |
      v
MySQL reachable?
      |
      v
Redis reachable?
      |
      v
Return trial result
```

This provides a useful way of verifying that the matching stack is functioning end-to-end.

---

## 22. Bulk Matching Architecture

For large Admin View × Admin View jobs, performing all comparisons inside one invocation would create a large execution workload.

The implementation therefore includes explicit workload-splitting and bulk-score modules.

Visible source structure includes:

```text
bulkscores/
    bulk_scores.go
    compute_scores.go

split/
    conversion.go
    split.go
```

The screenshots show a computation based on the number of candidate combinations, with approximately:

```text
(StudentCount × MentorCount) / 800,000 + 1
```

used in the bulk-score implementation.

This appears to determine how a very large matching operation should be divided across multiple Lambda executions.

For example:

```text
10,000 students
×
1,000 mentors

= 10,000,000 combinations
```

would result in approximately:

```text
10,000,000 / 800,000 + 1
≈ 13
```

parallel work units/Lambda invocations.

The screenshots strongly support this behavior, although the exact operational reasoning behind the `800,000` threshold is not currently remembered.

Therefore, this should be treated as an **implementation-derived threshold**, rather than claiming that it was selected through a particular benchmark unless that is later confirmed.

---

## 23. Multi-Level Work Splitting

The source indicates more than one level of batching/parallelization.

One visible constant is:

```text
MaxBatchesSplit = 6
```

inside the compute-score implementation.

The screenshots indicate that work assigned to an individual computation can be further divided into batches.

Therefore the architecture appears approximately as:

```text
Large Bulk Request
        |
        v
Outer workload partition
        |
   +----+----+----+
   |         |    |
Lambda     Lambda Lambda
   |         |    |
   v         v    v
Internal batches
```

The exact semantics of these six batches are not currently confirmed.

The fact that `MaxBatchesSplit = 6` existed is preserved here, while avoiding claims about exactly why six was chosen or precisely how every batch was executed until the implementation is re-reviewed.

---

## 24. Go Concurrency

The screenshots also show that the service uses Go concurrency primitives including:

```go
sync.WaitGroup
sync.Map
```

and goroutines.

A worker limit named approximately:

```text
MaxWorkerLimit
```

is visible in the implementation.

Therefore the system had two broad forms of parallelism/concurrency:

```text
Inter-Lambda parallelism
    Multiple AWS Lambda invocations

Intra-Lambda concurrency
    Go goroutines/workers inside an invocation
```

The Go runtime can schedule goroutines across available operating-system threads and CPU resources.

This means the design used lightweight Go concurrency internally while using Lambda fan-out for larger-scale horizontal parallelism.

---

## 25. Additional Parallel-Run Thresholds

The screenshots also show configuration similar to:

```text
Student-Mentor:
ParallelRuns = EntityCount / 25,000 + 1

Student-Circle:
ParallelRuns = EntityCount / 1,000 + 1
```

These values indicate additional matching-specific partitioning behavior.

However, their exact relationship with:

```text
800,000 candidate-combination threshold

MaxBatchesSplit = 6

MaxWorkerLimit
```

has not yet been fully reconstructed.

For accuracy, the document records these values as implementation details without inventing the exact orchestration logic.

This is one of the few parts worth revisiting directly from the source code if the code becomes available.

---

## 26. Internal Code Organization

The repository structure visible in the screenshots shows a deliberately modular design.

### `abstractmatching`

Contains abstractions used by matching algorithms.

Visible files include:

```text
abstract_matching.go
strategy.go
```

This suggests separation between the orchestration layer and concrete matching strategies.

### `abstractmatchingdocument`

Contains abstractions around matchable data fields.

Visible example:

```text
data_field.go
```

### `bulkscores`

Responsible for large-scale match-score calculation.

```text
bulk_scores.go
compute_scores.go
```

### `config`

Contains matching/scoring configuration and data-type definitions.

Visible files include:

```text
config.go
data_types.go
match_configs.go

sc_array_x.go
sc_base.go
sc_collection_x.go
sc_location_x.go
sc_ordered_array_x.go
sc_set_matching_x_single_choice.go
sc_set_matching.go
sc_single_choice_x.go
sc_text_x.go
score.go
```

### `entity`

Represents the entities participating in matching.

```text
primary_entity.go
secondary_entity.go
```

Although the business terminology is mentee and mentor, generic primary/secondary abstractions help avoid tightly binding the engine to only one entity combination.

### `env`

Contains environment/runtime setup.

```text
data.go
init.go
```

### `matchdetails`

Contains match-related result/details behavior.

```text
match_details.go
```

### `matchingdocument`

Contains matching-document representations.

Visible files include:

```text
primary_entity_data.go
student_data.go
students_data.go
```

### `matchsetting`

Contains matching runtime/settings logic.

```text
match_setting.go
```

### `persistence`

Handles persistence-related operations.

Visible files include:

```text
context.go
conversion.go
keys.go
match_score_store.go
stale_score_store.go
```

The presence of:

```text
match_score_store.go
stale_score_store.go
```

indicates explicit handling of calculated and stale/invalidation-related matching state.

The exact implementation of stale-score tracking is not currently documented.

### `playarea`

Contains what appears to be supporting/testing/experimental matching behavior.

### `refresher`

Contains refresh workflows.

```text
delta_refresh.go
errors.go
full_refresh.go
refresher.go
student_score_refresher.go
```

This strongly indicates that the service supported multiple refresh scopes, including:

```text
full refresh
delta/incremental refresh
individual student/user score refresh
```

The exact trigger semantics for every refresh type require source-code verification, but profile changes are confirmed to trigger a matching refresh.

### `requesthandler`

Contains entry points for different operational requests.

```text
bulk_refresher.go
request_handler.go
user_refresher.go
```

This corresponds well with the functional distinction between:

```text
bulk matching
individual/user matching
```

### `secrets`

Handles secret-related infrastructure.

### `split`

Contains workload splitting/concurrency logic.

```text
conversion.go
split.go
```

### `studentcirclematching`

Contains mentee/student-to-circle matching.

```text
student_circle_matching.go
```

### `studentmentormatching`

Contains mentor-mentee matching.

```text
student_mentor_matching.go
```

---

## 27. Background Computation Model

An important architectural property is that expensive matching work does not block the user-facing HTTP request.

For example, when matching an entire mentor list:

```text
HTTP/UI request
       |
       v
Trigger computation
       |
       +-----------------------------+
       |                             |
       v                             v
Return/render UI            Matching continues
                                   |
                                   v
                              Store results
                                in Redis
                                   |
                                   v
                              UI polls
```

This prevents long-running score calculation from causing the user-facing request to remain open for the entire computation.

---

## 28. Idempotency

Match-score writes are naturally idempotent for a given mentee-mentor pair.

If two computations happen simultaneously:

```text
Worker A:
SET match_score:100:200 = 0.82

Worker B:
SET match_score:100:200 = 0.82
```

the later Redis `SET` overwrites the value for the same key.

Because score generation for the same input/configuration should produce the same result, duplicate execution does not create duplicate match-score records.

Therefore the computation can safely tolerate duplicate execution at the Redis-write level.

---

## 29. Effective End-to-End Flow

The complete mentor-mentee flow can now be represented as:

```text
                  CHRONUS MONOLITH
                         |
          +--------------+---------------+
          |                              |
          v                              v
    Profile Management             Match Configuration
          |
          v
Generate / Update
Matching Document
          |
          +--------------+---------------+
                         |
                       MySQL
                         |
                         v
                 Matching Service
                   Go / Lambda
                         |
              +----------+----------+
              |                     |
              v                     v
        Read documents        Read configuration
              |                     |
              +----------+----------+
                         |
                         v
                Compare configured
                   field pairs
                         |
                         v
                Apply relative
                    weights
                         |
                         v
                 Final Match Score
                         |
                         v
                       Redis
                         |
                         v
               Chronus Application
                         |
                         v
             Display Match Percentage
```

---

## 30. Profile-Update Flow

```text
User changes profile
       |
       v
Chronus Monolith
       |
       +--> update profile
       |
       +--> regenerate/update
            Matching Document
       |
       +--> issue matching
            refresh request
                   |
                   v
            Matching Service
                   |
                   v
        invalidate/delete stale
            Redis scores
                   |
                   v
         Scores recomputed when
               required
```

---

## 31. Bulk Admin-Matching Flow

```text
Admin View A
Mentees
     |
     |       Admin selects matching
     |               against
     v
Admin View B
Mentors
     |
     v
Determine M × N workload
     |
     v
Matching Lambda
     |
     v
Split large workload
     |
 +---+---+---+---+---+
 |       |       |   |
 v       v       v   v
λ1      λ2      λ3  ...
 |       |       |
 v       v       v
Go workers / batches
 |       |       |
 +-------+-------+
         |
         v
     Match scores
         |
         v
       Redis
```

---

## 32. Important Engineering Characteristics

| Concern | Design |
|---|---|
| Matching rules | Configuration-driven |
| Profile representation | Pre-generated Matching Document JSON |
| Business/profile management | Chronus monolith |
| Matching computation | Dedicated Go service |
| Persistent/source data | MySQL |
| Match-score retrieval | Redis |
| Infrastructure | AWS Lambda |
| Large workloads | Partitioned/batched computation |
| Internal concurrency | Go goroutines |
| UI behavior | Asynchronous calculation + polling |
| Profile changes | Refresh/invalidate stale scores |
| Duplicate calculation | Idempotent Redis overwrite |
| Bulk matching | Cartesian product of selected Admin Views |
| Health/connectivity verification | Trial Run |

---

## 33. Why This Architecture Was Useful

The architecture solves several problems simultaneously.

### 33.1 Independence from Profile Schema

The matching service does not need to embed Chronus-specific questions or field names in its Go source.

The monolith constructs:

```text
Matching Document
+
Match Configuration
```

and the matching service interprets them.

This reduces coupling between profile management and score computation.

### 33.2 Efficient Repeated Score Access

Once calculated:

```text
mentee × mentor -> score
```

can be read directly from Redis.

### 33.3 Avoid Unnecessary Computation

Scores can be generated based on actual usage rather than maintaining every possible pair continuously.

### 33.4 Handle Large Bulk Workloads

Admin-initiated many-to-many matching can be partitioned into smaller computational units.

### 33.5 Cost-Efficient Infrastructure

Lambda avoids paying continuously for a service whose compute workload is intermittent.

### 33.6 Responsive Product Experience

Long calculations do not prevent mentor-listing pages from rendering; scores can appear asynchronously.

---

## 34. Areas That Are Intentionally Not Fully Specified

There are several details for which the screenshots show implementation hints but there is not enough remembered information to document exact semantics reliably.

These include:

```text
Exact behavior of each sc_* matching algorithm

Exact matching-document JSON schema

Exact stale_score_store implementation

Detailed full_refresh vs delta_refresh behavior

Reason the ~800,000 threshold was selected

Exact behavior of MaxBatchesSplit = 6

Exact relationship between:
    800,000 combination splitting
    ParallelRuns
    MaxBatchesSplit
    MaxWorkerLimit

Detailed Mentee-Circle score model

Exact failure/retry handling for a partially failed bulk operation

Whether match scores had any additional durable persistence
outside Redis
```

These should remain marked as **implementation details to verify from source**, rather than guessed.

---

## 35. Short Interview Explanation

A concise interview explanation of the project could be:

> At Chronus, I worked on a Go-based matching microservice that calculated compatibility scores between mentees and mentors. Matching was configuration driven: for each program, an administrator could configure which mentor and mentee fields should be compared and assign relative weights to those comparisons.
>
> We intentionally kept profile-management logic outside the service. Our monolithic application generated a JSON matching document for each user and maintained the match configurations in MySQL. The matching service loaded those documents and configurations, calculated a weighted score, and stored the resulting mentee-mentor scores in Redis for fast retrieval.
>
> Matching could happen at different scales—from a single mentor-mentee pair, to one mentee against an entire mentor list, to bulk matching between two filtered admin views. Large jobs create an M×N computation problem, so the service partitioned the workload across AWS Lambda executions and also used Go concurrency internally.
>
> We chose Lambda because matching was a bursty workload rather than something running continuously, so serverless infrastructure gave us both scalability and better cost efficiency. User-facing flows were asynchronous: the page could load with placeholders while matching ran in the background, and the UI polled until scores became available. Profile changes triggered refresh/invalidation so outdated Redis scores would not continue to be used.

---

## 36. Documentation Status

The following areas are currently well understood and documented:

- Functional purpose of the matching service
- Mentor, mentee, and circle concepts
- Match Configuration
- Matching Document concept
- Responsibility split between monolith and matching service
- Weighted scoring model
- Missing-field behavior
- MySQL and Redis responsibilities
- On-demand pair matching
- Mentor-listing matching
- Background/asynchronous computation
- UI placeholder + polling behavior
- Profile-change refresh/invalidation
- Admin Views
- Bulk Admin View × Admin View matching
- Cartesian-product scaling problem
- AWS Lambda rationale
- API Gateway/Lambda entry point
- Trial-run connectivity check
- Bulk-work splitting
- Go goroutine concurrency
- Redis write idempotency
- Repository/package structure

The main area that remains partially reconstructed is the **low-level parallelization and refresh implementation**, especially the exact interaction among:

```text
800,000 combinations-per-work-unit threshold
MaxBatchesSplit = 6
ParallelRuns
MaxWorkerLimit
full_refresh
delta_refresh
stale_score_store
```

If the source files become available later, these details can be reconstructed directly from code without changing the functional design documented above.


---

## 37. Confirmed Detail Ledger (Preserved From Project Discussion)

This section intentionally repeats the most important remembered facts in compact form so that they are not lost if the implementation is revisited later.

### 37.1 Product and Domain

- The service was created at **Chronus** and implemented in **Go (Golang)**.
- Its main purpose is to calculate match scores between **mentees and mentors** based on configurable profile-field comparisons.
- A secondary entity called a **Circle** represents a group; matching can also happen between a mentee and a Circle, but detailed Circle behavior is intentionally not asserted because it is not fully remembered.
- An organization/program can have a **Match Configuration** defining which field on one side maps to which field on the other side.
- A Match Configuration can contain **multiple field pairs**.
- The two mapped fields do not need to be semantically identical fields; for example, a mentee field can map to a differently named mentor field as explicitly configured.
- Multiple programs existed. The remembered model is that the relevant/default Match Configuration for a program is used for users in that program.

### 37.2 Matching Documents

- Matching Documents were **generated and updated by the monolithic application**, not by the Go matching service.
- There is **one Matching Document per user**, not one document per user per Match Configuration.
- The document contains the user's profile answers as JSON in MySQL.
- The remembered storage shape is essentially a `user_id` plus the JSON document, with little additional information.
- In the final design, the matching service operated on these Matching Documents instead of repeatedly interpreting raw profile-question/profile-answer records.
- A major remembered reason for introducing Matching Documents was to keep the matching service independent from mutable profile-question definitions/names and avoid hardcoding profile questions in Go.
- The Match Configuration and Matching Document use corresponding field/question references so the service can locate values in the JSON and compare them.
- Match Configurations and Matching Documents were owned/maintained by the monolith; the matching microservice focused on matching.

### 37.3 Scoring

- For the simplified documented behavior, matching is treated as **exact matching**.
- Exact matching does **not** mean substring/contains matching. A value like `"engineering and football"` does not exactly match `"football"`.
- Each comparison has a weight in the range **0 to 1**.
- Weights are **relative** and do **not** need to sum to `1`.
- Example: weights `1.0` and `0.5` contribute approximately `66.67%` and `33.33%` of the total score respectively.
- A missing field contributes **0** for that comparison.
- The simplified weighted formula is:

```text
                     Σ(fieldMatch × fieldWeight)
Final Match Score = ------------------------------
                           Σ(fieldWeight)
```

### 37.4 MySQL and Redis

- The matching service reads source data/configuration from **MySQL**.
- Earlier/raw application data included profile questions, profile answers, and Match Configurations; the final matching path used the prepared Matching Document JSON plus Match Configuration.
- Calculated match scores are written to **Redis** for fast retrieval.
- The remembered mentor-mentee Redis key format is:

```text
match_score:{mentee_id}:{mentor_id}
```

- For the documented model, the mentee/mentor pair can be treated as uniquely identifying a score.
- If the same pair is calculated concurrently more than once, Redis `SET` overwrites the same key; this makes duplicate score writes effectively idempotent for the same inputs.

### 37.5 Triggering and Refresh Behavior

- Opening a particular mentor's page while acting as a mentee can trigger matching for that **specific mentee-mentor pair**.
- Opening the **mentor listing page** can trigger matching between that mentee and the relevant/all mentors.
- An administrator can trigger **bulk matching** between two Admin Views.
- When a profile is edited, the monolith updates the Matching Document and sends a **matching refresh request** to the matching service.
- Existing affected Redis score records are deleted/invalidated so stale scores are not reused.
- The exact Redis-key discovery/deletion mechanism is not currently remembered and should be verified from source if needed.

### 37.6 Admin Views and Bulk Matching

- An **Admin View** is a list/set of users selected using filters.
- Example filters can include city (for example, users in Bangalore), age, and other user/profile conditions.
- Bulk matching between two Admin Views can be treated as a Cartesian product of the mentee-side users and mentor-side users.
- Example: `5,000 mentees × 1,000 mentors = 5,000,000` candidate pairs.

### 37.7 Asynchronous UI Behavior

- Large/list matching runs in the **background**.
- The UI does **not** block waiting for all scores to finish.
- The page loads while the matching service is still calculating.
- The UI shows a placeholder/status where the match score will eventually appear.
- A polling/asynchronous process checks for score availability/completion.
- Once a score becomes available, the UI starts using/displaying it.

### 37.8 AWS Lambda and Cost Rationale

- The Go matching service was deployed on **AWS Lambda**.
- A key reason was **cost**: matching was not used continuously, so keeping dedicated compute running all the time would be wasteful.
- Lambda allowed the service to consume compute primarily when matching work existed.
- The implementation uses AWS Lambda/API Gateway request types and `runtime.Start(handleRequest)`.

### 37.9 Trial Run

- `trial run` is **not** a scoring-preview feature.
- Its purpose is to check that:
  - a request can reach the matching service,
  - the service can access MySQL,
  - the service can access Redis.

### 37.10 Implementation Details Visible in the Screenshots

The source screenshots preserve the following implementation clues:

```text
bulkscores/
    bulk_scores.go
    compute_scores.go

split/
    conversion.go
    split.go

refresher/
    delta_refresh.go
    full_refresh.go
    refresher.go
    student_score_refresher.go

requesthandler/
    bulk_refresher.go
    request_handler.go
    user_refresher.go

persistence/
    match_score_store.go
    stale_score_store.go
```

Additional visible details include:

```text
Approximate bulk split formula:
(StudentCount × MentorCount) / 800,000 + 1

MaxBatchesSplit = 6

Student-Mentor:
ParallelRuns = EntityCount / 25,000 + 1

Student-Circle:
ParallelRuns = EntityCount / 1,000 + 1

Go concurrency primitives:
sync.WaitGroup
sync.Map
goroutines
MaxWorkerLimit
```

These values are preserved because they were visible in the implementation screenshots. Their exact orchestration relationship and the rationale behind each threshold are still intentionally marked as **to be verified from source** rather than guessed.

### 37.11 Known Unknowns — Do Not Invent

The following details remain uncertain and should not be reconstructed from memory as facts:

- Exact production Matching Document JSON schema beyond the one-user / JSON-payload concept.
- Exact behavior/formula of every `sc_*` scoring strategy.
- Exact implementation of `stale_score_store`.
- Exact difference and triggers between `full_refresh` and `delta_refresh`.
- Exact reason `800,000` was chosen.
- Exact reason `MaxBatchesSplit` is `6`.
- Exact relationship among `800,000`, `ParallelRuns`, `MaxBatchesSplit`, and `MaxWorkerLimit`.
- Exact failure/retry semantics when one partition of a bulk job fails.
- Detailed Mentee-Circle scoring behavior.
- Whether match scores were also durably persisted anywhere besides Redis.
