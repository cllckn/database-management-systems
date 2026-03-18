# Hands-On Exercise 1: Relational Schema Design and Constraint Implementation in PostgreSQL

## Objectives

In this exercise, you will design and implement a small relational database in PostgreSQL.  
The goal is to practice:

- Defining relational schemas
- Implementing primary keys (PK)
- Defining foreign keys (FK)
- Applying constraints (NOT NULL, UNIQUE, CHECK, DEFAULT)
- Testing referential integrity using INSERT and DELETE operations

---

## Scenario

A company manages its customers.

- Each **Customer** belongs to a **CustomerType** (e.g., Regular, Premium, Corporate).
- Each customer must be associated with a valid customer type.
- Referential integrity must be enforced between parent and child tables.

---

## Step 1 — Relational Schema

You are given the following relational schema.

---

### CustomerTypes

        customertypes (
            id INT PK,
            type_name VARCHAR(50) NOT NULL UNIQUE,
            discount_rate DECIMAL(5,2) CHECK (discount_rate >= 0 AND discount_rate <= 50)
        )

### Customers

        customers (
            id INT PK,
            customer_code VARCHAR(20) NOT NULL UNIQUE,
            full_name VARCHAR(100) NOT NULL,
            email VARCHAR(255) UNIQUE,
            type_id INT NOT NULL FK → customertypes(id),
            registration_date DATE DEFAULT CURRENT_DATE,
            credit_limit DECIMAL(12,2) CHECK (credit_limit >= 0)
        )



---

## Step 2 — Implementation Tasks

### Task 1 

* Construct a database named `ecommercedb`
* Define tables `customertypes` and `customers`
  - SERIAL primary keys
  - Properly named constraints
  - A FOREIGN KEY constraint

---

# Step 3 — Insert Sample Data

Insert at least **3 records** into each table.

### Example Data Requirements

#### CustomerTypes
- Regular (0%)
- Premium (10%)
- Corporate (20%)

#### Customers
- At least 3 customers
- Each must reference a valid CustomerType

---

# Step 4 — Relationship Testing

Perform the following tests and document the results.

---

### Test 1 — Valid Insert

Insert a new customer referencing an existing CustomerType.  
✔ Expected: Successful insertion.

---

### Test 2 — Invalid Foreign Key Insert

Try inserting a customer with a non-existing `type_id`.

✔ Expected: Foreign key violation error.

---

### Test 3 — Delete Parent (Restricted)

Attempt to delete a `CustomerType` that is still referenced by customers.

✔ Expected: Deletion rejected due to the FK constraint.

---

### Test 4 — Constraint Testing

Perform and observe:

- Insert a customer with duplicate `customer_code` → should fail.
- Insert a customer with duplicate `email` → should fail.
- Insert negative `credit_limit` → should fail.
- Insert CustomerType with discount > 50 → should fail.

---


# Hands-On Exercise 2: Implementation of a Banking Relational Schema in PostgreSQL

## Objective

In this exercise, students will:

1. Implement the given relational schema in PostgreSQL.
2. Apply all structural constraints (PK, FK, NOT NULL).
3. Implement business rules using:
    - UNIQUE constraints
    - CHECK constraints
    - DEFAULT values
4. Populate each table with **at least 3 valid records**.
5. Verify referential integrity and constraint enforcement.

---

## Relational Schema

**Construct a database named `bankingdb`**

```text
customer_types (
    id INT PK,
    type_code VARCHAR(20) NOT NULL,
    type_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    max_credit_limit DECIMAL(12,2)
)

customers (
    id INT PK,
    customer_code VARCHAR(20) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(30),
    date_of_birth DATE,
    type_id INT NOT NULL FK → customer_types(id),
    registration_date DATE,
    credit_limit DECIMAL(12,2)
)

account_types (
    id INT PK,
    type_code VARCHAR(20) NOT NULL,
    type_name VARCHAR(100) NOT NULL,
    minimum_balance DECIMAL(12,2)
)

accounts (
    id INT PK,
    account_number VARCHAR(30) NOT NULL,
    customer_id INT NOT NULL FK → customers(id),
    account_type_id INT NOT NULL FK → account_types(id),
    balance DECIMAL(15,2) NOT NULL,
    opening_date DATE NOT NULL,
    status VARCHAR(20)
)

transaction_types (
    id INT PK,
    type_code VARCHAR(20) NOT NULL,
    type_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    fee DECIMAL(10,2)
)

transactions (
    id INT PK,
    account_id INT NOT NULL FK → accounts(id),
    transaction_type_id INT NOT NULL FK → transaction_types(id),
    amount DECIMAL(15,2) NOT NULL,
    transaction_date TIMESTAMP,
    reference_number VARCHAR(50),
    status VARCHAR(20)
)
```

## Business Rules Including Constraints

