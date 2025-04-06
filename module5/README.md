# **Module 5: Intermediate SQL & Query Optimization**

***  

---

<!-- TOC -->
* [**Module 5: Intermediate SQL & Query Optimization**](#module-5-intermediate-sql--query-optimization)
  * [**Aggregate Functions And Grouping**](#aggregate-functions-and-grouping)
  * [Subqueries in SQL](#subqueries-in-sql)
  * [Set Operations](#set-operations)
  * [Views](#views)
  * [Transactions](#transactions)
  * [Performance Optimization and Indexing](#performance-optimization-and-indexing)
    * [EXPLAIN ANALYZE - Analyzing Queries and Identifying Bottlenecks](#explain-analyze---analyzing-queries-and-identifying-bottlenecks)
    * [Writing Efficient SQL Queries](#writing-efficient-sql-queries)
    * [Database Design & Server Configuration](#database-design--server-configuration)
    * [Indexing](#indexing-)
  * [Hands-on Exercise 1](#hands-on-exercise-1)
<!-- TOC -->

---
**The following queries use the Northwind Sample Database.**

---

## **Aggregate Functions And Grouping**

**Aggregate Functions**

Aggregate functions perform calculations on a set of rows and return a single value, 
such as COUNT(), MAX(), MIN(), SUM(), AND AVG().

```sql
SELECT COUNT(*) FROM "customers";                         -- Counts all customer records (including NULL values)
SELECT COUNT("Region") FROM "customers";                  -- Counts non-NULL values in the Region column only
SELECT MAX("UnitPrice") FROM "products";                  -- Returns the highest product price in the table
SELECT MIN("UnitPrice") FROM "products";                  -- Returns the lowest product price in the table
SELECT SUM("UnitPrice") FROM "products";                  -- Calculates the sum of all product prices
SELECT AVG("UnitPrice") FROM "products";                  -- Calculates the average price across all products

```

Key differences:

* COUNT(*) counts all rows, while COUNT(column) counts non-NULL values in that column

* MAX/MIN work with numeric, date, and string data types

* SUM/AVG only work with numeric columns

* All these aggregate functions ignore NULL values (except COUNT(*))

**Grouping - `GROUP BY`**

Grouping in SQL groups the query result based on one or more columns,
typically using the `GROUP BY` clause.
Aggregate functions are often used with `GROUP BY` to perform calculations on each group.

In a grouping operation, the selected fields must either be the grouped field(s) or aggregate functions (such as COUNT, SUM, AVG, etc.).

```sql

-- Groups all rows in the products table by the CategoryID column and returns each unique CategoryID once.
SELECT "CategoryID"
FROM products
GROUP BY "CategoryID"
ORDER BY "CategoryID";

-- Count products in each category
SELECT "CategoryID", COUNT(*) AS ProductCount
FROM products
GROUP BY "CategoryID";
-- This query groups the products by CategoryID and counts how many products are in each group.

-- Average unit price per category
SELECT "CategoryID", AVG("UnitPrice") AS AveragePrice
FROM products
GROUP BY "CategoryID";
-- This calculates the average unit price for products in each category.

-- Total price of products per category
SELECT "CategoryID", SUM("UnitPrice") AS TotalPrice
FROM products
GROUP BY "CategoryID";
-- This gives the total of all unit prices for each category.

-- Minimum and maximum unit price in each category
SELECT "CategoryID",
       MIN("UnitPrice") AS MinPrice,
       MAX("UnitPrice") AS MaxPrice
FROM products
GROUP BY "CategoryID";
```

**Filtering Groups - HAVING**

The HAVING clause filters the results after grouping, while WHERE filters rows before grouping.

```sql
SELECT "CategoryID", COUNT(*) AS "ProductCount"
FROM "products"
GROUP BY "CategoryID"
HAVING COUNT(*) > 10;  -- Only show categories with more than 10 products
-- HAVING COUNT(*) > 5 AND AVG("UnitPrice") < 50;


SELECT "CategoryID", COUNT(*) AS "ProductCount"
FROM "products"
GROUP BY "CategoryID"
HAVING "CategoryID"=1;  -- Only show category 1
  
  
--An alternative and better approach is filtering records before grouping, if possible.
SELECT "CategoryID", COUNT(*) AS "ProductCount"
FROM "products"
WHERE "CategoryID"=1  -- Filters before grouping
GROUP BY "CategoryID";
```

## Subqueries in SQL

```sql
-- 1. SINGLE-VALUE SUBQUERY (Scalar)
    -- Returns a single value (a single column and row).
    -- Can be used in WHERE, HAVING, or SELECT clauses.

-- Find products with a unit price less than the average unit price.
SELECT "ProductID", "UnitPrice" FROM "products"
WHERE "UnitPrice" < (SELECT AVG("UnitPrice") FROM "products");

-- List each product's unit price and its difference from the average price.
SELECT
    "ProductName",
    "UnitPrice",
    "UnitPrice" - (
        SELECT AVG("UnitPrice")
        FROM "products"
    ) AS "PriceDiffFromAvg"
FROM "products";

-- 2. MULTI-VALUE SUBQUERY
    -- Returns multiple values (one or more rows, but one column).
    -- Commonly used with operators like IN, NOT IN, ALL, ANY, and EXISTS.

-- Find products supplied by suppliers in London.
SELECT "ProductName"
FROM products
WHERE "SupplierID" IN (
    SELECT "SupplierID" FROM suppliers WHERE "City" = 'Tokyo'
);

-- 3. MULTI-COLUMN SUBQUERY
-- Returns multiple values (one or more rows and multiple columns).
-- Typically used with IN or EXISTS operators.

-- Find orders placed by customers in 'Japan' and 'Argentina'.
SELECT "OrderID", "CustomerID"
FROM orders
WHERE ("CustomerID") IN (
    SELECT "CustomerID" FROM customers WHERE "Country" IN ('Japan', 'Argentina')
);

-- 4. CORRELATED SUBQUERY

-- The outer query starts examining each row in the customers table
-- For each customer, it executes the inner subquery
-- The subquery checks if there's any order (SELECT *) where the order's CustomerID matches the current customer's CustomerID
-- If the subquery returns any rows (EXISTS is true), the customer is included in results
-- If no rows are returned (EXISTS is false), the customer is excluded
-- This correlated subquery behaves exactly like a nested loop in programming
-- The EXISTS version is often most efficient when you only need to check for existence of related records.

SELECT "CustomerID", "CompanyName", "ContactName"
FROM "customers"
WHERE EXISTS
          (SELECT * FROM "orders" WHERE "customers"."CustomerID" = "orders"."CustomerID");

-- Alternative formulations

SELECT DISTINCT c."CustomerID", c."CompanyName", c."ContactName"
FROM "customers" c
         JOIN "orders" o ON c."CustomerID" = o."CustomerID";

SELECT "CustomerID", "CompanyName", "ContactName"
FROM "customers"
WHERE "CustomerID" IN (SELECT "CustomerID" FROM "orders");

```

## Set Operations

These operators combine the results of two or more SELECT statements.

**For set operations, the SELECT statements must have the same number of columns and 
compatible data types in the corresponding columns.**

* UNION: Returns all distinct rows from both queries.
* UNION ALL: Returns all rows from both queries, including duplicates.
* INTERSECT: Returns rows that are present in both queries.
* EXCEPT (or MINUS in some other SQL dialects): Returns rows that are present in the first query but not in the second.

```sql
-- Combine two SELECT result sets and remove duplicates, then sort by the second column (Country).
SELECT "CompanyName", "Country" FROM "customers"
UNION
SELECT "CompanyName", "Country" FROM "suppliers"
ORDER BY 2;


-- Combine two SELECT result sets and include duplicates, then sort by the first column (CompanyName).
SELECT "CompanyName", "Country" FROM "customers"
UNION ALL
SELECT "CompanyName", "Country" FROM "suppliers"
ORDER BY 1;

-- Combine two SELECT result sets and return only common rows, then sort by the second column (Country).
-- INTERSECT returns only rows present in both result sets.
SELECT "CompanyName", "Country" FROM "customers"
INTERSECT
SELECT "CompanyName", "Country" FROM "suppliers"
ORDER BY 2;


-- Combine two SELECT result sets and return rows from the first set that are not in the second set, then sort by the second column (Country).

SELECT "CompanyName", "Country" FROM "customers"
EXCEPT
SELECT "CompanyName", "Country" FROM "suppliers"
ORDER BY 2;
```

## Views
A view is a virtual table based on a SELECT query. They simplify complex queries and enhance reusability.

Views are dynamic because they generate results on the fly by executing the underlying query each time 
they are accessed, reflecting the most current data.


**Benefits of using Views:**

* Simplification: Hide complex joins and calculations, making queries easier to write and understand.
* Code Reusability: Define a query once and reuse it multiple times.
* Security: Restrict access to certain columns or rows without granting direct access to the base tables.

```sql
-- To define or modify a view
CREATE OR REPLACE VIEW "public"."OrderCustomerEmployee" AS
SELECT "orders"."OrderID",
  "orders"."OrderDate",
  "customers"."CompanyName",
  "customers"."ContactName",
  "employees"."FirstName",
  "employees"."LastName"
FROM "orders"
INNER JOIN "employees" ON "orders"."EmployeeID" = "employees"."EmployeeID"
INNER JOIN "customers" ON "orders"."CustomerID" = "customers"."CustomerID";

-- Querying Views - just like a table
SELECT * FROM "OrderCustomerEmployee";

-- To remove a view
DROP VIEW "OrderCustomerEmployee";
```

---

## Transactions

A transaction is a sequence of one or more SQL operations that are treated as a single logical unit of work. 

Transactions ensure data integrity by adhering to the ACID properties:
* Atomicity: All operations within a transaction either succeed completely (commit) or fail completely (rollback). There's no partial execution.
* Consistency: A transaction brings the database from one valid state to another valid state. It preserves all defined rules and constraints.
* Isolation: Concurrent transactions should not interfere with each other. The effects of one transaction should not be visible to others until it is committed.
* Durability: Once a transaction is committed, its changes are permanent and will survive system failures.

```sql
-- Start transaction
BEGIN;

-- 1. Add order
INSERT INTO orders ("OrderID", "CustomerID", "EmployeeID", "OrderDate")
VALUES (10247, 'ALFKI', 5, CURRENT_DATE);

-- 2. Add order details
INSERT INTO order_details ("OrderID", "ProductID", "UnitPrice", "Quantity", "Discount")
VALUES (10247, 11, 14.00, 12, 0);

-- 3. Update inventory
UPDATE products
SET "UnitsInStock" = "UnitsInStock" - 12
WHERE "ProductID"  = 11;

-- End of transaction. Make changes permanent.
COMMIT;
```


## Performance Optimization and Indexing

Performance Optimization: Ensuring that your SQL queries execute efficiently and return results quickly is crucial for 
application responsiveness and scalability. Performance optimization in DBMS involves a range of techniques, 
from writing efficient SQL queries to leveraging database features such as indexing, appropriate normalization, 
and fine-tuning server configuration.


### EXPLAIN ANALYZE - Analyzing Queries and Identifying Bottlenecks

**EXPLAIN:** Shows the estimated execution plan that PostgreSQL will use for your query. Analyze the plan to see which 
indexes are being used, the order of operations (e.g., joins, scans), and the estimated costs. Look for full table 
scans on large tables when you expect an index to be used.

**EXPLAIN ANALYZE:** Actually executes the query and provides detailed statistics about the execution, 
including the actual time taken for each step, the number of rows processed, and buffer usage. This is crucial for 
identifying real performance bottlenecks.

```sql
EXPLAIN
SELECT * FROM "customer";

EXPLAIN ANALYSE
SELECT * FROM "customer";
```
**Query optimization is the process where PostgreSQL chooses the most efficient 
execution plan for a SQL statement based on cost estimates.**

Analyze the query plan:
* Seq Scan (slow full-table scan) → Needs an index.
* Index Scan (fast indexed lookup).
* Nested Loop vs Hash Join vs Merge Join (join strategies).


### Writing Efficient SQL Queries

* Select Only Necessary Columns
  * Avoid `SELECT *` and fetch only required columns
  * `SELECT *` statement results in higher I/O, memory, CPU, and energy usage, and can significantly 
  increase query execution time.
  
* Filter Data Early with WHERE Clauses
  * Apply filters as early as possible 
  * This reduces the number of rows the database needs to process in subsequent operations like joins and aggregations.

* Optimize JOIN Conditions
  * Ensure join columns are indexed: Joins are often performance-critical. Having indexes on the columns used in ON clauses 
  can dramatically speed up the matching process. 
  * Use the appropriate JOIN type: Understand the differences between INNER JOIN, LEFT JOIN, RIGHT JOIN, and 
  FULL OUTER JOIN and use the one that accurately reflects your data retrieval needs. Unnecessary outer joins can 
  lead to performance overhead.
  
* Use LIMIT and pagination to Restrict Results. Reduces the result set for large queries
  ```sql
  select * from orders
  order by "OrderID" DESC
  limit 10; -- list the last 10 orders
  
  select * from orders
  order by "OrderID" DESC
  limit 10 offset 0; -- list the 10 orders of first page
  ```
* Avoid Operations Inside WHERE Clauses on Indexed Columns
  ```sql
  -- Inefficient (functions can prevent index usage):
  SELECT * FROM orders WHERE DATE_PART('year', "OrderDate") = 2024;
  
  -- Efficient
  SELECT * FROM orders WHERE "OrderDate" >= '2024-01-01' AND "OrderDate" < '2025-01-01';
  ```
* Use joins instead of subqueries (if better).	Joins are often faster and more readable.
  * Using a correlated subquery with EXISTS can lead to better performance and faster execution, particularly 
  when checking for the existence of related data.

* Use LIKE Patterns Carefully
  * Leading wildcards (%value) are expensive - cannot efficiently use standard B-tree indexes. The database has to scan the entire column.
  * Trailing wildcards (value%) are generally more efficient
  * Consider Full-Text Search
    * For complex text searching requirements involving leading wildcards or more sophisticated pattern matching, 
    explore PostgreSQL's full-text search capabilities (using tsvector and tsquery with GIN or GiST indexes).

* Use indexes on columns that are frequently used in filtering (WHERE), joining (JOIN ON), sorting (ORDER BY), and grouping (GROUP BY) operations.

* Regularly run VACUUM to free up storage and ANALYZE to update statistics that help the query planner choose efficient execution plans.
  * Use VACUUM ANALYZE after bulk inserts, updates, or deletes to keep the database performance optimal.
  
### Database Design & Server Configuration
* A well-normalized database schema can significantly improve query performance. It reduces data redundancy and 
improves data integrity, which can lead to more efficient queries.

* Clearly defined relationships between tables (using primary and foreign keys) allow the database to efficiently perform joins.

* Choosing the correct data types for your columns optimizes storage and can improve query performance.

* Server settings (e.g., shared_buffers, work_mem) can impact performance.


### Indexing 

An index is a data structure that improves the speed of data retrieval operations on a database table. 


**Types of Indexes (Common in PostgreSQL)**

* B-tree Indexes (Default): Suitable for equality and range comparisons (=, >, <, >=, <=, BETWEEN, LIKE with no leading wildcard).
* Hash Indexes: Useful for equality comparisons (=).
* GIN Indexes (Generalized Inverted Index): Effective for full-text search, array searches, and indexing composite types.
* GiST Indexes (Generalized Search Tree): Suitable for geometric and spatial data, as well as other complex data types.

**Defining Index**
```sql
-- Construct a basic index on a single column. The default type is B-tree
CREATE INDEX idx_customers_country ON customers("Country");

-- Construct a hash index on ProductName column of products table.
CREATE INDEX idx_product_name_hash
  ON products USING HASH ("ProductName");
```

**Example**

```sql
-- Define a new table called person
CREATE TABLE person (
    person_id SERIAL,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    registration_date TIMESTAMP DEFAULT '2019-01-01 01:00:00',
    CONSTRAINT person_pk PRIMARY KEY(person_id)
);

-- Function to insert random data into the person table
CREATE OR REPLACE FUNCTION insert_data(record_count INTEGER)
RETURNS VOID
AS  
$$
BEGIN   
    IF record_count > 0 THEN
        FOR i IN 1 .. record_count LOOP
            INSERT INTO person (first_name, last_name, registration_date) 
            VALUES (
                substring('ABCÇDEFGĞHIiJKLMNOÖPRSŞTUÜVYZ' FROM ceil(random()*10)::smallint FOR ceil(random()*20)::smallint), 
                substring('ABCÇDEFGĞHIiJKLMNOÖPRSŞTUÜVYZ' FROM ceil(random()*10)::smallint FOR ceil(random()*20)::smallint),
                NOW() + (random() * (NOW() + INTERVAL '365 days' - NOW()))
            );
        END LOOP;
    END IF; 
END;
$$
LANGUAGE 'plpgsql' SECURITY DEFINER;

-- Insert 100,000 random rows
SELECT insert_data(100000);

-- Query without index: Sequential scan
EXPLAIN ANALYZE
SELECT * FROM person
WHERE first_name = 'EXAMPLE';  -- One of the rows should have first_name set to 'EXAMPLE'

-- Output (without index):
-- Seq Scan on person  (cost=0.00..2107.00 rows=496 width=38) (actual time=20.214..20.215 rows=1 loops=1)
--   Filter: (first_name = 'EXAMPLE')
--   Rows Removed by Filter: 99999
-- Planning Time: 0.085 ms
-- Execution Time: 20.237 ms

-- Create index on first_name column
CREATE INDEX first_name_idx ON public.person USING btree(first_name ASC NULLS LAST);

-- Query with index: Bitmap Index Scan
EXPLAIN ANALYZE
SELECT * FROM person
WHERE first_name = 'EXAMPLE';  -- One of the rows should have first_name set to 'EXAMPLE'

-- Output (with index):
-- Bitmap Heap Scan on person  (cost=12.26..784.32 rows=496 width=38) (actual time=0.052..0.053 rows=1 loops=1)
--   Recheck Cond: (first_name = 'EXAMPLE')
--   Heap Blocks: exact=1
--   -> Bitmap Index Scan on first_name_idx  (cost=0.00..12.14 rows=496 width=0) (actual time=0.045..0.046 rows=1 loops=1)
--        Index Cond: (first_name = 'EXAMPLE')
-- Planning Time: 0.123 ms
-- Execution Time: 0.083 ms

```

---
[Hands-on Exercise 1](./exercises)
---
