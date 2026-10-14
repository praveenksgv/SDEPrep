# HiLabs Assessment — Complete Sequence with Previously Given Answers

**Interview-study reconstruction, not an official answer key.** This combines the original photographed questions and the prior answers in this chat, maintaining the three numbered sections and two coding slots.

**How to read answers:**
- **Explicit earlier assistant reply** means that exact option number and chosen answer can be seen in earlier assistant messages in this conversation. This records *what ChatGPT answered then*, not confirmation that the platform graded it correct.
- **Screenshot-based study answer** means an answer was inferred/reconstructed from the photographed question or from the prior study document; the exact previous reply is unavailable. A number with **(tentative)** is **not reliable** as a historic selection.
- **Study answer** means a technical answer concept is supplied for interview preparation but no historical option number can be verified.
- **Unresolved/Missing** means the record is insufficient to state a definite selection. **Do not memorize a guess as an assessment answer.**

## Rapid-review answer key (all 52 MCQ slots)

### Section 1 — Core CS / Java / Algorithms (17)

| Q | Option previously given / mapped | Answer to study | Evidence |
|---:|---|---|---|
| 1 | — | JWT signature verification using trusted public key | Study answer |
| 2 | — | std::unique_ptr is noncopyable; use std::move for ownership transfer | Study answer |
| 3 | — | WHERE filters rows before grouping; HAVING filters aggregated groups | Study answer |
| 4 | — | Phaser (reusable, multi-phase thread synchronization) | Study answer |
| 5 | — | Intrinsic lock synchronized(this) protects coupled cache reads/writes and visibility | Study answer |
| 6 | 4 (likely) | RED method — Rate, Errors, Duration | Screenshot-based study answer |
| 7 | 1 | Use local variables instead of shared mutable instance fields where possible | Screenshot-based study answer |
| 8 | 3 | Insecure Direct Object Reference (IDOR / BOLA) | Screenshot-based study answer |
| 9 | — | result = [SALES, TECH]; shared captured ArrayList is nondeterministic/unsafe | Study answer |
| 10 | — | Correct LCS: X[i-1] == Y[j-1]; C[i-1][j-1]+1 else max(C[i-1][j], C[i][j-1]) | Study answer |
| 11 | — | Merge overlapping intervals by min(start) and max(end), preserving sort order | Study answer — exact value unknown |
| 12 | — | Encoded key with safe column offset and multiplier > value range | Study answer |
| 13 | — | Equal popcounts do not imply equivalent task masks or future legal transitions | Study answer |
| 14 | — | Unary token '~' not in operator set, making unary branch unreachable | Study answer |
| 15 | — | Modified binary search for rotated array minimum/pivot, O(log n) | Study answer |
| 16 | — | O(n log n) | Study answer |
| 17 | — | Exact logical flaw unverified; review digit-DP state, leading zeros and previous-digit handling | Unresolved |

### Section 2 — AI / LLM (17)

| Q | Option previously given / mapped | Answer to study | Evidence |
|---:|---|---|---|
| 1 | — | Subword tokenization, e.g. BPE / WordPiece | Study answer |
| 2 | — | Not recoverable | Missing |
| 3 | 3 (visible choice, tentative) | 5.60% misclassified (0.88×5% + 0.12×10%) | Screenshot-based study answer |
| 4 | 4 (tentative) | Long Short-Term Memory (LSTM) | Screenshot-based study answer |
| 5 | — | Chain-of-thought / step-by-step prompting | Study answer |
| 6 | — | Independent validation worsens despite training improvements: overfitting, poor generalization | Study answer |
| 7 | 2 (tentative) | High feature dimensionality and too few examples; more representative data reduces variance | Screenshot-based study answer |
| 8 | 3 (tentative) | More parameters can capture richer patterns but increase memory/compute | Screenshot-based study answer |
| 9 | 4 | Quantifies parameter contribution to error via gradients | Screenshot-based study answer |
| 10 | 4 | Semantic similarity is captured numerically using embeddings/cosine similarity | Screenshot-based study answer |
| 11 | 2 only IF event is X=3 | Cannot establish answer without missing probability event; if P(X=3), 0.2668 | Conditional study answer |
| 12 | 3 | Preserve all three RGB color channels | Screenshot-based study answer |
| 13 | — | Model with largest training/development versus validation gap | Study principle, not final model |
| 14 | 2 | Progressively minimize the optimization objective | Screenshot-based study answer |
| 15 | 2 | Approximately [0.05, 0.13] (95% CI for 18/200) | Screenshot-based study answer |
| 16 | 4 | Median = 17 | Screenshot-based study answer |
| 17 | — | Not recoverable | Missing |

### Section 3 — Area Tracks / SQL & Databases (18 recalled, only 17 recovered)

| Q | Option previously given / mapped | Answer to study | Evidence |
|---:|---|---|---|
| 1 | — | CREATE PROCEDURE ... @Genre NVARCHAR(...) ... SELECT ... WHERE Genre = @Genre | Study answer |
| 2 | 3 | Attribute closure confirms a set can determine all attributes (plus minimality for candidate key) | Screenshot-based study answer |
| 3 | 3 | Outer join includes unmatched tuples; inner join retains matching tuples | Explicit earlier assistant reply |
| 4 | 3 | Evaluate computed field for each row before aggregating AVG etc. | Explicit earlier assistant reply |
| 5 | 1 | They dictate the refresh strategy of materialized views | Explicit earlier assistant reply |
| 6 | 1 | JOIN Employees e to Employees m ON e.manager_id = m.emp_id | Explicit earlier assistant reply |
| 7 | 3 | WAL ensures integrity by recording changes before they are applied | Explicit earlier assistant reply |
| 8 | 1 | IF SQL%NOTFOUND immediately after UPDATE | Explicit earlier assistant reply |
| 9 | 4 | Aggregated join view is read-only; use INSTEAD OF trigger for controlled writes | Explicit earlier assistant reply |
| 10 | 3 | INCLUDE columns do not count toward index key-size / count limits | Explicit earlier assistant reply |
| 11 | 3 | SUM(...) OVER (PARTITION BY Category ORDER BY OrderDate) | Explicit earlier assistant reply |
| 12 | 4 | Use same data type and join distribution keys for both MPP tables | Explicit earlier assistant reply |
| 13 | 2 | Starvation from higher-priority arrivals bypassing older runnable jobs | Explicit earlier assistant reply |
| 14 | 4 | Local index partitions drop with table; global index may need repair | Explicit earlier assistant reply |
| 15 | 4 | Independent multivalued dependencies: split MovieRegion and MovieLanguage (4NF) | Explicit earlier assistant reply |
| 16 | 4 | Rewrite correlated query as appropriate EXISTS/IN/semi-join/decorrelation | Explicit earlier assistant reply |
| 17 | 2 | Full recovery model / Oracle ARCHIVELOG for log-based point-in-time recovery | Explicit earlier assistant reply |
| 18 | — | Not recoverable | Missing |

**Coding tasks:** Coding 1 = **Target Sum**, sign-assignment count with subset-sum DP (fully documented below). Coding 2 = **not recovered** from the accessible photos.

## Counts and certainty

- **52 MCQ positions:** 49 identified MCQ topics, 3 unconfirmed slots (AI 2, AI 17, Area Tracks 18).
- **15 explicit previous answer messages with numbered selections** are available in the conversation for Database Questions **3–17**.
- **34 other identified MCQ topics** have a study answer or an explicit uncertainty marker, but their exact historic assistant responses are not all visible.
- **2 coding positions:** 1 statement and C++ solution recovered, 1 missing.
- **54 total reported assessment positions.**

