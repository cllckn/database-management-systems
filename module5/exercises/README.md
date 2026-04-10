# Hands-on Exercise 1: Java Console Application – CRUD Operations on northwinddb

---

## Objective

The objective of this exercise is to develop a **Java console application** that performs **CRUD (Create, Read, Update, 
Delete)** operations on the `categories` collection in MongoDB.

---

## Prerequisite Setup — Define a New Database

1. Connect to MongoDB Atlas.
2. Construct a new empty database `northwind` and collection `categories`.
3. Insert the following records:
4. 
```json
[
  {
    "name": "electronics",
    "description": "description of electronics category"
  },
  {
    "name": "computer",
    "description": "description of electronics category"
  },
  {
    "name": "robotics",
    "description": "description of robotics category"
  }
]

```

---

## Task 1 — Understand the categories Table

The `categories` table includes:

- `name`
- `description`
- `_id` field is added by dbms automatically and can be used as primary key.

Students will perform CRUD operations on this table.

---

## Task 2 — Java Application Development

Develop a **Java console application** that interacts with MongoDB.


## Task 3 — Implement CRUD Operations



## Task 4 — Testing

Test your application by:

- Inserting multiple categories
- Updating existing records
- Deleting records
- Retrieving and verifying data
