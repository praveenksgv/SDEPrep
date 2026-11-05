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


## CDN - Reduction of Network Latency
A **Content Delivery Network (CDN)** serves content from servers closer to users. AWS CloudFront uses *edge locations* around the world to reduce latency and the number of requests reaching the origin server, such as an S3 bucket.

For example, if an image is stored in S3 in Mumbai and a user in London requests it:

```text
User in London → nearby CloudFront edge → regional edge cache → S3 in Mumbai
```

CloudFront checks the nearby edge first. On a **cache hit**, it returns the image immediately. On a **cache miss**, it checks the regional edge cache and, if needed, fetches the image from S3. It then delivers the image and caches it for future requests. Requests only travel as far as necessary. [AWS CloudFront documentation](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/HowCloudFrontWorks.html)


Types of files normally served by a CDN:

- **Static website files:** CSS, JavaScript, fonts, HTML
- **Media:** videos, audio, images, downloadable files
- **Public API responses:** product catalogs, news feeds, and other shared data








## Cache Talking Points
When to Bring Up Caching
- Read-heavy workload
- Expensive queries
- High database CPU
- Latency requirements

How to Introduce Caching
1. Identify the bottleneck
2. Decide what to cache
3. Choose your cache architecture
4. Set an eviction policy
5. Address the downsides