---

## Full question sequence and detailed responses

# Original assessment questions, screenshot-derived choices and explanations

**Source:** The photographs and follow-up discussion in this project conversation. **Purpose:** Revise the questions for the upcoming interview, not to recreate an official question bank.

## Reading this document

- The user recalls **three MCQ sections containing 17 + 17 + 18 = 52 slots**, followed by **two coding problems** (total **54 slots**). This file lists **every one of those 54 positions** to avoid silently dropping a question. The screenshot section headings suggest the order **Core CS**, **AI/LLM Fundamentals**, **Area Tracks (SQL/database)**. The number of items in the last section should be rechecked against the assessment because the available Area Tracks photographs visibly reach **Question 17**, not 18.
- **[Seen]** = the question/topic was visible in a screenshot in the thread. **[Partial]** = wording, answer options or images were cut off/blurred; text is a close *summary* rather than a claimed verbatim transcription. **[Missing]** = no readable screenshot of that particular slot could be confirmed. **[Explainer]** = model-supplied study answer, **not an official grading key**.
- Most original images have diagonal perspective, glare, watermarks, or horizontal scrolling. When the exact four options were not legible, the file **does not invent four original options**. For clearly readable options, they are recorded in order.
- The files reviewed include 78 accessible original image photographs and the messages' image previews; some show the same question in two photos. Earlier answers in this conversation occasionally gave an option that was not present; this file preferentially uses the visible question/option text and calls out uncertainty.

## Count at a glance

| Sequence | Topic/section | Slots | Observed coverage |
|---|---|---:|---|
| 1 | Core CS / Java / Algorithms | 17 | 17 question topics visible, some options incomplete |
| 2 | AI / LLM Fundamentals | 17 | 15 question topics visible; Q2 and Q17 not confidently recovered |
| 3 | Area Tracks / SQL & Databases | 18 | 17 question topics visible; Q18 not confidently recovered |
| 4 | Coding | 2 | Target Sum visible; second problem not confidently recovered |
| **Total** | | **54** | **50 identifiable question/problem statements or topics, 4 unconfirmed slots** |

---

# SECTION 1 — Core CS / Java / Algorithms (Questions 1–17)

### 1. JWT authentication middleware — [Seen, partial wording/options]

**ANSWER TO STUDY:** JWT signature verification using trusted public key.  
**Evidence:** Study answer. **Note:** Original option number unavailable.
**Question (faithful summary):** A service is replacing an existing JSON-token authentication flow with a JWT-based one. A middleware function extracts a token from an `Authorization` header, checks its prefix, and validates it using a public key and claims structure. Which operation ensures that the incoming token has not been tampered with? The Go code includes a call shaped like `VerifyToken(token, appPublicKey, claims)` and returns an authorization error on verification failure.

**Answer / interview explanation [Explainer]:** JWT **cryptographic signature verification**, with the expected algorithm and trusted verification key, establishes integrity. **Claims validation** additionally checks expiration, issuer, audience, etc. Simply decoding the JWT is not verification. In asymmetric signing the public key verifies a signature made with the private key. Original option text/number not reliably readable.

### 2. C++ `std::unique_ptr` compilation failure — [Seen, partial options]

**ANSWER TO STUDY:** std::unique_ptr is noncopyable; use std::move for ownership transfer.  
**Evidence:** Study answer. **Note:** Original option number unavailable.
**Question (summary):** A C++ developer sees a compilation error while attempting to duplicate ownership of a performance-critical resource:

```cpp
std::unique_ptr<int> p1(new int(10));
std::unique_ptr<int> p2 = p1; // compilation failure
```

Which C++ feature or language-level property intentionally causes the failure?

**Answer [Explainer]:** A `unique_ptr` is **move-only**; its copy constructor and copy assignment are deleted to enforce exclusive ownership. Transfer it with `auto p2 = std::move(p1);` (then `p1` becomes empty). Not the same as `shared_ptr`.

### 3. SQL grouping and filtering (`WHERE` versus `HAVING`) — [Seen, partial options]

**ANSWER TO STUDY:** WHERE filters rows before grouping; HAVING filters aggregated groups.  
**Evidence:** Study answer. **Note:** Original option number unavailable.
**Question (summary):** An SQL analytics query needs to group records, compute aggregates such as `COUNT` or `AVG`, and filter groups/individual records. Which statement correctly explains `GROUP BY`, `WHERE`, and `HAVING` or their evaluation order?

**Answer [Explainer]:** `WHERE` filters rows **before** grouping; `GROUP BY` forms groups; aggregate functions compute group values; `HAVING` filters **groups after** aggregation. Example:

```sql
SELECT department_id, COUNT(*) AS n
FROM employees
WHERE active = 1
GROUP BY department_id
HAVING COUNT(*) >= 5;
```

### 4. Thread synchronization across repeated phases — [Seen]

**ANSWER TO STUDY:** Phaser (reusable, multi-phase thread synchronization).  
**Evidence:** Study answer. **Note:** Original option order not reliably recovered.
**Question (summary):** A complex task is split into multiple phases, each involving several threads. Which synchronization aid is most appropriate when threads must repeatedly wait for others before advancing to the next phase?

**Options visible by name:** Phaser; Semaphore; CountDownLatch; CyclicBarrier (exact order of choices partly blurred).

**Answer [Explainer]:** **`Phaser`** is designed for repeated, potentially dynamic phased synchronization and can register/deregister parties. `CyclicBarrier` also handles reusable fixed-party barriers, but `Phaser` best fits dynamic multiple phases.

### 5. Java intrinsic locking / cached prime factors — [Seen]

**ANSWER TO STUDY:** Intrinsic lock synchronized(this) protects coupled cache reads/writes and visibility.  
**Evidence:** Study answer. **Note:** Exact previously selected option number not available.
**Question (summary):** A multi-threaded financial tool caches the prime factors of the most recently requested number. Previously, concurrent requests returned inconsistent results; now the code synchronizes both cache reads and updates on `this`.

```java
public List<Integer> findPrimeFactors(int num) {
    synchronized (this) {
        if (numCache.get() == num)
            return new ArrayList<>(primeFactorCache.get());
    }
    List<Integer> factors = new ArrayList<>();
    for (int i = 2; i <= num; i++) {
        if (isPrime(i) && num % i == 0) factors.add(i);
    }
    synchronized (this) {
        numCache.set(num);
        primeFactorCache.set(new ArrayList<>(factors));
    }
    return factors;
}
```

**Question:** Why does this restore correctness under parallel load?

**Answer [Explainer]:** Using the **same intrinsic lock** for readers and writers makes the check / paired state update atomic from other synchronized callers' perspectives and provides the Java **happens-before / memory-visibility guarantee**. It does not make factorization itself parallel. Copying the list prevents exposing a mutable cache reference. **Choose the option about synchronized shared cache state and visibility, not only slower execution.**

### 6. Site Reliability Engineering / RED monitoring metrics — [Seen]

**ANSWER TO STUDY:** **4 (likely)** — RED method — Rate, Errors, Duration.  
**Evidence:** Screenshot-based study answer. **Note:** Prior message with explicit option not available.
**Question (summary):** A new REST API needs monitoring. The team tracks (1) request volume/rate, (2) percentage of requests that fail, and (3) response-time latency including p95/p99. Which named monitoring method is this?

**Options (visible):** The Four Golden Signals; API metrics; USE method; **RED method (Rate, Errors, Duration)**.

