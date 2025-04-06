# Hands-On Exercise1: Indexing for Query Optimization

## Objective:
Understand how indexing affects query performance. You will:
- Run a query on the `orders` table using a filter on `customerid`
- Observe performance using `EXPLAIN ANALYZE` before and after indexing
- Add an index on the `customerid` field
- Compare execution plans with and without indexing



## Steps:

1. **Query Without Index:**

   Run a `SELECT` query on the `orders` table that filters rows by a specific `customerid` value using `EXPLAIN ANALYZE`.

   Record the scan type (such as sequential scan), estimated cost, and execution time.

2. **Add Index:**

   Define a B-tree index on the `customerid` field in the `orders` table.


3. **Query With Index:**

   Run the same query again using `EXPLAIN ANALYZE`.

   Compare the scan type, cost, and execution time with the previous result.