1. customer_code, account_number, reference_number, and all type_code fields must be unique.
2. email must be unique per customer.
3. credit_limit, balance, minimum_balance, fee, and max_credit_limit must be non-negative.
4. amount in transactions must be strictly positive.
5. registration_date defaults to the current date if not provided. (`DEFAULT CURRENT_DATE`)
6. transaction_date defaults to the current timestamp if not provided. (`DEFAULT CURRENT_TIMESTAMP`)
7. status in accounts defaults to "ACTIVE".
8. status in transactions defaults to "COMPLETED".
9. A customer’s credit_limit must not exceed the max_credit_limit defined in their customer type.
10. An account balance must not fall below the minimum balance defined by its account type.


## Sample Data

**customer_types**

| id | type_code | type_name   | description                          | max_credit_limit |
|----|-----------|------------|--------------------------------------|------------------|
| 1  | REG       | Regular    | Standard retail banking customer     | 5000.00          |
| 2  | PRM       | Premium    | High-value customer with benefits    | 20000.00         |
| 3  | CORP      | Corporate  | Business or enterprise customer      | 100000.00        |

---

**account_types**

| id | type_code | type_name | minimum_balance |
|----|-----------|-----------|-----------------|
| 1  | SAV       | Savings   | 100.00          |
| 2  | CHK       | Checking  | 0.00            |
| 3  | BUS       | Business  | 1000.00         |

---

**transaction_types**

| id | type_code | type_name | description                         | fee  |
|----|-----------|----------|-------------------------------------|------|
| 1  | DEP       | Deposit  | Money deposited into account        | 0.00 |
| 2  | WDR       | Withdraw | Cash withdrawal from account        | 1.50 |
| 3  | TRF       | Transfer | Transfer between bank accounts      | 2.00 |


---




# Hands-on Exercise 3: SQL Querying with the Northwind Database

### Objective

The objective of this exercise is to practice fundamental **SQL querying skills** using the **Northwind sample database**.

Students will learn how to:

- Define a new database
- Import an existing database schema and data
- Write SQL queries using:
    - `SELECT`
    - `WHERE`
    - `DISTINCT`
    - `LIKE`
    - `BETWEEN`
    - `IN`
    - `INNER JOIN`
    - `LEFT JOIN`
    - `RIGHT JOIN`

---

# Part 1 — Database Setup

## Task 1 — Define a New Database

1. Open your database client (pgAdmin, intelliJ, or similar).
2. Construct a new empty database named:

```
northwind_lab
```

---

## Task 2 — Import the Northwind Database

[Download **Northwind sample database**](../../resources/dbs/northwind.backup)


After downloading the script:

1. Connect to the `northwind_lab` database using your database client (pgAdmin, intelliJ, or similar).
2. Import (restore) the Northwind sample database.

The imported database should include 15 tables.


---

# Part 2 — Basic SELECT Queries

## Task 3

Retrieve **all columns** from the `products` table.

---

## Task 4

Retrieve the following columns from the `products` table:

- `ProductName`
- `UnitPrice`
- `UnitsInStock`

---

## Task 5

Retrieve **all customers** from the `customers` table.

---

# Part 3 — DISTINCT

## Task 6

Retrieve the **distinct list of countries** from the `customers` table.

---

## Task 7

Retrieve the **distinct list of cities** where customers are located.

---

# Part 4 — WHERE Clause

## Task 8

Retrieve all products with a **UnitPrice greater than 50**.

---

## Task 9

Retrieve all customers located in **Germany**.

---

## Task 10

Retrieve orders placed after the year `'1996/01/01'`.

---

# Part 5 — LIKE Operator

## Task 11

Retrieve customers whose **CompanyName starts with 'A'**.

---

## Task 12

Retrieve products whose **ProductName contains the word 'chocolate'**.

---

## Task 13

Retrieve employees whose **LastName starts with 'D'**.

---

# Part 6 — BETWEEN Operator

## Task 14

Retrieve products whose **UnitPrice is between 20 and 40**.

---

## Task 15

Retrieve orders placed **between '1996-01-01' and '1996-10-30'**.

---

# Part 7 — IN Operator

## Task 16

Retrieve customers located in the following countries:

- Japan
- Spain

---

## Task 17

Retrieve products that belong to the following category IDs:

- 1
- 2
- 3

---

# Part 8 — INNER JOIN

## Task 18

Retrieve the following information by joining the `products` and `categories` tables:

- `ProductName`
- `CategoryName`

---

## Task 19

Retrieve the following information for each order by joining the `orders` and `customers` tables:

- `OrderID`
- `OrderDate`
- `CompanyName`

---

## Task 20

Retrieve the following information for each product by joining the `products` and `suppliers` tables:

- `ProductName`
- `CompanyName` (Supplier)

---

# Part 9 — LEFT JOIN

## Task 21

Retrieve all **customers and their orders**.

The result must include **customers who have not placed any orders**.

Tables to use:

- `customers`
- `orders`

---

## Task 22

Retrieve all **products and their categories**, including products that do not belong to any category.

Tables to use:

- `products`
- `categories`

---

# Part 10 — RIGHT JOIN

## Task 23

Retrieve all **orders and their associated customers**, ensuring that all orders appear in the result.

Tables to use:

- `orders`
- `customers`

---

## Task 24

Retrieve all **suppliers and the products they supply**, ensuring that all suppliers appear in the result.

Tables to use:

- `suppliers`
- `products`








---