**Answer [Explainer]:** **RED method (Rate, Errors, Duration)**, likely **option 4**. Do not confuse it with SRE’s related **four Golden Signals** (Latency, Traffic, Errors, Saturation), which add saturation. The three requested measurements align precisely with RED.

### 7. Thread-safe class design — [Seen]

**ANSWER TO STUDY:** **1** — Use local variables instead of shared mutable instance fields where possible.  
**Evidence:** Screenshot-based study answer. **Note:** Exact prior answer reply not available.
**Question:** You are designing a class accessed by multiple threads. Which practice helps ensure thread safety?

**Options (readable):**
1. Use local variables instead of instance variables within methods.
2. Allow threads to modify shared data without synchronization.
3. Avoid using synchronized methods or blocks.
4. Use static variables to minimize the overhead of synchronization.

**Answer [Explainer]:** **Option 1**. Local variables are per-invocation; mutable shared fields require coordination. A local variable referencing shared mutable data is **not automatically safe**.

### 8. Insecure Direct Object Reference (IDOR) — [Seen]

**ANSWER TO STUDY:** **3** — Insecure Direct Object Reference (IDOR / BOLA).  
**Evidence:** Screenshot-based study answer. **Note:** Exact prior answer reply not available.
**Question (summary):** A logged-in user requests `GET /api/orders/101`. The API checks authentication but not ownership. The user changes the path ID to another customer's order ID and can read that order. Identify the vulnerability.

**Options:**
1. Cross-Site Scripting (XSS)
2. Cross-Site Request Forgery (CSRF)
3. Insecure Direct Object Reference (IDOR)
4. SQL Injection

**Answer [Explainer]:** **Option 3 — IDOR**, also called broken object-level authorization (BOLA). Authorize access **to each requested object**, not just the endpoint.

### 9. Java parallel streams with side effects — [Seen]

**ANSWER TO STUDY:** result = [SALES, TECH]; shared captured ArrayList is nondeterministic/unsafe.  
**Evidence:** Study answer. **Note:** Question option mapping unavailable.
**Question (summary):** Given a Java stream like:

```java
List<String> captured = new ArrayList<>();
List<String> result = Stream.of("SALES", "TECH", "SALES", "OPS", "TECH")
    .parallel()
    .filter(d -> { captured.add(d); return !d.equals("OPS"); })
    .distinct()
    .sorted()
    .collect(Collectors.toList());
```

A reporting pipeline shows inconsistent behavior in the captured collection under concurrent execution. Which outcomes are deterministic, and which are not?

**Answer [Explainer]:** The stream **result**, when the execution succeeds, is deterministically **`[SALES, TECH]`** because `OPS` is filtered out, then distinct/sorted. The side-effectful **`captured`** ArrayList is not thread-safe, so its content/order/size is not dependable and there can be races or lost updates. The exact MCQ choice numbering is unclear.

### 10. Longest Common Subsequence (LCS) DP off-by-one — [Seen]

**ANSWER TO STUDY:** Correct LCS: X[i-1] == Y[j-1]; C[i-1][j-1]+1 else max(C[i-1][j], C[i][j-1]).  
**Evidence:** Study answer. **Note:** Original option mapping unavailable.
**Question (summary):** A program implements LCS with a `(m+1) × (n+1)` dynamic-programming matrix and loops `i=1..m`, `j=1..n`. Records now return inflated/incorrect matches, and the solution must retain the DP layout and return `C[m][n]`. Which replacement corrects it?

**Fault pattern shown:** Comparing `X[i]` and `Y[j]` (or using incorrectly shifted neighboring DP cells) inside one-based DP loops.

**Answer [Explainer]:** Compare **`X[i-1] == Y[j-1]`** when indexing the strings; if equal, `C[i][j]=C[i-1][j-1]+1`, else `C[i][j]=max(C[i-1][j],C[i][j-1])`. Initialize row/column zero. Choose the option containing this combination. Original four code snippets are too blurry to reproduce exactly.

### 11. Merge intervals / insert a priority alert window — [Seen, partial numeric details]

**ANSWER TO STUDY:** Merge overlapping intervals by min(start) and max(end), preserving sort order.  
**Evidence:** Study answer — exact value unknown. **Note:** Input endpoints and exact option number not verified.
**Question (summary):** An emergency broadcast scheduler maintains sorted, non-overlapping integer intervals. Insert a new interval and merge any overlapping intervals while retaining chronological order. The screenshot gives a fixed `slots` list and a new `alert` and asks for the merged sequence. Algorithm displayed:

```java
while (i < n && slots[i][1] < alert[0]) res.add(slots[i++]);
while (i < n && slots[i][0] <= alert[1]) {
    alert[0] = Math.min(alert[0], slots[i][0]);
    alert[1] = Math.max(alert[1], slots[i][1]);
    i++;
}
res.add(alert);
while (i < n) res.add(slots[i++]);
```

**Answer [Explainer]:** Intervals strictly before the inserted alert remain; overlapping intervals are merged by minimum start and maximum end; after intervals remain. **The exact provided interval endpoints and four resulting sequence options are not readable enough to claim one numbered answer.**

### 12. Encoding binary-tree node coordinates into a sortable key — [Seen]

**ANSWER TO STUDY:** Encoded key with safe column offset and multiplier > value range.  
**Evidence:** Study answer. **Note:** Original option number not verified.
**Question (summary):** An audit tool stores vertical traversal of a binary tree; it needs a unique integer key encoding the column (`col`, range approximately ±1000) and the node value (0..1000), while retaining correct sort order by column then value. Which encoded-key strategy avoids collisions and preserves sorting?

**Answer [Explainer]:** Use a **sufficiently large multiplier / offset**, such as `key = (long long)(col + OFFSET) * BASE + value` where `BASE > maxValue - minValue` and `OFFSET >= |minCol|`. Recover using integer division/modulo; make sure signed arithmetic and casts preserve ordering. Several candidate formulas were displayed, but full option text is not reliably recoverable.

### 13. Bitmask DP — invalid pruning by popcount — [Seen]

**ANSWER TO STUDY:** Equal popcounts do not imply equivalent task masks or future legal transitions.  
**Evidence:** Study answer. **Note:** Original option number not verified.
**Question (summary):** For tasks with durations and prerequisite bitmasks, `dp[mask]` represents minimum completion cost for the exact set of completed tasks. An optimization discards one state whenever two masks have equal number of set bits and one current DP cost is lower. Why can this pruning give suboptimal schedules?

**Options (readable concepts):** One option says **equal popcount masks may enable different future prerequisites/schedules**. Other options cite DP costs, state compression or numeric comparisons without recognizing different task identities.

**Answer [Explainer]:** **Equal number of completed tasks does not mean equal completed task sets**. Future eligible tasks depend on which prerequisites were completed. A lower-cost mask does not dominate every different mask with the same popcount.

### 14. Postfix expression evaluator and unary minus — [Seen]

**ANSWER TO STUDY:** Unary token '~' not in operator set, making unary branch unreachable.  
**Evidence:** Study answer. **Note:** Original option number not verified.
**Question (summary):** Python evaluates postfix arithmetic with binary operators and a special token for unary negation. Code checks `if token in operators`, then within that branch checks for unary `~`. What is the bug (while `3 4 + 5 -` should evaluate to `2`)?

```python
operators = {'+', '-', '*', '/', '^'}
for token in expression.split():
    if token.isdigit(): stack.append(int(token))
    elif token in operators:
        if token == '~':
            a = stack.pop()
            stack.append(-a)
        else:
            ...
```

