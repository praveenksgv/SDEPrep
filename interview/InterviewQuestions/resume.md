1. While migrating the redis architecture, how did you make sure that background jobs are running fine?

2. How did you implement the react so that the extracted data is always shown in json format? How did react knew that it what kinda of data it will get? Was extracted data always in json format?

3. Is fastapi async?? Is it block when serving a service? Specially when making a database call? If for one request it's not stopping, how the process again resume when the database calls ends.

4. A standard python question:
```
Problem statement
A distributor tracks stock per SKU per warehouse. You’re given records: {"sku", "warehouse", "stock", "reorder_point", "safety_stock"}. Return (sku, warehouse) pairs where stock <= reorder_point, ordered by urgency (largest shortfall first). Ties break alphabetically by sku. Shortfall = reorder_point - stock.

Example
records = [
    {"sku": "A100", "warehouse": "WH1", "stock": 40, "reorder_point": 50, "safety_stock": 20},
    {"sku": "A100", "warehouse": "WH2", "stock": 48, "reorder_point": 50, "safety_stock": 20},
    {"sku": "B200", "warehouse": "WH1", "stock": 10, "reorder_point": 30, "safety_stock": 10},
    {"sku": "C300", "warehouse": "WH1", "stock": 60, "reorder_point": 50, "safety_stock": 10},
]
# Output: [("B200", "WH1"), ("A100", "WH1"), ("A100", "WH2")]
```

5. Why to use sqlalchamy in your Nullaxis Project? How does it create a connection with mysql/postgres etc?

6. What is alembic migrations? Why to use it?

7. What is pydantic and why to use it?

8. what is fastapi builtin api documentation?

9. What is a counter cache in redis? what is a lua script? If I have to give coupans to 1000 people and I kept this number in redis, and 1M requests hit the redis together(at the same time), how will we handle it?

10. GET vs POST vs PUT vs PATCH vs DELETE request type.



12. What are the decorators in python?

13. What is a shallow copy and deep copy in python?

14. What is the maximum size of file that can be uploaded to amazon s3?

15. What is the maximum time an aws lambda function can run for?