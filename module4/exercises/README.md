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

        customertypes(
        id PK,
        type_name NOT NULL UNIQUE,
        discount_rate CHECK (discount_rate >= 0 AND discount_rate <= 50)
        )

### Customers

        customers(
        id PK,
        customer_code NOT NULL UNIQUE,
        full_name NOT NULL,
        email UNIQUE,
        type_id NOT NULL FK → customertypes(id),
        registration_date DEFAULT CURRENT_DATE,
        credit_limit CHECK (credit_limit >= 0)
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