**Answer [Explainer]:** The special unary operator **`'~'` is missing from `operators`**, so the unary branch is unreachable. Add it to the operator set or check for unary `~` before the binary-operator branch. Note: if the code truly prints only `3 4 + 5 -`, that particular sample does not exercise unary negation.

### 15. Finding pivot/minimum in a rotated sorted array — [Seen]

**ANSWER TO STUDY:** Modified binary search for rotated array minimum/pivot, O(log n).  
**Evidence:** Study answer. **Note:** Original option number not verified.
**Question (summary):** A huge sorted array has been rotated at an unknown pivot (e.g., ascending array now begins with larger values followed by smaller values). Find the minimum/pivot efficiently.

**Answer [Explainer]:** **Modified binary search in O(log n)** (assuming distinct elements): compare midpoint with the right endpoint; if `a[mid] > a[right]` move left bound to `mid+1`, else move right bound to `mid`. Repeated duplicates can degrade worst-case complexity.

### 16. Nested-loop time complexity — [Seen]

**ANSWER TO STUDY:** O(n log n).  
**Evidence:** Study answer. **Note:** Original option number not verified.
**Question (summary):** A nested loop has an outer index that iterates through `n` positions. The inner index starts at 1 and **doubles** repeatedly until it exceeds `n`; inner work is O(1). What is total time complexity?

**Answer [Explainer]:** **O(n log n)**; `n` outer iterations times roughly `log₂ n` inner iterations.

### 17. Digit DP with state `pos`, `tight`, `prev`, `has7` — [Seen, partially cropped]

**ANSWER TO STUDY:** Exact logical flaw unverified; review digit-DP state, leading zeros and previous-digit handling.  
**Evidence:** Unresolved. **Note:** No defensible unique choice available.
**Question (summary):** A digit DP counts integers up to a limit subject to digit constraints. The state uses `pos` (MSD-to-LSD digit), `tight`, previous digit, and `has7` flag. The transition rejects a digit matching `prev` and tracks whether digit 7 has appeared. Which logical flaw causes wrong results for large cases?

**Answer [Explainer / caveat]:** The screenshot's precise target property and answer options are only partly legible. Key issues to check are **whether the memoization key contains all future-relevant state (`pos`, `tight`, `prev`, `has7`)**, **leading zeros**, and whether the transition matches the intended 7-related constraint. Do **not** memorize a particular numbered choice from this screenshot without seeing the full options.

---

# SECTION 2 — AI / LLM Fundamentals (Questions 1–17)

### 1. Tokenization of rare specialized medical terms — [Seen]

**ANSWER TO STUDY:** Subword tokenization, e.g. BPE / WordPiece.  
**Evidence:** Study answer. **Note:** Original option number unavailable.
**Question (summary):** A custom language model trained on specialized medical records frequently encounters very long, rare compound words and out-of-vocabulary tokens. Which tokenization approach best mitigates the problem?

**Answer [Explainer]:** **Subword tokenization** such as **Byte Pair Encoding (BPE)**, **WordPiece** or **SentencePiece** (choose the actual subword-tokenizer option). It segments unseen words into reusable units; byte-level variants can avoid unknown-token failures.

### 2. Original question not confirmed — [Missing]

**ANSWER TO STUDY:** Not recoverable.  
**Evidence:** Missing. **Note:** No confirmed question statement.
**Status:** An intervening message/image cannot be reliably associated with a readable AI/LLM Question 2. No original statement or four options are recoverable with confidence. **Do not substitute the database candidate-key question here:** that is Area Tracks Question 2.

### 3. Overall spam-classifier misclassification rate — [Seen]

**ANSWER TO STUDY:** **3 (visible choice, tentative)** — 5.60% misclassified (0.88×5% + 0.12×10%).  
**Evidence:** Screenshot-based study answer. **Note:** Prior assistant reply with explicit option unavailable.
**Question:** An email classifier processes 50,000 messages; 12% are spam. It has a 5% false-positive rate on non-spam and 10% false-negative rate on spam. Approximately what percentage of all messages are misclassified?

**Answer [Explainer]:** **5.6%**: `0.88 × 0.05 + 0.12 × 0.10 = 0.056`. The screenshot includes **5.60%** among the displayed choices.

### 4. Long-range dependencies in sequence prediction — [Seen, partial]

**ANSWER TO STUDY:** **4 (tentative)** — Long Short-Term Memory (LSTM).  
**Evidence:** Screenshot-based study answer. **Note:** Choice order partially blurred.
**Question (summary):** A sequence-prediction model has difficulty capturing information over long intervals and newer patterns across many time steps. Which architecture is designed to preserve long-term dependencies compared with a traditional recurrent architecture?

**Answer [Explainer]:** **Long Short-Term Memory (LSTM)** (or gated RNN architecture) uses gating to retain or forget information and mitigate vanishing gradients. The four option strings are partly blurred; screenshot appears to include options `Convolutional Neural Network`, `Support Vector Machine`, `Decision Tree`, `Long Short-Term Memory (LSTM)`.

### 5. Prompting with intermediate reasoning steps — [Seen]

**ANSWER TO STUDY:** Chain-of-thought / step-by-step prompting.  
**Evidence:** Study answer. **Note:** Original option number unavailable.
**Question:** An engineering team uses a workflow for a complicated problem: (1) read the statement, (2) identify numerical values and constraints, (3) derive intermediate relationships before computing, and (4) produce the final answer using the derived reasoning. Which prompting strategy is most closely represented?

**Answer [Explainer]:** **Chain-of-thought (step-by-step) prompting**; prompting to reason through intermediate stages rather than supplying an answer directly.

### 6. Credit-risk model revision and independent validation — [Seen, partial options]

**ANSWER TO STUDY:** Independent validation worsens despite training improvements: overfitting, poor generalization.  
**Evidence:** Study answer. **Note:** Earlier user disputed an answer for this question; no certain option number.
**Question (summary):** A financial institution evaluates successive credit-risk model versions. One model's development/testing performance improves while independent validation produces worse results. During model review, a senior architect recommends reverting to an earlier configuration. Which concern best supports that recommendation?

**Answer [Explainer]:** **Overfitting / poor generalization and the need to trust independent validation**, rather than an attractive development score. The question's option wording is blurred; earlier replies in this conversation were disputed, so no exact option number is asserted.

### 7. Genomic disease classifier improves with more training patients — [Seen]

**ANSWER TO STUDY:** **2 (tentative)** — High feature dimensionality and too few examples; more representative data reduces variance.  
**Evidence:** Screenshot-based study answer. **Note:** Option order not independently verified.
**Question:** A biotechnology model has about 20,000 genomic measurements and only 750 training patients. Training accuracy is approximately 99%, but test performance changes dramatically between hospitals. After increasing training data to 15,000 patients without changing architecture, performance becomes more stable. Why?

**Answer [Explainer]:** **High dimensionality relative to sample count leads to sparse coverage and overfitting**. Increasing representative examples generally helps learn a more stable signal and reduces variance; it does not reduce the raw number of feature dimensions.

### 8. Increasing language-model parameters — [Seen]

**ANSWER TO STUDY:** **3 (tentative)** — More parameters can capture richer patterns but increase memory/compute.  
**Evidence:** Screenshot-based study answer. **Note:** Option order not independently verified.
**Question:** The team replaces a small language model with a much larger one while holding task and training-data type constant. It expects richer patterns but has limited deployment resources. What is the likely effect of more parameters?

