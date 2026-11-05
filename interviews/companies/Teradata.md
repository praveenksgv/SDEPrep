# Interview Questions — Full Session

## Round 1 — Resume / AI Project / Fundamentals / HLD / DSA

### Introduction & Project (Invoice Reconciliation)
1. Can you introduce yourself?
2. Tell me what you have there — what is the pipeline, what you are doing, how do you spread/scale it?
3. Can you elaborate on how exactly you're doing the comparison (invoice vs PO vs GRN)?
4. What kind of prompting and other techniques are you using?
5. What challenges did you face in this invoice extraction?
6. How do you improve the extraction accuracy?
7. Did you use any kind of retrievable context (RAG) in that?

### Search / Caching
a. Can you explain what a knowledge-based system is?
b. How does semantic search work?
c. What is a Top-k retrieval

### Web / API Fundamentals
a. Explain the HTTP requests — what are PUT vs PATCH?
b. Explain the HTTP status codes.
c. What types of authentication have you worked with?
d. What about authorization? Like what kinda of user has which access? How did you do it?
e. (Follow-up) Role-based access — normal user vs org admin vs super admin.

### LLM Quality
a. If you get poor results from the LLM, what things do you check? (hallucination, grounding, thresholds)
```
Let's say you asked "Describe a dog", and you get the response as "Dog is an animal" in production. What would you check for in the production. How would you fix that.
```

### HLD — Ticket Booking System (Train Booking)
17. Design a ticket booking system (customer books a ticket).
18. Stage-wise: Requirements → Non-functional reqs → Core entities → APIs → High-level design.
19. Add Redis seat-hold flow; how do you ensure no two users book the same seat?

### DSA — Capacity to Ship Packages Within D Days
```
You are given:
* An integer array weights, where weights[i] is the weight of the i-th package.
* An integer D, the number of days within which all packages must be shipped.
You have a ship with a fixed integer capacity C (same capacity every day).
Rules
1. Packages must be shipped in the given order (you cannot reorder them).
2. Each day, you load packages onto the ship in order, adding as many as possible without exceeding capacity C.
3. If adding the next package would exceed capacity, you stop loading for the day and ship what you have loaded.
4. You can ship at most one load per day.
5. Every package must be shipped exactly once.
Task
Return the minimum ship capacity C such that all packages can be shipped within D days.
Examples
1. weights = [1,2,3,4,5,6,7,8,9,10], D = 5 → 15
2. weights = [3,2,2,4,1,4], D = 3 → 6
Constraints
* 1 <= len(weights) <= 50_000
* 1 <= weights[i] <= 500
* 1 <= D <= len(weights)
 ```

## Round 2 — Cloud

a. Have you deployed any resources or automated anything in cloud infrastructure (VMs, setting up a cluster)?
b. How does kubernates verify if the pods are running healthy or not?
c. Have you worked with CloudFormation templates / ARM templates?

### High Level Design
```
Design a service where an internal user calls a REST API to provision a compute cluster (10 VMs + networking) in a customer account. The idea is that user should be able to provision the cluster and it should be able to let say deploy changes to those vms and those vms should be able to talk to other vms in the network. We already have the resources — focus on the networking: how do the VMs talk to each other over a port (private subnet setup)?  What networking configuration is needed so the 10 VMs can communicate? What things are required to create a subnet? Define an address range. Can you define a CIDR table?
```




### Feedback Question Given by Ankit(Things I asked to study for better cloud understanding?)
1. Setup in environments
2. Subnet
3. Automate setup for cluster
4. How can I install
5. What is netting(how does cloud talks to outside resource)