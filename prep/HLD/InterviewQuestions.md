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