**Answer [Explainer]:** Higher **representational capacity**, with increased **memory, computational cost, and possible overfitting** (depending on data/training). Among the four displayed options, the one about capturing **richer patterns but using more resources** is the matching statement (appears to be **option 3**, verify wording).

### 9. Backpropagation: gradients identify parameter contribution to error — [Seen]

**ANSWER TO STUDY:** **4** — Quantifies parameter contribution to error via gradients.  
**Evidence:** Screenshot-based study answer. **Note:** Four options reconstructed in existing file.
**Question:** A fraud model misclassifies high-value transactions. Engineers backpropagate loss through multiple layers and analyze how small parameter changes influence activations and final loss. Which capability of backpropagation is used most directly?

**Options:**
1. Determines prediction confidence thresholds.
2. Estimates feature importance across datasets.
3. Measures activation sparsity across layers.
4. Quantifies parameter contribution to error.

**Answer [Explainer]:** **Option 4.** Backpropagation computes derivatives of loss with respect to parameters, via the chain rule.

### 10. Embeddings and cosine similarity for candidate matching — [Seen]

**ANSWER TO STUDY:** **4** — Semantic similarity is captured numerically using embeddings/cosine similarity.  
**Evidence:** Screenshot-based study answer. **Note:** Four options reconstructed in existing file.
**Question (summary):** A hiring platform embeds a job description and each applicant profile into vectors, measures cosine similarity, and shortlists candidates over a threshold. Profiles may match despite using different words from the job description. Which concept explains this?

**Options:**
1. Retrieved documents are supplied externally.
2. Model parameters are randomly initialized.
3. Multiple responses are sampled repeatedly.
4. Semantic similarity is captured numerically.

**Answer [Explainer]:** **Option 4** — semantic embeddings map similar meanings to nearby vector representations; cosine similarity measures alignment.

### 11. Binomial probability — [Seen, question ending cropped]

**ANSWER TO STUDY:** **2 only IF event is X=3** — Cannot establish answer without missing probability event; if P(X=3), 0.2668.  
**Evidence:** Conditional study answer. **Note:** Event is cropped, not a verified assessment answer.
**Question (visible start):** `Let X ~ Binomial(10, 0.3). What is P(X ...` **The actual event (`=k`, `>=k` etc.) is cut off in the image.**

**Options visible:** approximately `0.3828`, `0.2668`, `0.1211`, `0.1493`.

**Answer [Explainer / caveat]:** The probability mass formula is `P(X=k) = C(10,k) (0.3)^k (0.7)^(10-k)`. In particular `P(X=3)=0.2668279` (option 2 **if** the missing expression was `P(X=3)`). Without the missing event **the MCQ answer cannot honestly be verified**.

### 12. Color preprocessing for red corrosion versus gray dust — [Seen]

**ANSWER TO STUDY:** **3** — Preserve all three RGB color channels.  
**Evidence:** Screenshot-based study answer. **Note:** Four options reconstructed in existing file.
**Question:** A corrosion classifier must distinguish red defects from equally bright gray dust; the camera provides separate R, G, B measurements for every location. Which preprocessing retains the necessary evidence?

**Options:**
1. Average channels into grayscale values.
2. Threshold channels into binary masks.
3. Preserve all three color channels.
4. Keep only maximum channel intensity.

**Answer [Explainer]:** **Option 3**, since red-versus-gray color differences can vanish after collapsing channels to grayscale.

### 13. Four models and validation generalization — [Seen, chart not legible]

**ANSWER TO STUDY:** Model with largest training/development versus validation gap.  
**Evidence:** Study principle, not final model. **Note:** Specific model letter not verified.
**Question (summary):** A team compares four candidate models for production. It wants to avoid models that look good during training but do not generalize to unseen validation data. Which model poses the greatest risk of reduced generalization?

**Options displayed:** statements naming Models A, B, C and D in different orders, based on whether development and validation results diverge. **The chart/specific metrics mentioned by "As shown in the given image" are not readable here.**

**Answer [Explainer]:** Choose the model with **strong training/development performance but significantly worse validation performance** (largest meaningful generalization gap), *not* automatically the model with the highest training score. Exact model letter cannot be confirmed from this photograph.

### 14. Gradient descent optimization — [Seen]

**ANSWER TO STUDY:** **2** — Progressively minimize the optimization objective.  
**Evidence:** Screenshot-based study answer. **Note:** Four options reconstructed in existing file.
**Question:** A forecasting team repeatedly evaluates prediction error and updates parameters. Changes become small when the objective is nearly optimal. What is gradient descent primarily trying to accomplish?

**Options:**
1. Progressively simplify prediction outputs.
2. Progressively minimize the optimization objective.
3. Progressively increase the training dimensionality.
4. Progressively expand the training distribution.

**Answer [Explainer]:** **Option 2**; iteratively update parameters in a negative-gradient direction to reduce the loss/objective.

### 15. Confidence interval for component-failure proportion — [Seen]

**ANSWER TO STUDY:** **2** — Approximately [0.05, 0.13] (95% CI for 18/200).  
**Evidence:** Screenshot-based study answer. **Note:** Exact rounding may vary.
**Question:** Of 200 components tested for 1,000 hours, 18 fail. Which interval is closest to the approximate 95% confidence interval for the true failure proportion?

**Answer [Explainer]:** `p̂=18/200=0.09`, normal approximation `0.09 ± 1.96 sqrt(0.09×0.91/200) ≈ [0.050, 0.130]`; **option 2**, the interval around **`[0.05, 0.13]`**. Exact options' decimal places are slightly blurred.

### 16. Mean and median of five expressions — [Seen]

**ANSWER TO STUDY:** **4** — Median = 17.  
**Evidence:** Screenshot-based study answer. **Note:** Four values shown in screenshot.
**Question:** The mean of `k−1, k+2, k+5, k−3, k+7` is `17`. What is their median?

**Options:** `20`, `12`, `15`, `17`.

**Answer [Explainer]:** **Option 4 — 17.** The sum is `5k+10`, hence `(5k+10)/5=k+2=17`, so `k=15`; sorted values are `[12,14,17,20,22]`.

### 17. Original question not confirmed — [Missing]

**ANSWER TO STUDY:** Not recoverable.  
**Evidence:** Missing. **Note:** No confirmed question statement.
**Status:** No readable AI/LLM Question 17 can be confidently distinguished from the subsequent restart at Area Tracks Question 1. Original question/options not fabricated.

---

# SECTION 3 — Area Tracks / SQL & Database Engineering (Questions 1–18)

### 1. SQL Server stored procedure filtering books by an optional genre — [Seen]

**ANSWER TO STUDY:** CREATE PROCEDURE ... @Genre NVARCHAR(...) ... SELECT ... WHERE Genre = @Genre.  
**Evidence:** Study answer. **Note:** Exact original SQL-option number not verified.
**Question (summary):** A reporting developer needs a reusable routine that returns all rows from `Library.Books` for a given genre. Caller should pass genre as an `NVARCHAR` parameter and optionally query without hardcoding genre into the SQL. Which definition correctly creates this stored procedure in SQL Server?

**Answer [Explainer]:** Valid SQL Server-style syntax uses `CREATE PROCEDURE ... @Genre NVARCHAR(...) AS BEGIN SELECT ... WHERE Genre = @Genre END`. If the task requires **optional filter**, a default `@Genre NVARCHAR(50)=NULL` and condition `(@Genre IS NULL OR Genre=@Genre)` meet that requirement. Multiple code options are displayed but perspective prevents dependable option numbering.

