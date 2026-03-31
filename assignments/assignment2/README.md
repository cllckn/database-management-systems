# Assignment 2: Library Automation System – SQL/NoSQL Implementation and Application Development



## Objective

The objective of this assignment is to extend the previously designed **Library Automation System** by:

1. Implementing the same database structure in **MongoDB (NoSQL)**
2. Developing **two separate Java applications**:
    - One integrated with **PostgreSQL**
    - One integrated with **MongoDB**
3. Performing **CRUD operations** on selected entities in both systems


---


## Required Tasks

---

## Task 1 – MongoDB Data Model Design

Transform the relational schema into a **MongoDB document model**.

### Requirements:

- Represent entities as **collections**
- Decide between:
    - **Embedding** (e.g., Book with Authors)
    - **Referencing** (e.g., Loans referencing Members)
- Justify your design decisions

**Example (Simplified)**

{
"_id": 1,
"title": "Database Systems",
"isbn": "123456",
"authors": [
{ "authorId": 1, "name": "John Doe" }
],
"categories": ["Computer Science"]
}

---

## Task 2 – MongoDB Implementation

Using MongoDB:

1. Construct a database named `library_db`
2. Define collections corresponding to your design
3. Insert sample documents:
    - At least **2 documents per collection**


---

## Task 3 – Java Application (PostgreSQL)

Develop a Java application connected to **PostgreSQL**.

### Requirements:

- Use **JDBC**
- Perform CRUD operations on **at least 1 tables**


**Required Operations:**

- Insert new records
- Retrieve records (SELECT)
- Update records
- Delete records

---

## Task 4 – Java Application (MongoDB)

Develop a separate Java application connected to **MongoDB**.

### Requirements:

- Use **MongoDB Java Driver**
- Perform CRUD operations on **at least 1 collections**

### Required Operations:

- Insert documents
- Retrieve documents (SELECT)
- Update documents
- Delete documents

---


## Deliverables

Submit a **single PDF report** including:

1. MongoDB Data Model Design
2. Screenshots or outputs of CRUD operations
3. Source code for both applications.


---


## Notes

- Use meaningful naming conventions
- Keep applications simple but functional
- Focus on correctness rather than appearance

---


## Evaluation Criteria
***
The assignment will be evaluated based on two primary components:

1) Project Implementation: The quality and effectiveness of the project you implement.

2) Oral Exam Performance: Your performance during the oral exam, which will take place during your lab class in
   Week 13( the week starting April 13, 2026 ).



## Oral Exam Requirement

The oral exam is **mandatory**.

### During the Oral Exam:

- Both Java applications must be **ready to run**
- PostgreSQL and MongoDB must be **running**
- Students must demonstrate:
    - CRUD operations
    - Understanding of design decisions

---

## Group Work

Students must complete the assignment **individually**.

---

## Report Structure

### 1. Cover Page

- Student Name
- Course Name
- Submission Date
- Assignment Title

### 2. Study Explanation

- Objective
- MongoDB Design Decisions
- Java Application Design
- CRUD Implementation Details


---

## Email Submission

Submit via email:

- Email: cceken@ku.edu.kz

### Subject Format:

dbms-assignment2-StudentFullName

### File Naming:

StudentFullName-report.pdf

---

## Late Submission and Oral Exam Policy

Students must submit their work **before the oral exam (Week 13 lab session)**.

- Late oral defense: allowed **1 week later**
- Penalty: **20% deduction**
- No further extensions will be granted

---

### By completing this assignment, students will gain practical experience in both relational and NoSQL database systems, as well as application-level data management.

***Good luck!***

