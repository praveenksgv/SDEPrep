Second Round:
Your main preparation gap is **explaining your own project precisely and confidently**. You had useful examples—especially vendor-specific matching—but uncertainty about service names, validation stages, and infrastructure weakened the explanation.

I don’t have a file-writing tool in this session, so I can’t save the file directly. The complete Markdown content below is ready to save as **`wipro_round_2_interview_review.md`**.

```markdown
# Wipro Round 2 — Interview Questions and Response Review

## Scope of this review

This review uses the interview transcript shared in this chat.

- It evaluates your recorded answers, not the assistant’s suggested answers.
- Some answers were incomplete or affected by transcription errors.
- A missing detail in the transcript does not prove you did not know it.
- This is preparation feedback, not a prediction of the hiring decision.
- Suggested answers must be adjusted to your actual implementation.

## Overall assessment

Your strongest answers used concrete examples from the invoice reconciliation project. You explained why different vendors required different extraction prompts and matching keys. Your notification factory example was also understandable.

The main weaknesses were:

1. Unclear structure and repeated filler words.
2. Uncertainty about technologies used in your own project.
3. Mixing offline evaluation with production validation.
4. Describing operational scripts when asked about Infrastructure as Code.
5. Explaining architectural components without clearly explaining their connections.
6. Not consistently distinguishing personal ownership from team-level work.

The highest-value preparation is to make your project explanation precise before studying more unrelated technologies.

## All questions and individual feedback

### 1. “Can you give a quick introduction about yourself?”

**What you said**

You mentioned your education, approximately 4.5 years of backend experience, architecture, microservices, artificial intelligence work, performance improvements, and recent invoice reconciliation work.

**What worked**

- You established your experience level and backend focus.
- You introduced the project that became the main discussion topic.

**What needs improvement**

- You repeated “microservices” and used broad phrases such as “different things.”
- “Creating AI engines” is vague and invites follow-ups you may not intend.
- Your recent project did not get a clear problem-and-contribution statement.
- “Myself Praveen” is less natural than “I’m Praveen.”

**Better structure**

1. Current role and experience.
2. Two relevant areas of work.
3. Recent project and personal contribution.

**Suggested answer**

“I’m Praveen, a backend engineer with around 4.5 years of experience building services and improving application performance. Recently, at Nlexis, I worked on invoice reconciliation: extracting invoice information and comparing it with purchase order and goods-received data to identify mismatches. My work has mainly involved backend services, document processing, and validation logic.”

Only include responsibilities you personally handled. Verify official institution and company names before using this introduction.

### 2. “Which OCR tool did you use?”

OCR means Optical Character Recognition.

**What you said**

Azure OCR, with uncertainty about a “prebuilt” module.

**What worked**

- You identified the cloud provider.
- You did not confidently invent a model name.

**What needs improvement**

An interviewer expects you to know the main document-processing service used in a project you discuss in depth.

Before the next interview, verify:

- Exact Azure service name.
- Model name or model identifier.
- Whether it performed text extraction, layout analysis, or invoice-field extraction.
- What information you passed to the language model afterward.

**Important distinction**

A prebuilt invoice model and a general text/layout extraction model do different jobs. Do not call your implementation “prebuilt invoice” solely because you remember the word “prebuilt.”

### 3. “What challenges did you face developing reconciliation?”

**What you said**

Vendor terminology and layouts varied. Most invoices used a purchase order number, while a particular vendor used a reference ID. Some vendors needed different prompts.

**What worked**

This was one of your strongest examples:

- It was specific to your project.
- It explained a real source of extraction and matching difficulty.
- It connected vendor variation to implementation decisions.

**What needs improvement**

- Avoid long explanations of what a purchase order is unless asked.
- Explain the solution and observed effect, not only the problem.
- Distinguish extracting a field from using that field to find a record.

**Suggested answer**

“One challenge was vendor variation. The same field could appear under different labels, and some vendors used a reference ID instead of a purchase order number. We handled those cases through vendor-specific prompts and matching-key rules, while keeping the common reconciliation checks shared.”

If you mention improvement, use a verified result rather than an invented percentage.

### 4. “Can you explain the architecture?”

**What you said**

Frontend, Application Programming Interface (API) server, PostgreSQL, and a worker. User requests went through the API; the worker handled reconciliation.

**What worked**

- You identified the main application components.
- You separated request handling from longer-running processing.

**What needs improvement**

The missing connections matter more than additional boxes:

- Where were uploaded files stored?
- How did the API create a task?
- How did the worker discover or receive that task?
- Where were task status and results stored?
- How did the frontend learn that processing had finished?
- What happened if processing failed?

These are preparation questions, not confirmed features of your implementation.

**Recommended explanation order**

1. User uploads an invoice.
2. API accepts it and records the work.
3. Worker receives or discovers the task.
4. Worker extracts fields and obtains reference data.
5. Reconciliation rules produce the result.
6. Result is stored and retrieved through the API.

Name the actual worker-trigger mechanism. Do not add a message queue unless your system used one.

### 5. “The LLM returned the requested data. How do you validate it?”

LLM means Large Language Model.

**What you said**

You described an evaluation/test setup and later mentioned deterministic checks and anomaly-related checks.

**What worked**

- You recognized that a model response cannot be trusted automatically.
- You referred to tests and deterministic validation.

**What needs improvement**

Your explanation mixed different kinds of validation:

| Validation type | What it establishes |
| --- | --- |
| Output/schema validation | The response has the expected structure and field types |
| Source validation | Extracted values are supported by the invoice |
| Business reconciliation | Values agree with purchase order and receipt records |
| Offline evaluation | A prompt/model performs acceptably on labeled examples |

A valid schema does not establish factual accuracy. A good evaluation score does not guarantee that every production invoice is correct.

**Suggested answer structure**

“We evaluated extraction changes on invoices with expected outputs. For each production invoice, we also applied our deterministic checks and compared the extracted information with reference records. Cases that could not be reconciled required review.”

Add schema checks, source grounding, or anomaly detection only if you actually implemented them.

### 6. “When does validation happen?”

**What you said**

You associated validation with extending the schema and running checks.

**What needs improvement**

State the timing directly:

- Before releasing a prompt or schema change: offline evaluation.
- After each model response: production validation.
- After reference-data lookup: business reconciliation.

**Suggested answer**

“When we changed the extraction schema or prompt, we ran evaluation cases before releasing it. During processing, each invoice also went through validation and reconciliation checks.”

Prepare what happened after a failed check: rejection, retry, manual review, or another actual outcome.

### 7. “Where was the worker hosted?”

**What you said**

Azure Kubernetes Service (AKS).

**Assessment**

This directly answered the question.

**Prepare likely follow-ups**

- Was the worker continuously running or scheduled?
- How was it deployed?
- How were configuration and credentials supplied?
- What happened when its container restarted?
- How did you inspect logs and failed tasks?

Know the implementation you used; do not claim cluster administration if you only owned the application.

### 8. “Do you know AWS, or have you worked only with Azure?”

AWS means Amazon Web Services.

**What you said**

You had some AWS exposure at a previous company, with more experience in Azure.

**What worked**

You calibrated your experience honestly.

**Improvement**

Replace repeated “a bit of idea” with a specific boundary:

“My primary hands-on experience is with Azure. I also used AWS Lambda in a previous role, though my AWS experience is more limited.”

### 9. “Which AWS services have you used?”

**What you said**

AWS Lambda.

**What needs improvement**

A service name should be followed by one real use case.

Prepare:

- What triggered the function.
- What the function did.
- What inputs and outputs it handled.
- What you personally changed or maintained.
- How failures were investigated.

Do not add services to make the answer sound broader.

### 10. “What about Azure? Which services have you used?”

**What you said**

AKS and PostgreSQL. Earlier, you also mentioned Azure OCR.

**What worked**

You named services relevant to your project.

**What needs improvement**

Explain each service’s purpose in one phrase.

Also verify whether PostgreSQL was:

- Azure Database for PostgreSQL.
- Self-hosted.
- Hosted through some other arrangement.

Using PostgreSQL does not by itself identify an Azure managed database product.

### 11. “Tell me about Infrastructure as Code.”

**What you said**

You described scripts that periodically checked whether something was down and brought it back up. You said you had not written those scripts.

**Assessment**

This was the clearest conceptual mismatch in the interview.

The scripts you described sound like operational monitoring or recovery automation. Their exact purpose is unclear, but the description does not establish Infrastructure as Code (IaC).

**Correct concept**

IaC defines and manages infrastructure through version-controlled configuration.

Examples include:

- Terraform.
- Azure Bicep.
- Azure Resource Manager templates.
- AWS CloudFormation.

**Suggested answer**

“Infrastructure as Code means defining resources such as networks, clusters, and databases in configuration files so environments can be provisioned consistently and changes can be reviewed. I wasn’t the primary owner of infrastructure provisioning. The scheduled scripts I mentioned were operational automation.”

**Study next**

Declarative configuration, planning changes, state, drift, repeatability, and idempotency.

### 12. “Which design patterns have you implemented?”

**What you said**

You discussed vendor-specific matching behavior and later used notification creation as a Factory example.

**What worked**

Your project contained a useful example of varying behavior.

**What needs improvement**

Different conditional branches do not automatically mean you implemented the Strategy pattern.

Be ready to explain:

- The common interface or callable contract.
- The separate implementations.
- How the implementation was selected.
- Why this was better than a small conditional in your case.

If you used configuration and conditionals, describe that honestly.

**Better phrasing**

“We had strategy-like vendor-specific behavior. I would distinguish that from a formal Strategy implementation unless we actually separated the behaviors behind a common interface.”

### 13. “How do you decide between Strategy and Factory?”

**What you said**

A factory provides an email or text-message notifier through a common notifier interface. Strategy varies invoice matching by purchase order number or reference ID.

**What worked**

Both examples were reasonable and more concrete than your initial explanation.

**What needs improvement**

Lead with the distinction before the example:

- Factory concerns object creation.
- Strategy concerns interchangeable behavior.

The caller depending on a notifier interface is abstraction. The factory specifically handles choosing and creating the concrete notifier.

**Suggested answer**

“I use Factory to hide how the appropriate object is created. I use Strategy to vary an algorithm behind a common interface. They can work together: a factory can create the matching strategy for a vendor.”

Also remember: sometimes a simple conditional is enough.

### 14. “What is the difference between unit and integration testing?”

**What you said**

You described submitting an invoice through the frontend and checking whether the API, worker, and reconciliation pipeline worked together.

**What worked**

You correctly identified interactions between components.

**What needs improvement**

Your example covers a broad workflow. If it starts through the real user interface and exercises the system through its final result, it is usually described as an end-to-end test.

A narrower integration test might verify that:

- The API correctly persists a task in a real test database.
- The worker correctly loads a task and saves its result.

**Suggested answer**

“A unit test checks isolated logic, such as a quantity-mismatch rule. An integration test checks components working together, such as the worker and database. An end-to-end test checks the complete user workflow.”

Test terminology can overlap, so explain the boundary being exercised.

### 15. “What is the difference between short polling and long polling?”

**What you said**

In long polling, the browser sends another request after receiving a response. The server waits for an update or timeout.

**What worked**

Your explanation captured the core long-polling behavior.

**What needs improvement**

Say that the server holds the HTTP request pending. HTTP means Hypertext Transfer Protocol.

“Keeping the connection open” alone is imprecise: ordinary short-polling requests can also reuse a persistent connection.

**Suggested answer**

“With short polling, the client checks at intervals and the server responds immediately. With long polling, the server waits to respond until there is an update or a timeout, after which the client sends another request.”

**Trade-offs**

- Short polling: simpler, but can create many empty requests.
- Long polling: fewer empty responses, but more concurrent pending requests and timeout management.

### 16. “Anything else?” / “Do you have any questions?”

**Transcript note**

The closing exchange was fragmented. This appears to have been an invitation for questions, but the exact wording is uncertain.

**Preparation**

Have two questions ready:

- “What would you expect someone in this role to own in the first few months?”
- “What are the main engineering challenges the team is currently addressing?”

Since the team was not finalized, asking about expectations across teams would fit the conversation.

## Communication habits to improve

### Start with the answer

You often reasoned aloud before reaching the distinction.

Use this sequence:

1. Direct answer.
2. One concrete example.
3. One relevant trade-off.
4. Stop and allow a follow-up.

For example:

“Factory handles object creation; Strategy handles interchangeable behavior.”

Then give the notifier and matching examples.

### Reduce repeated filler words

Common fillers in the transcript included:

- “Like.”
- “Basically.”
- “Kind of.”
- “Okay.”
- “There was some…”

Some may be transcription artifacts, but the pattern was frequent enough to affect clarity.

A short pause is better than filling every gap.

### Avoid restarting an entire answer

You said you had “gone here and there” and restarted the Factory explanation.

Instead, reset with one sentence:

“Let me state the distinction clearly.”

Then give the answer without apologizing repeatedly.

### Separate personal ownership from team ownership

Use:

- “I implemented…” for your contribution.
- “Our team used…” for shared architecture.
- “The platform team managed…” when that was outside your responsibility.

Honesty about scope helps, but follow it with the relevant knowledge you do have.

### Make uncertainty specific

Instead of:

“There was some prebuilt module.”

Say:

“I remember the Azure extraction service, but I need to verify the exact model name.”

This is a fallback, not a substitute for preparing the names beforehand.

### Explain impact without inventing metrics

For important projects, prepare:

- The problem.
- Your contribution.
- The implementation decision.
- The evidence that it helped.

If you do not have measured accuracy or latency figures, describe a verified qualitative result. Do not manufacture numbers.

## Preparation priorities

### Priority 1: Own the invoice reconciliation project end to end

Prepare a one-page diagram and explain:

- Upload and document storage.
- Task creation and worker execution.
- Azure extraction service and exact model.
- Language model inputs and outputs.
- Vendor-specific prompts and matching keys.
- Purchase order and receipt lookup.
- Deterministic validation rules.
- Any anomaly checks you actually used.
- Status/result persistence.
- Review and failure handling.

Practice a 90-second overview and a five-minute technical explanation.

### Priority 2: Separate evaluation from runtime validation

Know:

- What your expected-output dataset contained.
- How expected answers were produced.
- What “correct” meant for each field.
- How changes were compared against the previous version.
- Whether vendor and document-format coverage was adequate.
- Which checks ran for every invoice.
- What happened when a check failed.

If you mention anomaly detection, be able to explain its input, rule or model, threshold, and resulting action. Otherwise, avoid the term.

### Priority 3: Understand worker reliability

Prepare answers to:

- How did the worker obtain tasks?
- What happened after a crash?
- Could a task be processed twice?
- Were retries supported?
- How were failures surfaced?
- Could two workers process the same task?

These were not all asked, but they are natural follow-ups to your architecture.

### Priority 4: Learn the infrastructure concepts you mention

Focus on:

- AKS application deployment and troubleshooting.
- Pods, deployments, health probes, and logs.
- Managed versus self-hosted PostgreSQL.
- Your actual Lambda use case.
- IaC versus operational scripts.

Do not study a long cloud-service catalog at the expense of these fundamentals.

### Priority 5: Practice patterns and test boundaries

Be able to explain:

- Strategy versus Factory.
- Dependency injection versus object creation.
- When a conditional is simpler than a pattern.
- Unit versus integration versus end-to-end testing.

Use a small example for each.

### Priority 6: Rehearse concise answers aloud

Record yourself answering:

1. Introduce yourself.
2. Explain the project architecture.
3. Describe your personal contribution.
4. Explain your hardest challenge.
5. Explain validation.
6. Explain one design decision.
7. Explain one limitation or failure.

Listen for repeated fillers, missing links, unsupported claims, and answers that continue after the question has already been answered.

## Project facts to verify before the next interview

- [ ] Official names of employers, institution, and services.
- [ ] Exact Azure OCR/document extraction model.
- [ ] Exact language model and its role.
- [ ] PostgreSQL hosting arrangement.
- [ ] Uploaded-document storage.
- [ ] Worker-trigger mechanism.
- [ ] Task states, retries, and duplicate-processing behavior.
- [ ] Reference-data retrieval and reference-ID mapping.
- [ ] Actual evaluation dataset and criteria.
- [ ] Meaning of “anomaly” checks in your implementation.
- [ ] Personal ownership versus team-owned components.
- [ ] Actual AWS Lambda use case.
- [ ] Verified project outcomes or measurements.
```