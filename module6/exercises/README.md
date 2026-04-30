# Hands-On Exercise 1: Indexing for Query Optimization

## Objective:

Understand how indexing affects query performance.


## Task 1 — Define a New Database

1. Open your database client (pgAdmin, intelliJ, etc.).
2. Construct a new empty database named:

```
pagila_lab
```

---

## Task 2 — Import the Northwind Database

[Download **Pagila sample database**](../../resources/dbs/pagila.backup)



1. Connect to the `pagila_lab` database using your database client (pgAdmin, intelliJ, etc.).
2. Import (restore) the Pagila sample database.

The imported database should include several tables.


---

## Task 3 — Add index to speed up the queries

1. **Query Without Index:**

   Run a `SELECT` query on the `rental` table that filters rows by a specific `customer_id` value using `EXPLAIN ANALYZE`.

   Record the scan type (such as sequential scan), estimated cost, and execution time.

2. **Add Index:**

   Define a B-tree index on the `customer_id` field on the `rental` table.


3. **Query With Index:**

   Run the same query again using `EXPLAIN ANALYZE`.

   Compare the scan type, cost, and execution time with the previous result.