```sql
CREATE PROCEDURE dbo.uspBooksByGenre
    @Genre NVARCHAR(50) = NULL
AS
BEGIN
    SELECT *
    FROM Library.Books
    WHERE @Genre IS NULL OR Genre = @Genre;
END;
```

### 2. Candidate key and attribute closure — [Seen]

**ANSWER TO STUDY:** **3** — Attribute closure confirms a set can determine all attributes (plus minimality for candidate key).  
**Evidence:** Screenshot-based study answer. **Note:** Prior assistant reply not visible.
**Question (summary):** What is the significance of validating a candidate key through attribute closure in relational database design?

**Options visible:**
1. It provides a basis for implementing distributed databases.
2. It assists in identifying optimal transaction logs.
3. It confirms that the set can uniquely identify all tuples in a relation.
4. It helps determine the scalability of the database.

**Answer [Explainer]:** **Option 3**. A set of attributes is a **superkey** if its closure contains every attribute; it is a **candidate key** only if additionally **minimal** (no proper subset is a superkey).

### 3. Outer join versus inner join — [Seen]

**ANSWER TO STUDY:** **3** — Outer join includes unmatched tuples; inner join retains matching tuples.  
**Evidence:** Explicit earlier assistant reply. **Note:** Original worded choice shown in prior reply.
**Question:** How does the OUTER JOIN operation differ in its handling of tuples compared to INNER JOIN?

**Options (closely readable):**
1. Outer Join removes all tuples that do not have corresponding matches in the other relation.
2. Outer Join modifies the attributes of tuples to include extra fields.
3. Outer Join includes unmatched tuples from both relations; Inner Join includes only matching tuples.
4. Outer Join combines only the tuples that have matching keys in both relations.

**Answer [Explainer]:** **Option 3 is the intended choice**, with a precision caveat: **FULL OUTER JOIN** preserves unmatched rows from both sides, **LEFT** from the left, and **RIGHT** from the right. INNER JOIN keeps matching row combinations only.

### 4. Group aggregation over a computed field — [Seen]

**ANSWER TO STUDY:** **3** — Evaluate computed field for each row before aggregating AVG etc..  
**Evidence:** Explicit earlier assistant reply. **Note:** Original worded choice shown in prior reply.
**Question (summary):** An engineer defines a computed engagement score based on views, comments and likes, and wants an aggregate query returning average rating/engagement per category. What must the database logically do to process a grouped aggregate of that computed expression correctly?

**Options (visible):**
1. Store the computed field permanently and reference it directly.
2. Skip the computed field and aggregate regular columns.
3. Evaluate the computed field on each qualifying row so its values are available as input to aggregation functions such as `AVG`.
4. Perform aggregation before evaluating the computed expression (using only group-level summaries).

**Answer [Explainer]:** **Option 3** in the intended logical explanation. An optimizer may algebraically rewrite a computation for performance, but the resulting aggregated value must be **semantically equivalent** to aggregating the per-row expression.

### 5. Database views and materialized-view refresh strategy — [Seen, partially cropped]

**ANSWER TO STUDY:** **1** — They dictate the refresh strategy of materialized views.  
**Evidence:** Explicit earlier assistant reply. **Note:** Question stem partially unclear; confidence limited.
**Question (summary):** A database-related question asks how dependencies/underlying data changes influence the treatment of **materialized views** (perhaps refresh policy). Which statement fits?

**Options (clearly legible):**
1. They dictate the refresh strategy of materialized views.
2. They influence which attributes are included or excluded.
3. They establish the security protocols for views.
4. They determine the real-time updating of views.

**Answer [prior reply; uncertain]:** **Option 1**, as given during the assessment. **The full question stem is unreadable from the available photograph**; do not treat this as a general rule that every dependency alone dictates refresh timing. Study `REFRESH ON COMMIT` vs scheduled/on-demand refresh, query rewrite, and base-table changes.

### 6. Employees / direct-manager SQL self-join — [Seen]

**ANSWER TO STUDY:** **1** — JOIN Employees e to Employees m ON e.manager_id = m.emp_id.  
**Evidence:** Explicit earlier assistant reply. **Note:** SQL text reproduced from prior reply.
**Question:** Given `Employees(emp_id, emp_name, manager_id)`, each team member's `manager_id` references their manager's `emp_id`. Return each employee's name and the name of their **direct manager**, excluding executives whose `manager_id` is null.

**Options visible (abbreviated; in order):**
1. `FROM Employees e JOIN Employees m ON e.manager_id = m.emp_id`
2. `FROM Employees e RIGHT JOIN Employees m ON e.manager_id = m.emp_id`
3. `FROM Employees e INNER JOIN Employees m ON e.emp_id = m.manager_id WHERE e.manager_id IS NULL`
4. `FROM Employees e LEFT JOIN Employees m ON e.emp_id = m.manager_id`

**Answer [Explainer]:** **Option 1**. The inner self-join naturally excludes rows without a matching manager:

```sql
SELECT e.emp_name AS employee, m.emp_name AS manager
FROM Employees e
JOIN Employees m ON e.manager_id = m.emp_id;
```

### 7. Write-Ahead Logging (WAL) — [Seen]

**ANSWER TO STUDY:** **3** — WAL ensures integrity by recording changes before they are applied.  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.
**Question:** How does write-ahead logging (WAL) in database storage ensure integrity, and what are the performance trade-offs?

**Options:**
1. Improves read performance.
2. Simplifies data encryption.
3. Ensures data integrity by recording changes before they are applied.
4. Reduces storage costs.

**Answer [Explainer]:** **Option 3.** WAL makes replay/recovery possible after a crash; it adds sequential log writes and flush/commit latency, partially offset by batching/group commit.

### 8. PL/SQL UPDATE affected-row check — [Seen]

**ANSWER TO STUDY:** **1** — IF SQL%NOTFOUND immediately after UPDATE.  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.
**Question:** `update_customer_status` accepts a customer ID and new status, executes `UPDATE`, then must raise an error if no rows were affected. What PL/SQL construct detects this immediately?

**Options:**
1. `IF SQL%NOTFOUND` immediately after `UPDATE`.
2. `IF SQLCODE = 100` after the `UPDATE`.
3. `SELECT COUNT(*)` before the update.
4. Loop checking for affected rows.

**Answer [Explainer]:** **Option 1**. Equivalent clear check: `IF SQL%ROWCOUNT = 0 THEN RAISE_APPLICATION_ERROR(-20001, 'Not found'); END IF;`.

### 9. PostgreSQL grouped reporting view is read-only — [Seen]

**ANSWER TO STUDY:** **4** — Aggregated join view is read-only; use INSTEAD OF trigger for controlled writes.  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.
**Question (summary):** A view `SalesView` joins `products` and `sales`, groups by product name and category ID and calculates `SUM(sale_amount)`. Updating product metadata through the grouped view fails. Why, and what limited write-handling approach is safe?

```sql
CREATE VIEW SalesView AS
SELECT p.product_name, p.category_id,
       SUM(s.sale_amount) AS total_sales
FROM products p JOIN sales s ON p.product_id = s.product_id
GROUP BY p.product_name, p.category_id;
```

**Options:**
1. Read-only status is due to lacking persisted results; converting to materialized view makes aggregates directly writable.
2. Only omitted base keys cause read-only status; adding `product_id` makes PostgreSQL infer write targets.
3. Missing row checks cause read-only status; add `WITH CHECK OPTION` to update grouped output.
4. Read-only status follows from grouped multi-relation derivation; keep analytics in the view and route approved writes through an `INSTEAD OF` trigger.

