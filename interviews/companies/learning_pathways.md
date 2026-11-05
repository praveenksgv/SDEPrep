# Tech Lead Interview — Assessment System (HLD + Coding)

Round type: Tech Lead, first round
Format: HLD design (assessment platform) followed by a coding round (log file alerting)

---

## Part 1 — Introduction / Background

**Q1. Tell me about your background and what you do.**

Your answer covered: 4.5 years backend, some frontend, microservices, performance,
database work; JNI/test-generation pipeline at Oracle; invoice reconciliation system
at NullAxis (OCR + LLM) for airline customers; currently exploring opportunities;
location flexibility (Hyderabad/Bangalore).

**Feedback / what to improve:**
- For a *Tech Lead* role, lead with ownership and technical decisions, not a project
  tour. Open with: "I'm a backend engineer with ~4.5 years, and most recently I
  owned X end-to-end and made the call on Y."
- The intro drifted into logistics (flat, flatmate, how Manish found you). Keep that
  out of the opening — mention location only if asked.
- Quantify impact where possible ("reduced manual review by N%", "cut p99 latency
  from X to Y"). Tech leads are assessed on outcomes.
- Be ready for the immediate follow-up: "What did *you personally* own and decide?"
  Don't claim formal team-lead experience you don't have — frame it as technical
  leadership (design ownership, cross-team work, mentoring juniors).

---

## Part 2 — HLD: Assessment / Quiz System

System to design: A college platform where a teacher asks an LLM to generate an
assessment (~10 MCQs + answer key). Teacher reviews, sets passing threshold and
attempt limit (e.g. 3), then publishes. Students take it (never see the answer key),
are scored immediately, can retry up to the limit if below threshold. Teacher sees a
summary: scores, attempt counts, who passed/failed/didn't attempt. Generation uses
an LLM; everything else is deterministic.

**Q2. Walk through the requirements.**
**Q3. Non-functional requirements.**
**Q4. Core entities.**
**Q5. APIs / interfaces.**
**Q6. High-level architecture (with box diagram).**
**Q7. Should teacher and student be separate microservices?**
   (Your call: single modular service for now; discuss monolith → microservices later.)
**Q8. How is the answer key stored (so students never see it)?**
**Q9. Assessment status lifecycle (Draft / Active / Closed).**
**Q10. PATCH request for publishing an assessment (status transition).**
**Q11. How scoring works once an attempt is created (AttemptAnswers table).**
**Q12. Where does the Student module sit in the architecture?**
**Q13. Generation flow — where API calls and the LLM sit.**
**Q14. What do you consider when choosing an LLM provider?**
**Q15. How can the event system help evaluate providers? Any other ways?**
   (LLM-as-judge, teacher rating, automated structural checks, shadow mode via
   events, implicit feedback from AttemptAnswers, A/B routing.)

**Feedback / what to improve:**
- **Strong:** You followed a clean stage-wise flow (requirements → NFRs → entities →
  APIs → architecture → deep dives), asked clarifying questions before designing,
  and stated sensible defaults. This is exactly right.
- **Concurrency depth was good** (atomic attempt-limit check, `SELECT ... FOR UPDATE`,
  single transaction for check + insert + score). Keep leading with correctness —
  that's what separates a lead from a mid-level answer.
- **Lead the architecture decisions more confidently.** When asked "microservices or
  not?", the interviewer said "you're the architect, you should know." Don't hedge —
  state the decision (modular monolith now), give the one-line reason (shared data,
  simpler ops, generation is the only different scaling profile), and name the trigger
  to split (LLM latency/load affecting the student path). You eventually did this —
  do it *first*, then offer trade-offs.
- **Diagrams:** You adapted well to the interviewer's box style. Keep diagrams minimal
  and label data flows (who reads/writes which table) — you did this on the second pass.
- **Answer-key storage:** Good instinct to separate it into its own table and enforce
  at the query/service layer rather than UI-only. Could have mentioned column-level DB
  grants as defense-in-depth sooner.
- **LLM provider evaluation:** This was a highlight. You connected it back to the event
  system (shadow mode) and to real product signals (regenerate rate, AttemptAnswers
  distribution). That systems-thinking is what a tech lead should show.
- **Watch scope creep:** A couple of times you jumped ahead to the full architecture
  before the interviewer wanted it. Stay disciplined to "one stage, then pause" — it
  reads as more senior and collaborative.

---

## Part 3 — Coding: Login Alert from Access Log
Lets write a code to read a access log file and alert is a user has logged in more than 10 times in 1 hour. 
log file format: timestamp, user_id, activity_type

09/10/2026 00:00:01, user_a, login
09/10/2026 00:00:02, user_b, login
09/10/2026 00:00:02, user_a, logout
09/10/2026 00:00:03, user_a, login
09/10/2026 00:00:04, user_a, login
09/10/2026 00:00:04, user_c, login
09/10/2026 00:00:05, user_a, login
09/10/2026 00:00:06, user_d, login




**Feedback / what to improve:**
- **Good progression:** You asked the right clarifying questions first (rolling vs
  fixed-hour window, sorted or not, date format `MM/DD` vs `DD/MM`, login-only).
  Interviewers value this.
- **You explained approach before coding** (brute force → sliding window), which
  matches a strong interview style. Keep doing this.
- **Two valid designs:** per-user deque (one pass, each login enqueued/dequeued once)
  and the global two-pointer with a `counter` map. You adapted to the interviewer's
  preferred two-pointer/counter idea cleanly — good flexibility.
- **Small correctness points to watch:**
  - Ensure "more than 10" means the **11th** login triggers (`> 10`), which you had.
  - The global two-pointer version counts logins across *all* users in the window; make
    sure the counter is keyed by user (you did) and you only alert on the current
    `right` user's login.
  - Timezone/DST: `mktime` uses local time; fine for an interview, but say out loud
    "I'd fix the timezone explicitly in production."
- **State complexity unprompted:** O(n) time for a sorted log, O(users × window) space.
  Say it before being asked — tech leads quantify.
- **Edge cases to mention aloud:** malformed lines, duplicate timestamps, a user hitting
  exactly 10 vs 11, empty file. You handled parsing defensively; just narrate it.

---

## Overall Summary

**Strengths**
- Clean, disciplined HLD structure and good clarifying questions.
- Strong on correctness/concurrency (atomic attempt check, transactions).
- Excellent systems thinking on LLM provider evaluation tied to events/product signals.
- Good coding communication: clarify → approach → code → adapt to interviewer.

**Top 3 things to improve**
1. **Own decisions as a lead.** When asked to choose (microservices, DB, provider),
   state the decision + one-line rationale + the trigger that would change it — before
   listing trade-offs. Less hedging.
2. **Tighten the intro for a lead role.** Lead with ownership and quantified impact;
   drop logistics unless asked.
3. **Narrate complexity and edge cases unprompted** in coding, and resist jumping
   ahead of the current design stage.
