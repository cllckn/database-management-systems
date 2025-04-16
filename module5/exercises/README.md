# Hands-On Exercise 1: Cancelling an Order in Northwind (Using Transactions)

## Objective

Write a SQL transaction that cancels an existing order from the Northwind sample database. The process must:

- Restock the products included in the canceled order.
- Delete all related line items from the `order_details` table.
- Delete the order from the `orders` table.

Use a transaction to ensure that all steps are executed together and maintain data integrity.

## Context

The following tables from the Northwind database are used:

- `orders`: stores the general information about each order.
- `order_details`: stores the line items associated with each order.
- `products`: contains inventory details, including `UnitsInStock`.

You will work with **Order ID 10249**, assuming it has not been shipped yet.

## Instructions

1. Begin a transaction.
2. Restock the products included in the order using data from `order_details`.
3. Delete the related records in `order_details`.
4. Delete the order from the `orders` table.
5. Commit the transaction.

---





# Hands-On Exercise 2: Indexing for Query Optimization

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

---