**Answer [Explainer]:** **Option 4.** Aggregated join views generally aren't automatically updatable; an `INSTEAD OF` trigger can map controlled changes to real base-table updates when the mapping is well-defined.

### 10. SQL Server covering nonclustered index — [Seen]

**ANSWER TO STUDY:** **3** — INCLUDE columns do not count toward index key-size / count limits.  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.

**Question:** An enterprise database uses a nonclustered index on `Sales.Transactions`, with `TransactionDate` as its only key column. A reporting query filters by transaction date and returns `TerminalID`, `Amount`, and `AuthCode`. The architect includes these three as nonkey columns rather than expanding the key. The table also has a `VARCHAR(MAX)` Comments column used occasionally by audit queries. Why choose this configuration?

```sql
CREATE NONCLUSTERED INDEX IX_Report_Covering
ON Sales.Transactions (TransactionDate)
INCLUDE (TerminalID, Amount, AuthCode);

SELECT TerminalID, Amount, AuthCode
FROM Sales.Transactions
WHERE TransactionDate BETWEEN '2026-01-01' AND '2026-03-21';
```

**Options:**
1. Requires index key expansion when adding data types like `ntext` or `image` types.
2. Forces query optimizer to access clustered index data to locate specific values.
3. Bypasses engine calculations for index key size or the total number of key columns.
4. Prioritizes column definition order to ensure optimal performance for the query.

**Answer [Explainer]:** 3. Included columns do not count toward the index-key column count or key-size limit. They are stored at the leaf level and can cover the SELECT without a key lookup. This does **not** mean included columns have no storage or maintenance cost. Legacy `text`/`ntext`/`image` columns are not generally eligible as included columns; `varchar(max)` may be included, subject to SQL Server rules.

### 11. Running total using SQL window functions — [Seen]

**ANSWER TO STUDY:** **3** — SUM(...) OVER (PARTITION BY Category ORDER BY OrderDate).  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.

**Question:** A developer needs a summary report from a large Orders table, showing sales per product category over the last year and a running total as orders are processed within each category. Which SQL window function/clause calculates the running total without self-joins or subqueries?

**Options:**
1. `PIVOT` or `UNPIVOT`, which transforms rows into columns or vice versa and is not designed for running aggregations.
2. `RANK() OVER (ORDER BY TotalSales DESC)` to rank categories rather than calculate a running total.
3. `SUM(...) OVER (PARTITION BY Category ORDER BY OrderDate)` to calculate the running total within each category.
4. `NTILE(4) OVER (ORDER BY OrderDate)` to divide orders into quartiles rather than calculate running totals.

**Answer [Explainer]:** 3.

```sql
SELECT Category, OrderDate, OrderID, Amount,
       SUM(Amount) OVER (
           PARTITION BY Category
           ORDER BY OrderDate, OrderID
           ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
       ) AS RunningTotal
FROM Orders;
```

**Interview note:** An explicit `ROWS` frame and deterministic tie-breaker avoid ambiguity when several orders share the same date. If a separate category-level annual total is required, use another window aggregate or a grouped query.

### 12. MPP query optimizer, nested loop vs hash join — [Seen]

**ANSWER TO STUDY:** **4** — Use same data type and join distribution keys for both MPP tables.  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.

**Question:** An MPP system's query optimizer consistently chooses a nested-loop join for two large tables, taking hours. Statistics are current. Which architectural change is most likely to encourage an efficient hash join?

**Options:**
1. Increase memory of the leader node for larger intermediate result sets.
2. Add more compute nodes to increase total RAM for hash joins.
3. Rewrite using a `CROSS JOIN` followed by `WHERE` to trick the optimizer.
4. Ensure join columns have the same data type and are used as distribution keys for both tables.

**Answer [Explainer]:** 4. Compatible join-key types and colocated distribution can avoid expensive redistribution and enable efficient distributed equijoins. The optimizer is not *guaranteed* to choose hash join; inspect query plans and table distribution.

### 13. Scheduler starvation — [Seen]

**ANSWER TO STUDY:** **2** — Starvation from higher-priority arrivals bypassing older runnable jobs.  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.

**Question:** A shared request scheduler serves standard and premium jobs during an afternoon burst. Standard jobs are queued and runnable, while premium jobs continue arriving and are always chosen first. Workers remain active and completed-task counts increase, but old standard requests wait indefinitely. Identify the scheduling pathology and its immediate cause.

**Options:**
1. Repeated conflict-avoidance causes active requests to yield and retry, creating activity without stable completion (livelock).
2. A fairness failure in dispatch policy lets newly arriving higher-priority work repeatedly overtake older runnable requests for an unbounded duration.
3. Worker-capacity saturation prevents forward progress uniformly across both classes.
4. Cyclic hold-and-wait dependency among active requests prevents all progress (deadlock).

**Answer [Explainer]:** 2 — **starvation** caused by strict priority scheduling without fairness/aging. Mitigations: priority aging, weighted fair scheduling, or guaranteed standard-class capacity.

### 14. Oracle local vs global partitioned indexes — [Seen]

**ANSWER TO STUDY:** **4** — Local index partitions drop with table; global index may need repair.  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.

**Question:** Oracle `ORDERS` is range-partitioned monthly by `ORDER_DATE` in a 24-month rolling window. It has a local index aligned to monthly partitions and a global partitioned index on `CUSTOMER_ID` for cross-month searches. The DBA drops the oldest partition without requesting index maintenance. Date reports still work, but customer lookups fail until index repair. Why?

```sql
ALTER TABLE ORDERS
DROP PARTITION orders_2022_jan;
```

**Options:**
1. Both access paths stay valid; later failures usually indicate stale statistics rather than index-state transitions.
2. Customer indexing self-adjusts because it spans all months, while matched date indexing must be rebuilt.
3. Matched date indexing survives only through delayed cleanup, while customer indexing remains intact because its key ignores partition cuts.
4. Matched index slices disappear with their table slice, but cross-slice customer indexing loses independence and may require explicit post-drop repair.

**Answer [Explainer]:** 4. Local index partitions correspond to table partitions. A global index spans table partitions, so dropping one can affect its validity/maintenance. **Oracle version/DDL caveat:** Modern Oracle versions may support asynchronous global index maintenance or `UPDATE INDEXES`; do not assume every DROP always makes a global index unusable. Partitioning is not equivalent to distributing data across independent shards.

### 15. Fourth normal form / multivalued dependencies — [Seen]

**ANSWER TO STUDY:** **4** — Independent multivalued dependencies: split MovieRegion and MovieLanguage (4NF).  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.

**Question:** A streaming platform stores availability and language facts in one relation:

```text
CONTENT(MovieID, AvailableRegion, SupportedLanguage)
```

For each movie, region availability and supported languages are independently determined. Product wants to add region UK for movie M101 without changing language support. An engineer suggests inserting only `(M101, UK, English)` and tolerating other gaps. The architect says the table design creates misleading completeness and expensive maintenance. Identify the defect and remedy.

**Options:**
1. One row stores multiple values per movie, so first-normal-form repair should move regions and languages into array-style collections.
2. One nonkey attribute indirectly determines another, so update stability needs a 3NF split.
3. A composite key is only partially used by dependents, so a surrogate identifier retaining the single relation fixes repetition.
4. One determinant carries two independent repeating sets, so completeness depends on cross-pair expansion; split into `MovieRegion` and `MovieLanguage`.

