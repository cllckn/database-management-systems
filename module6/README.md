# Module 6: SQL Programming

<!-- TOC -->
* [Module 6: SQL Programming](#module-6-sql-programming)
  * [Introduction to SQL Programming](#introduction-to-sql-programming-)
  * [Functions and Stored Procedures in PL/pgSQL](#functions-and-stored-procedures-in-plpgsql)
  * [Control Structures in PL/pgSQL](#control-structures-in-plpgsql)
  * [Triggers](#triggers-)
  * [Hands-on Exercise 1](#hands-on-exercise-1)
<!-- TOC -->

## Introduction to SQL Programming 

- **Standard SQL** is primarily a **declarative language** designed for constructing databases and for querying 
and manipulating data.

- It **does not include full programming constructs** such as control flow (`IF`, `LOOP`, `WHILE`) or procedural 
logic required for complex business operations.

- To overcome this limitation, **RDBMSs like PostgreSQL** provide extended SQL programming languages such as 
**PL/pgSQL** that support:
    - Variables and constants
    - Conditional statements (`IF`, `CASE`)
    - Looping constructs (`LOOP`, `WHILE`, `FOR`)
    - Error handling
    - Modular code through functions and procedures

- These procedural extensions allow developers to **write SQL programs** directly in the database, enabling:
    - Implementation of business logic
    - Automation of workflows
    - Improved performance through reduced network communication

- Additionally, SQL programs in PostgreSQL are **stored as precompiled code**, which means:
    - Parsing, analysis, and planning are done once at creation time
    - Execution is faster during runtime, especially for repeated calls


**Key Advantages of SQL Programming**

* Code Reusability: PL/pgSQL code can be encapsulated into functions and stored procedures, making it reusable across 
different applications.
* Performance: Executing code within the database can be faster than sending multiple SQL statements from a client application, 
as it reduces network traffic. SQL programs (functions, procedures, triggers) in PostgreSQL are stored precompiled, 
meaning the database parses, analyzes, and plans the execution once, and stores the compiled version.
This results in faster execution times for repeated calls, as the overhead of parsing and planning is avoided each time.

## Functions and Stored Procedures in PL/pgSQL
Both functions and stored procedures are named blocks of PL/pgSQL code that perform specific tasks.

**Functions**

Can be used in SQL expressions: Functions can be called within SELECT statements, WHERE clauses, and other parts of SQL queries.

Must return a value (usually): A function is designed to take input parameters and return a result.

**Stored Procedures**

Called using the CALL statement: Stored procedures are invoked explicitly using the CALL statement.

Can return multiple values (using OUT parameters) or no value: Procedures are more flexible in terms of what they return. 
They can modify data, perform actions, and optionally return data.

More focused on actions: Procedures are often used for data manipulation, transaction control, and complex operations.

**Summary**
- Use a **function** for a reusable operation that returns a result and can be embedded in SQL queries.
- Use a **stored procedure**  to perform a sequence of actions, especially when including transaction control.



* Defining and calling a function
```sql
-- A function that adds two numbers and returns the result
CREATE OR REPLACE FUNCTION add_numbers(a INTEGER, b INTEGER)
RETURNS INTEGER AS $$
BEGIN
    RETURN a + b;
END;
$$ LANGUAGE plpgsql;

-- Call the function
SELECT add_numbers(5, 3);  -- Output: 8
```
* Defining and calling a stored procedure

```sql
-- A procedure that adds two numbers and stores the result in an OUT parameter
CREATE OR REPLACE PROCEDURE add_numbers_proc(
    IN a INTEGER,
    IN b INTEGER,
    OUT result INTEGER
)
AS $$
BEGIN
    result := a + b;
END;
$$ LANGUAGE plpgsql;

-- Call the function
CALL add_numbers_proc(5, 3, result => NULL);  -- The result will be returned via the OUT parameter

```

## Control Structures in PL/pgSQL
PL/pgSQL provides a variety of control structures to manage the flow of execution within your functions and procedures.

```sql
-- Create or replace a function that demonstrates control structures
CREATE OR REPLACE FUNCTION demo_control_structures(looplimit INT)
    RETURNS VOID AS $$
DECLARE
    i INT;
    number INT := 3;  -- Sample number for CASE WHEN
BEGIN

    -------------------------------------------------------------------
    -- IF-ELSE Statements
    -- The IF-ELSE statement allows you to execute different blocks
    -- of code based on conditions.
    -------------------------------------------------------------------
    IF looplimit < 0 THEN
        RAISE EXCEPTION 'limit value cannot be negative.';
    ELSE
        RAISE NOTICE 'Starting loops with limit %', looplimit;
    END IF;

    -------------------------------------------------------------------
    -- CASE WHEN Statements:
    -- Used for conditional branching with multiple possibilities.
    -- Offers flexibility when handling different cases for a variable.
    -------------------------------------------------------------------
    RAISE NOTICE 'CASE WHEN example:';
    CASE number
      WHEN 1 THEN
        RAISE NOTICE 'Number is 1';
      WHEN 2 THEN
        RAISE NOTICE 'Number is 2';
      WHEN 3 THEN
        RAISE NOTICE 'Number is 3';
      ELSE
        RAISE NOTICE 'Number is something else';
      END CASE;

    -------------------------------------------------------------------
    -- Loop Statements
    -- PL/pgSQL provides several loop structures for repetitive execution.
    -------------------------------------------------------------------

    -------------------------------------------------------------------
    -- FOR Loop:
    -- Iterates over a fixed range of values.
    -- Useful when the number of iterations is known in advance.
    -------------------------------------------------------------------
    RAISE NOTICE 'FOR LOOP from 1 to limit';
    FOR i IN 1..looplimit LOOP
        RAISE NOTICE 'FOR LOOP value: %', i;
    END LOOP;

    -------------------------------------------------------------------
    -- WHILE Loop:
    -- Executes a block of code repeatedly as long as a condition is true.
    -- Useful when the number of iterations is unknown and condition-based.
    -------------------------------------------------------------------
    i := 1;
    RAISE NOTICE 'WHILE LOOP from 1 to limit';
    WHILE i <= looplimit LOOP
        RAISE NOTICE 'WHILE LOOP value: %', i;
        i := i + 1;
    END LOOP;

    -------------------------------------------------------------------
    -- LOOP...EXIT WHEN:
    -- DO LOOP in PL/pgSQL runs indefinitely until explicitly exited.
    -- EXIT WHEN is used to break out of the loop based on a condition.
    -------------------------------------------------------------------
    i := 1;
    RAISE NOTICE 'LOOP with EXIT WHEN from 1 to limit';
    LOOP
        EXIT WHEN i > looplimit;
        RAISE NOTICE 'LOOP EXIT WHEN value: %', i;
        i := i + 1;
    END LOOP;

END;
$$ LANGUAGE plpgsql;

```

```sql
-- Example 1: This will raise an exception due to negative input
SELECT demo_control_structures(-1);

-- Example 2: This will execute all loops and case logic for limit = 5
SELECT demo_control_structures(5);

```

**Examples**


---
**The following queries are based on the Pagila sample database.**

---

* Record Traversal in a SELECT Result Set 

```sql
-- Define or replace a function that iterates over the result set of a SELECT query
CREATE OR REPLACE FUNCTION iterate_records()
RETURNS TEXT
AS
$$
DECLARE
    currentCustomer customer%ROWTYPE;  -- Declare a variable 'cust' with the same structure as a row in the 'customer' table
    result TEXT;            -- Declare a variable to accumulate the final result as text
BEGIN
    result := '';           -- Initialize the result variable to an empty string

    -- Loop through each row returned by the SELECT * FROM customer query
    FOR currentCustomer IN SELECT * FROM customer LOOP
        -- Concatenate the customer ID and first name with a tab and newline character to format the result
        result := result || currentCustomer.customer_id || E'\t' || currentCustomer.first_name || E'\r\n';
    END LOOP;

    -- Return the final formatted string containing all customer IDs and first names
    RETURN result;
END;
$$
LANGUAGE 'plpgsql';

```
```sql
SELECT iterate_records();

```

* Returning a Table

```sql
-- Define or replace a function that returns a table
CREATE OR REPLACE FUNCTION search_staff(staffid INT)
RETURNS TABLE(id INT, firstname VARCHAR(40), lastname VARCHAR(40)) 
AS 
$$
BEGIN
    -- Return a query that selects columns from the staff table
    RETURN QUERY 
    SELECT staff_id, first_name, last_name
    FROM staff
    WHERE staff_id = staffid;
END;
$$
LANGUAGE "plpgsql";

```

```sql
-- Call the function and display the returned table
SELECT * FROM search_staff(1);

```

* Function Calling Another Function Example

This example shows how to call another function from within a PL/pgSQL function. The outer function fetches staff 
information and calculates the total payment amount for that staff member.

```sql
-- Define or replace a function that returns a formatted string
-- showing staff ID, name, and their total payments
CREATE OR REPLACE FUNCTION public.payment_summary(staffID INTEGER)
RETURNS TEXT
LANGUAGE "plpgsql"
AS
$$
DECLARE
    staff_record RECORD;     -- Will hold the row returned from the inner function
    total_amount NUMERIC;    -- To store the total amount of payments
BEGIN
    -- Call another function (search_staff) and store the returned row into staff_record
    staff_record := search_staff(staffID);

    -- Calculate the total payment amount made by the staff member
    total_amount := (
        SELECT SUM(amount) 
        FROM payment 
        WHERE staff_id = staffID
    );

    -- Return a formatted string with staff ID, name, and total payment
    RETURN staff_record.id || E'\t' || staff_record.firstname || E'\t' || total_amount;
END
$$;

```

```sql
-- Execute the function for staff ID 2
SELECT payment_summary(2);

```


## Triggers 

Triggers are used to automatically perform predefined tasks in response to specific events such as INSERT, UPDATE, or DELETE operations on a table. They help enforce rules, maintain audit trails, or perform automatic updates in a consistent and reliable manner.

A trigger definition typically consists of two parts:

* Trigger Function

  This is a user-defined function that contains the logic to be executed automatically. It can include operations such as logging changes, validating data, or updating related tables.

* Event Definition

  This specifies the event that activates the trigger (e.g., AFTER INSERT, BEFORE UPDATE, or AFTER DELETE) and the table to which the trigger is attached. It also specifies whether the trigger fires for each row or once per statement.



**Advantages of Using Triggers**
* Maintaining Data Integrity:
  * Triggers offer an alternative to enforce data consistency.
  * Example: When a product is sold, reduce its stock count automatically.

* Alternative to Scheduled Tasks:
Some automated tasks that are usually scheduled (e.g., via CRON) can instead be done instantly using triggers.

* Automatic Execution:
  * Tasks are performed immediately, before or after data-modifying operations, without requiring explicit calls.
  * Example: When a customer record is deleted, automatically move the data to an OldCustomers table.

* Useful for Logging Activities:
  * Triggers can be used to record changes in a separate log table for tracking purposes.
  * Example: When a user changes their password, log the activity in an audit table.


---
**The following queries are based on the Northwind sample database.**

---

**Trigger Example: Monitoring Unit Price Changes of Products**

This example demonstrates how to use a trigger in PostgreSQL to track changes in a product's unit price. When the 
price of a product is updated, the trigger automatically inserts a record into a logging table.

* Step 1: Add a new table for logging
```sql
CREATE TABLE public.ProductPriceChangeLog (
    log_id serial,                                -- Unique identifier for each change
    product_id SMALLINT NOT NULL,                 -- ID of the product whose price changed
    old_price REAL NOT NULL,                      -- The previous price
    new_price REAL NOT NULL,                      -- The new price after the update
    change_date TIMESTAMP NOT NULL,               -- Timestamp of when the change occurred
    CONSTRAINT PK_ProductPriceChangeLog PRIMARY KEY (log_id)
);

```

* Step 2: Define the Trigger Function
```sql
CREATE OR REPLACE FUNCTION track_product_price_change()
RETURNS TRIGGER 
AS
$$
BEGIN
    -- Check if the unit price is changed
    IF NEW."UnitPrice" <> OLD."UnitPrice" THEN
        -- Insert a new log entry into the tracking table
        INSERT INTO public.ProductPriceChangeLog(product_id, old_price, new_price, change_date)
        VALUES (OLD."ProductID", OLD."UnitPrice", NEW."UnitPrice", CURRENT_TIMESTAMP);
    END IF;

    RETURN NEW; -- Allow the update to proceed
END;
$$
LANGUAGE plpgsql;

```

* Step 3: Define the trigger

```sql
CREATE TRIGGER on_unit_price_change
BEFORE UPDATE ON products
FOR EACH ROW
EXECUTE FUNCTION track_product_price_change();

```

* Example Use Case:
  * This update triggers the function because the price is being changed.
  * A record is automatically inserted into ProductPriceChangeLog showing the change from the old price to 100.
```sql
UPDATE products
SET "UnitPrice" = 100
WHERE "ProductID" = 4;
```

**Before Clause**
```sql
-- Trigger Function: Pre-insert or pre-update validation and formatting
CREATE OR REPLACE FUNCTION customer_before_insert_or_update()
RETURNS TRIGGER 
AS
$$
BEGIN
    -- Convert the company name to uppercase
    NEW."CompanyName" := UPPER(NEW."CompanyName");

    -- Remove leading and trailing spaces from contact name
    NEW."ContactName" := TRIM(NEW."ContactName");

    -- Raise an error if city is NULL
    IF NEW."City" IS NULL THEN
        RAISE EXCEPTION 'City field cannot be null.';
    END IF;

    -- Allow the insert or update to proceed with the modified data
    RETURN NEW;
END;
$$
LANGUAGE plpgsql;
```
```sql
-- Define Trigger: Runs before insert or update on "customers"
CREATE TRIGGER customer_data_validation
BEFORE INSERT OR UPDATE ON "customers"
FOR EACH ROW
EXECUTE FUNCTION customer_before_insert_or_update();
```
```sql
-- Example: Insert without city (will raise exception)
INSERT INTO "customers" ("CustomerID", "CompanyName", "ContactName") 
VALUES ('45', 'XY Ltd.', '    Jane Roe     ');

-- Example: Insert with all required fields
INSERT INTO "customers" ("CustomerID", "CompanyName", "ContactName", "City") 
VALUES ('45', 'XY Ltd.', '    Jane Roe     ', 'Petropavlovsk');
```
```sql
-- Disable a specific trigger on the products table
ALTER TABLE "products"
DISABLE TRIGGER on_unit_price_change;

-- Re-enable the specific trigger
ALTER TABLE "products"
ENABLE TRIGGER on_unit_price_change;

-- Disable all triggers on the products table
ALTER TABLE "products"
DISABLE TRIGGER ALL;

-- Re-enable all triggers on the products table
ALTER TABLE "products"
ENABLE TRIGGER ALL;

-- Drop a specific trigger
DROP TRIGGER IF EXISTS on_unit_price_change ON "products";

```


---
[Hands-on Exercise 1](https://github.com/cllckn/database-management-systems/tree/main/module6/exercises)
---
