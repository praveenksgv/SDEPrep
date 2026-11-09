1. Can you introduce yourself and walk me through your professional experience, particularly the projects you've worked on?

2. Can you explain the AI-powered invoice reconciliation platform you recently built? Is it related to employee reimbursements, or what business problem does it solve?

3. How does the invoice reconciliation process work? What documents are involved, and what validations does the system perform?

4. Among the projects you've worked on recently at Oracle and your current organization, which one was the most challenging, and why?

5. Which OCR technology or model did you use for the invoice reconciliation platform?

6. Which LLM did you use, and who was responsible for its API costs?

7. Were you using Air India's API keys and infrastructure, or did you create and manage your own API access?

8. Did you use LangChain or any other framework to build the LLM workflow?

9. Why did you choose a predefined backend workflow instead of an agentic AI workflow?

10. Since Gemini is multimodal and can directly process PDFs and images, why did you need a separate OCR service?

11. Did you perform a formal evaluation comparing Azure OCR with Gemini's direct document-processing capabilities, or was the decision based on manual internal testing?

12. How would you decide which AI model or document-processing pipeline to use? What steps and evaluation criteria would you consider?

13. Have you encountered situations in your previous projects where you needed to justify a technical decision with measurable evidence? Can you share an example?

14. If you are frequently changing prompts, models, or extraction logic in an AI pipeline, how would you track whether each change improves or degrades the results instead of relying on manual testing?

15. How would you compare two different LLMs, Model X and Model Y, for the same document-extraction use case?

16. What non-functional requirements would you consider when designing or evaluating an AI-powered document-processing pipeline?

17. How important was high availability for your invoice reconciliation platform, considering that initially only two clients were using it?

18. You mentioned working on a high-volume matching system that processed approximately one million match scores per week. Was that project at Oracle or Chronus?

19. If you're building a high-volume application where data acquisition is critical, how would you manage both functional and non-functional requirements?

20. For a high-volume, highly available application, what scalability approaches or algorithms would you consider?

21. What scalability solutions have you actually implemented in your previous applications?

22. Can you explain how you handled horizontal scaling and parallel processing in your Go-based matching microservice using AWS Lambda?

23. You mentioned a RAG application for QA workflows that reduced manual effort by approximately 70%. What exactly was that application, and why did you use RAG?

24. Can you describe a situation where you had a technical disagreement with stakeholders such as a product manager, senior engineer, or QA team? How did you arrive at a solution?

25. During the Redis upgrade, why did you recommend connection pooling instead of using a single Redis connection? How did you convince your manager that the change was beneficial?

26. With AI coding tools becoming common, how do you ensure the quality of code written by other developers?

27. As a senior engineer, how do you approach pull request reviews and maintain code quality when AI-generated code is involved?

28. If developers are creating around 50 PRs per week using AI tools, how would you realistically manage code reviews without manually reviewing every line?

29. Have you worked with traditional NLP libraries or low-level natural language processing techniques, beyond using LLMs?

30. Can you describe a recent situation where a technical decision you made failed, or where you missed a deadline? How did you recover, and what did you learn?
31. What did you do to prevent prompt injection in your LLM applications?