**Answer [Explainer]:** 4 — independent multivalued dependencies; decompose to 4NF.

```sql
MovieRegion(MovieID, AvailableRegion)
MovieLanguage(MovieID, SupportedLanguage)
```

### 16. Correlated subquery optimization — [Seen]

**ANSWER TO STUDY:** **4** — Rewrite correlated query as appropriate EXISTS/IN/semi-join/decorrelation.  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.

**Question:** A reporting query suffers significant degradation because a correlated subquery in its `WHERE` clause is executed once per outer row, causing excessive I/O. How can it be optimized without changing semantics and while reducing vulnerability to some query manipulations?

**Options:**
1. Rewrite as a `LEFT JOIN` and filter for `NULL`, which can sometimes optimize better.
2. Create an index on the correlated inner-query column, helping the inner query but not necessarily eliminating repeated execution.
3. Convert into `UNION ALL`, which is semantically different and unlikely to fix repeated execution.
4. Rewrite as `EXISTS` or `IN` (where semantically equivalent), enabling possible semi-join optimization; alternatively decorrelate into a join/non-correlated subquery.

**Answer [Explainer]:** 4, with qualifications. `EXISTS`/`IN` are not interchangeable for every original subquery, especially with `NULL` and duplicate semantics. A `LEFT JOIN ... IS NULL` is appropriate for certain anti-joins, not every correlated query. Performance depends on the optimizer and plan. Parameterization is the main defense against SQL injection; rewriting as `EXISTS` does not by itself make SQL safe.

### 17. Point-in-time database recovery — [Seen]

**ANSWER TO STUDY:** **2** — Full recovery model / Oracle ARCHIVELOG for log-based point-in-time recovery.  
**Evidence:** Explicit earlier assistant reply. **Note:** Exact selected choice quoted earlier.

**Question:** A critical database server has an unrecoverable disk failure. Last full backup was 24 hours ago, but transaction logs are available up to the failure. Which recovery model/process provides the most granular point-in-time recovery and minimizes financial data loss?

**Options:**
1. Restore a database snapshot; this reflects the snapshot time, not later transactions.
2. Full recovery model (or Oracle `ARCHIVELOG` mode), using transaction logs to restore to a chosen point in time, potentially near failure.
3. Simple recovery model (or Oracle `NOARCHIVELOG`), recovering only to the latest full/differential backup.
4. Differential backups, capturing changes since the full backup but not independently offering point-in-time recovery after their creation.

**Answer [Explainer]:** 2. Restore a full backup and replay the required log backups/archived redo logs (and other required recovery components) to the desired point. Actual recovery is limited by the availability and integrity of logs/backups; logs existing only on the failed disk would not be recoverable.

---


### 18. Original question not confirmed — [Missing]

**ANSWER TO STUDY:** Not recoverable.  
**Evidence:** Missing. **Note:** No confirmed question statement.
**Status:** Available Area Tracks photographs cover Questions 1–17, with the header showing Question 17 before the coding activity. The user recalls 18 questions in one of the three MCQ tracks, but no distinct Area Tracks Question 18 is recoverable in the accessible images. It may mean this track actually had 17 and the 18-item track was a different section. **Do not treat an invented Question 18 as original.**

---

# SECTION 4 — Coding Problems (two slots)

### Coding 1. Target Sum: insert '+' or '-' before each element — [Seen]

**Original question (faithful summary):** Marco is very interested in numbers. Shyam provides `N` numbers and an integer target. Find the **number of expressions** obtained by placing either `+` or `-` before every number, so the resulting sum equals `target`.

**Input:** Line 1 integer `N`; line 2 has the `N` integers; line 3 is `target`. **Output:** one integer, the number of sign assignments.

**Constraints as shown:**

- `1 <= nums.length <= 20`
- `0 <= nums[i] <= 10^3`
- `0 <= sum(nums[i]) <= 10^3`
- `-10^3 <= target <= 10^3`

**Sample 1:** input `1` / `1` / `1` → output `1`.

**Sample 2:**

```text
5
2 1 4 1 3
5
```

Output `3` (the screenshot lists three valid expressions, e.g., `-2 +1 +4 -1 +3 = 5` and other sign assignments).

**Core method:** Let `S = sum(nums)`, and let `neg` be the sum of numbers assigned a minus. Then `S - 2*neg = target`, so `neg = (S-target)/2`. Count subsets summing to `neg`, with each index distinct. If `(S-target)` is negative or odd, answer 0. Zeros double the ways, including `+0` and `-0`.

**C++17 working reference:**

```cpp
#include <bits/stdc++.h>
using namespace std;

int main() {
    int n; cin >> n;
    vector<int> nums(n);
    int sum = 0;
    for (int &x : nums) { cin >> x; sum += x; }
    int target; cin >> target;

    int delta = sum - target;
    if (delta < 0 || delta % 2 != 0) {
        cout << 0 << '\n';
        return 0;
    }
    int need = delta / 2;
    vector<long long> dp(need + 1, 0);
    dp[0] = 1;
    for (int x : nums) {
        for (int s = need; s >= x; --s)
            dp[s] += dp[s - x];
    }
    cout << dp[need] << '\n';
}
```

**Complexity:** `O(N * sum(nums))` time; `O(sum(nums))` space. With up to 20 numbers the maximum count is `2^20`, fitting in 32 bits, but `long long` is convenient.

**Compilation error seen in a later screenshot:** The incorrect inner-loop test `s >= nums` compares integer `s` against the entire `vector<int> nums`. Fix it to `s >= num` (where `num` is the range-for loop's current element) or `s >= x` in the reference implementation.

### Coding 2. Original coding task not confirmed — [Missing]

**Status:** The user reports **two** coding questions, but the inspected photographs, code editor screenshot, sample tests, and compilation-error photograph all correspond to the **same Target Sum** task. A second distinct coding prompt with an independent problem statement/input/output was **not found** among the accessible captures. This placeholder preserves the second coding slot but **does not invent a problem**.

---

# Interview practice — questions likely to be asked from these topics

- **Java and concurrency:** Explain why intrinsic locks provide visibility; why a mutable `ArrayList` is dangerous in a parallel stream; how `Phaser` differs from `CyclicBarrier`; starvation versus deadlock versus livelock.
- **SQL & DB:** Walk through covering indexes and included columns; correct self-join; grouped view read-only behavior and `INSTEAD OF` triggers; local versus global indexes; WAL and point-in-time recovery; query plans and distribution keys.
- **Algorithms:** Demonstrate an LCS recurrence; identify the invalid DP-state-pruning assumption; explain `O(n log n)` inner doubling; derive Target Sum using subset-sum DP and handle zeros.
- **AI/ML:** Overfitting and validation; subword tokenizers; embeddings and cosine similarity; backpropagation; confidence intervals and weighted classification errors; training cost versus model size.

## Verification and missing-slot checklist

| Slot | Remaining verification required |
|---|---|
| AI/LLM Q2 | Missing a confidently readable question screenshot |
| AI/LLM Q17 | Missing a confidently readable question screenshot |
| Area Tracks Q18 | Confirm which track had 18 questions and recover its last screenshot |
| Coding Q2 | Missing a distinct second coding statement |

**Final count:** **54 slots = 52 MCQs (17 + 17 + 18) + 2 coding questions.** This file contains **50 identified topics/prompts**, **4 explicitly unconfirmed slots**. Some of the 50 have partially illegible wording and/or options. Numbering represents the user's recollection; the accessible Area Tracks photos only show numbering 1–17.
