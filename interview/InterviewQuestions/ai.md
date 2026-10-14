1. Single Store dicussion over architecture of an agent

```
Atlas: The Untrusted AI Incident Assistant
Your company runs a cloud database product. Engineers and support teams spend a lot of time debugging customer issues.

Six months ago, another team shipped an internal AI assistant called Atlas. Given a customer issue, Atlas is supposed to return:

a likely diagnosis,
supporting evidence,
relevant source links,
recommended next debugging steps,
and a confidence level.

Atlas can search across public docs, internal runbooks, past support tickets, Slack threads, deployment changelogs, metrics, logs, traces, and customer configuration metadata.

The current version is a standard RAG system: documents are chunked, embedded, retrieved with top-k search, and passed to an LLM to generate an answer with citations.

It looked good in demos. In production, support engineers do not trust it. Common complaints include unsupported citations, overconfident diagnoses, stale support tickets being treated as evidence, missing metrics and logs unless explicitly requested, slow responses, and one permission leak.

Walk me through how you would investigate this failure, redesign the relevant parts of Atlas, and ship a version that support engineers can trust.

You do not need to write code. I care more about how you reason than which AI framework you choose.

You may ask clarifying questions. If you make assumptions, state them.


Incident: NimbusCart Query Regression
A support engineer asks Atlas:

Customer NimbusCart reports that dashboard queries became 10x slower today.

They are on prod-us-east-1.
They say nothing changed in their application.

What is the likely cause, and what should we check next?

Scenario time:

2026-05-20 14:30 UTC

Atlas answers:

Likely diagnosis: NimbusCart is probably affected by the stale index statistics
bug described in TICKET-1842. The customer should run ANALYZE and rebuild indexes.

Evidence:
- TICKET-1842: another customer had slow dashboard queries after an upgrade.
- DOC-11: slow queries can be caused by missing indexes or stale statistics.

Confidence: high.


Available Sources
Assume Atlas had access to the following sources.
DOC-11
Type: public documentation
Visibility: customer-visible
Title: Troubleshooting slow queries

Slow queries can be caused by missing indexes, increased data volume,
lock contention, resource saturation, or query compilation overhead.

For sudden latency regressions, compare query latency, queue time,
CPU usage, memory pressure, deployment changes, and query shape changes.
RUNBOOK-7
Type: internal runbook
Visibility: internal-only
Title: Investigating sudden query latency regressions

First determine whether the latency is execution time, queue time,
network time, or compilation time.

If CPU and memory are normal but compilation time increases sharply,
check plan cache hit rate, query normalization, and whether the customer
is sending highly variable literal SQL instead of parameterized queries.

Past tickets are useful as hypotheses, but should not be treated as evidence
unless the customer's version, region, feature flags, and metrics match.
TICKET-1842
Type: support ticket
Visibility: internal-only
Created: 2025-08-12
Title: Old issue: slow dashboard queries after index stats bug

Customer ShopVerse saw 10x slower dashboard queries after upgrade v4.1.8.
Root cause was stale index statistics after a background analyze job failed.
Fixed in v4.2.0. Only affected clusters running v4.1.x.
CHANGE-991
Type: deployment changelog
Visibility: internal-only
Created: 2026-05-20 09:10 UTC
Title: prod-us-east-1 deployment

Rolled out v5.7.3 to prod-us-east-1.
Enabled adaptive_plan_cache_v2 for 5% of tenants.

Known risk: customers with highly variable literal SQL may see elevated
compilation time if plan cache churn increases.

Rollback flag: adaptive_plan_cache_v2=false.
CONFIG-55
Type: customer configuration
Visibility: restricted
Title: NimbusCart customer config

Customer: NimbusCart
Region: prod-us-east-1
Version: v5.7.3
Feature flags: adaptive_plan_cache_v2=true
Prepared statements: disabled
Plan cache size: 512 MB
METRIC-88
Type: metric
Visibility: restricted
Time range: 2026-05-20 08:00 UTC to 14:00 UTC
Title: NimbusCart query metrics

p95 query latency increased from 220 ms to 2.8 s after 09:30 UTC.
CPU remained between 32% and 41%.
Memory pressure remained low.
Queue time remained under 20 ms.
Compilation time p95 increased from 35 ms to 1.9 s.
Plan cache hit rate dropped from 91% to 37%.
LOG-12
Type: log
Visibility: restricted
Time range: 2026-05-20 09:20 UTC to 14:00 UTC
Title: NimbusCart query planner logs

Repeated planner events:
plan_cache_eviction reason=cache_churn
normalized_query_hash cardinality increased sharply
literal_query_pattern detected
adaptive_plan_cache_v2 enabled
SLACK-901
Type: Slack thread
Visibility: internal-only
Title: SRE discussion about v5.7.3 rollout

SRE note: We are seeing possible plan cache churn in a few tenants after
adaptive_plan_cache_v2.

Codename: FalconIce. Do not mention the codename or rollout percentage in
customer-facing communications.


Questions
1. Debug the Answer
What is wrong with Atlas's answer?

Please discuss:

whether TICKET-1842 applies,
what evidence Atlas ignored 
what assumptions Atlas made,
whether the confidence level is justified,
and whether the recommended next steps are appropriate.


Confidence is incorrect(more relevant confidence should be there)
Relevance on older ticket/content should be lesser but relying on it too much
If runbook is the doc that should be followed, it’s not getting followed.
Changelog was ignored & configuration
2. Produce a Better Answer
What should Atlas have answered instead?

Include:

likely diagnosis,
supporting evidence,
recommended next debugging steps,
confidence level,
and caveats or uncertainty.

Assume the answer is being shown to a support engineer. If your answer would change for an SRE or an end customer, explain how.
3. Diagnose the System Failure
What likely went wrong in the Atlas system that led to this bad answer?

Consider retrieval, ranking, source freshness, source authority, structured metrics/logs, customer scoping, time windows, citations, confidence, and permissions.

You do not need to cover every category. Focus on the failures you think matter most.

4. Redesign the Relevant Parts of Atlas
How would you redesign the relevant parts of Atlas to prevent this class of mistake?

5. Discuss the architecture and product behavior you would change. For example:

how Atlas should retrieve and rank evidence,
how it should use metrics/logs/traces differently from documents,
how it should verify claims and citations,
how it should handle role-based access and redaction,
how it should decide confidence,
when it should abstain or escalate,
and how you would manage latency and cost.

```