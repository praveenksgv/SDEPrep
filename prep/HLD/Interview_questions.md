1. Uber SWE 3 [Interview](https://leetcode.com/discuss/post/8473417/uber-freight-swe3-interview-experience-b-mzfs/) - Distributed URL shortener

```

Round 3 — System Design
Design a distributed URL shortener.

The discussion covered the overall distributed architecture and how the system would work at scale.

Important areas to consider:

URL shortening API
Redirect API
Unique short-code generation
Database/storage
Caching
Distributed ID generation
Read/write scalability
Handling very high redirect traffic
Availability and fault tolerance
Expiration of URLs
Collision handling
Database partitioning/sharding

```




2. Curefit SDE2 Interview(Mine)


```
Our main application, CoreService, and an external system, PartnerService, use separate databases. CoreService needs to maintain a synchronized local copy of a specific, predefined subset of data owned by PartnerService.
The synchronization could follow either a pull-based or push-based approach. Both should be discussed, but the primary focus should be on the pull-based approach because we have limited control over PartnerService and can mainly request data through the interfaces it exposes. What would be the most reliable and efficient design for this synchronization?


- the interviewer was interested in the approaches not the arcitecture diagram etc.
```


3. Design Instagram - Wanderon First Round

4. Design URL Shortner - DigitalOcean
    a. How to handle hot key problem?


5. Design something similar to IRCTC - Teradata First Round

6. Teradata - Second round - [Resource](https://www.youtube.com/watch?v=43tIX7901Gs)
```
Design a service where an internal user calls a REST API to provision a compute cluster (10 VMs + networking) in a customer account. The idea is that user should be able to provision the cluster and it should be able to let say deploy changes to those vms and those vms should be able to talk to other vms in the network. We already have the resources — focus on the networking: how do the VMs talk to each other over a port (private subnet setup)?  What networking configuration is needed so the 10 VMs can communicate? What things are required to create a subnet? Define an address range. Can you define a CIDR table?
```

7. Design a swiggy tracking system - Blink Health R1

```
Design Swiggy's real-time order tracking. After order placed, customer app shows delivery partner moving on map + updated ETA. 200k concurrent deliveries at peak; app refreshes every 5 seconds. Walk me through location ingestion, ETA computation, how updates reach the app.
```

8. Blink Health — System Design Interview(Utkarsh) - R2
```

============================================================
1. ORIGINAL SYSTEM DESIGN PROBLEM (HACKERRANK)
============================================================

Design Feature Flag Evaluation System

Build a feature flag system that supports:

1. Targeting:
   - Enable or disable a feature for a specific user.
   - Enable or disable a feature for a specific tenant.

2. Percentage Rollouts:
   - Enable a feature for a deterministic percentage of users.
   - Example: Enable a feature for 10% of users.

3. Kill Switches:
   - Immediately disable a feature for everyone.
   - Override all targeting and percentage rollout rules.

4. Performance:
   - Support 100,000 feature flag evaluations per second
     on a single application instance.

5. Configuration Propagation:
   - Feature flag configuration changes must propagate
     to application instances within 30 seconds.

Walk through:
- Requirements
- Data model
- Evaluation engine
- High-level architecture
- Design decisions
- Configuration propagation
- Performance and consistency considerations


============================================================
2. REQUIREMENT CLARIFICATION QUESTIONS
============================================================

Q1. What does 100K feature flag evaluations per second mean?
    Does it represent 100K different tenants enabling or
    disabling feature flags, or 100K evaluation requests
    that could come from the same tenant or multiple tenants?

    Interviewer clarification:
    It refers to 100K evaluation requests. Multiple requests
    can originate from the same tenant.

Q2. How would you support enabling or disabling a feature
    for specific users and tenants?

Q3. How would you implement deterministic percentage
    rollouts, such as enabling a feature for 10% of users?

Q4. How would you implement an instant kill switch that
    disables a feature everywhere, overriding all rules?


============================================================
3. HIGH-LEVEL ARCHITECTURE QUESTIONS
============================================================

Q5. What would your high-level design for this feature
    flag evaluation system look like?

Q6. What components would you introduce to separate
    feature flag configuration management from evaluation?

Q7. What exactly is the Data Plane in your architecture?
    Is it a separate service, or does it run within
    the application?

Q8. How would feature flag configuration changes travel
    from the Control Plane to the Data Plane?

Q9. What data structures would you use to support
    high-throughput feature flag evaluations?

Q10. How would you design the system to handle 100K
     evaluations per second on a single application instance?


============================================================
4. PERCENTAGE ROLLOUT FOLLOW-UP QUESTIONS
============================================================

Q11. How exactly would you handle percentage-based rollouts?

     Example:
     A feature flag is enabled for 10% of users.

     How would the system decide whether a particular
     user should receive the feature?

Q12. How would you ensure that the same user consistently
     receives the same feature flag evaluation result?

Q13. How would changing the rollout percentage work?

     Example:
     Initially, a feature is enabled for 10% of users.

     The administrator changes it to 30%.

     How would the system handle this change?

Q14. Would you need to recalculate or update the
     feature flag assignments of all existing users
     when the percentage changes?


============================================================
5. KAFKA, EVENTUAL CONSISTENCY, AND OUTAGES
============================================================

Q15. Suppose we use Kafka to propagate configuration
     updates from the Control Plane to the Data Plane.

     How much eventual consistency can the system tolerate?

Q16. What happens if the Kafka queue becomes overloaded
     with a large number of configuration changes?

     The Data Plane might not receive the latest
     configuration within the required 30 seconds.

     How would you ensure the propagation SLA is met?

Q17. How would you detect that a Data Plane instance
     is lagging behind the latest configuration?

Q18. What would happen during a Kafka outage?

     How would feature flag evaluations continue working?

Q19. How would you handle both Kafka overload and
     Kafka outages while maintaining availability
     and configuration freshness?

Q20. How would you ensure that a kill switch takes
     effect immediately if Kafka is delayed or unavailable?


============================================================
6. FEATURE FLAG DEPENDENCY QUESTIONS
============================================================

Q21. Suppose one feature flag depends on another
     feature flag.

     How would you extend your data model and
     evaluation engine to support dependencies?

     Example:

     F1 = Parent Feature Flag
     F2 = Child Feature Flag

     F2 depends on F1.

     If F1 is disabled, F2 must also be disabled.

Q22. How would dependency evaluation work when a
     feature flag has multiple dependencies?

Q23. Suppose we make an API call to retrieve the
     current value of a child feature flag.

     How would that API work, and what result
     should it return?

Q24. Should dependencies be evaluated when a
     feature flag configuration changes (WRITE time),
     or when someone requests the value of the
     child feature flag (READ time)?

Q25. Consider the following sequence:

     F1 = Parent Feature Flag
     F2 = Child Feature Flag

     F2 depends on F1.

     Step 1:
         POST F1 OFF

     Step 2:
         GET F2

     Expected Result:
         F2 = OFF

     How would you design the system so that
     GET F2 returns OFF after F1 is disabled?

Q26. When the parent feature flag changes,
     should we proactively update all dependent
     child flags, or calculate their effective
     values dynamically during evaluation?

Q27. What are the trade-offs between evaluating
     dependencies on READ versus WRITE?


============================================================
7. MULTI-LEVEL DEPENDENCIES AND CYCLE DETECTION
============================================================

Q28. Suppose feature flag dependencies can extend
     across multiple levels.

     Example:

         F3 -> F2 -> F1

     F3 depends on F2.
     F2 depends on F1.

     How would you support multi-level dependencies?

Q29. What happens if someone creates a dependency
     that introduces a cycle?

     Example:

         F3 -> F2 -> F1
          ^          |
          |__________|

     F3 depends on F2.
     F2 depends on F1.
     F1 depends on F3.

     How would you prevent such cyclic dependencies?

Q30. At what point should cyclic dependency
     validation happen?

     - When creating/updating a feature flag?
     - Or during feature flag evaluation?

Q31. Which algorithm or data structure would you
     use to detect cycles in feature flag dependencies?

Q32. How would you handle an API request that attempts
     to introduce a cyclic dependency?

Q33. How would you ensure that dependency validation
     does not negatively impact the performance
     of the 100K evaluations/second read path?


============================================================
8. INTERVIEW CLOSING
============================================================

Q34. Do you have any questions for us about
     Blink Health, the engineering team, or the role?


============================================================
NOTES
============================================================

- The original problem statement is from the HackerRank
  screenshot shared during the interview.

- The main follow-up discussions focused on:
  1. Control Plane vs Data Plane
  2. Percentage rollout and percentage changes
  3. Kafka propagation and eventual consistency
  4. Kafka overload and outages
  5. Feature flag dependencies
  6. READ-time vs WRITE-time dependency evaluation
  7. Multi-level dependencies
  8. Cyclic dependency detection

- Questions have been cleaned up and organized
  for interview revision.

- Some questions are explicitly stated by the interviewer.
  Others are reconstructed from the discussion to capture
  the technical follow-ups and clarifications.

- No solutions or answers are included.
```
 
9. Company: Blink Health(Utkarsh) - R1
```
==================================================
1. ORIGINAL INTERVIEW QUESTION (FROM SCREENSHOT)
==================================================

Design Email Notification System

Design an email notification service for a SaaS
product.

- 50 million transactional emails/day
  (alerts, password resets, billing).
- Some urgent (sub-second), others bulk (eventual).
- No email may be lost.
- Duplicates must be rare.
- Must keep working when external provider degrades.

Walk me through the architecture.


==================================================
2. INTERVIEWER CLARIFICATIONS
==================================================

During requirement clarification, the interviewer
specified:

1. Do not go into security-specific details.
   Focus on a normal email notification system.

2. Email template management can be discussed
   as part of the design.

3. Duplicate emails must be prevented.
   The interviewer emphasized:
   "There should not be any duplicates."


==================================================
3. HLD FOLLOW-UP QUESTIONS
==================================================

Q1. Why would you choose Kafka over RabbitMQ
    for this email notification system?

Q2. Are we going to use Kafka for both urgent
    and eventual/bulk emails?

Q3. What improvements can be made to the
    proposed high-level design diagram?

Q4. Are there any important areas that require
    a deeper design discussion?

Q5. What is a Provider Router in this
    architecture?


==================================================
4. MAIN DESIGN AREAS DISCUSSED
==================================================

1. Functional and non-functional requirements

2. Core entities and data modeling

3. High-level system architecture

4. Kafka vs RabbitMQ

5. Handling urgent and bulk email queues

6. Email deduplication

7. Provider routing and failover

8. Retry mechanisms and failure handling

9. Transactional outbox and reliability

10. Email template management

11. Scaling to 50 million emails per day


==================================================
5. INTERVIEW IMPLEMENTATION REQUIREMENTS
==================================================

Implement a simplified Email Notification System.

Language: C++14

Storage: In-memory only

Core classes:

1. EmailNotification
2. TemplateManager
3. EmailQueue
4. NotificationService
5. IEmailProvider
6. MockEmailProvider
7. ProviderRouter
8. EmailWorker

Expected functionality:

- Accept email notification requests
- Prevent duplicate requests
- Support versioned templates
- Maintain urgent and bulk queues
- Process emails using workers
- Select an external email provider
- Handle provider failures
- Support retries
- Track notification status
```