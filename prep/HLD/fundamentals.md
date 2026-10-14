**Interview Flows**

1. Requirement gathering
    a. Availability
    b. Latency
    c. Consistency
    d. Partion Tolerance
    e. Query patterns(frequency etc.)
    f. reads vs writes
    g. scalability
2. List all core entities
3. API interface
4. Data Flow
5. High Level Design
6. Deep Dive



**Basic Matrics**

Consider an EC2 - 1000rps
Consider redis - 100k rps




**Core Components**
1. *API Gateway*
    a. Handles routing
    b. Authentication(Like does JWT token present or not)
    c. Handles Rate Limiting

2. *Load Balancer*
    a. Normally you don't show in the HLD diagram(Normally the services has a logical ip which then distributes the requests)
    b. Normally it comes after api gateways