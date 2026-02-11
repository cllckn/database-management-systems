# Assignment 1: Library Automation System – Database Design and Implementation

### Objective

The objective of this assignment is to design and implement a **relational database** for a Library 
Automation System.

Students are expected to:

1. Analyze given business rules
2. Design an **ER Diagram using Crow’s Foot notation**
3. Convert the ER model into a **Relational Schema**
4. Implement the database structure (tables, fields, keys, constraints)
5. Insert sample rows into each table to test the design

The final design must demonstrate proper use of:
- One-to-Many (1:M) relationships
- Many-to-Many (M:N) relationships
- One-to-One (1:1) relationships
- Participation (mandatory / optional)
- At least one **Unary (Recursive) relationship**

---

## Business Rules (Initial Scope)

The following rules describe the core requirements of the system.  
Students may extend these rules as necessary to satisfy all modeling requirements.

### Library and Members

A **Member** can borrow many books, but each loan is associated with exactly one Member.

Each **Member** has:

- MemberID (unique)
- FirstName
- LastName
- Email
- Phone
- MembershipDate
- Status (Active, Suspended, etc.)

A Member may have **exactly one Library Card**.

Each **Library Card**:
    - Has a unique CardID
    - Has IssueDate and ExpiryDate
    - Is associated with exactly one Member

---

### Books and Authors

Each **Book**:

- Has a unique BookID
- Title
- ISBN
- PublicationYear
- Edition
- Publisher

A **Book** can be written by one or more Authors.

An **Author** can write multiple Books.

Each **Author** has:
- AuthorID (unique)
- FirstName
- LastName
- Country

---

### Book Copies and Loans

A Book may have multiple **Book Copies**.

Each Book Copy:
- Has a unique CopyID
- Has a Barcode
- Has a Status (Available, Borrowed, Lost, Damaged)
- Belongs to exactly one Book

A **Loan**:

- Has a unique LoanID
- LoanDate
- DueDate
- ReturnDate (optional)
- FineAmount

Each Loan:

- Is associated with exactly one Book Copy
- Is associated with exactly one Member

A Book Copy may appear in many Loans over time.

A Member may have zero or many Loans.

---

### Categories and Classification

Each Book belongs to one or more **Categories**.

Each Category:
- Has a unique CategoryID
- CategoryName
- Description

---

### Staff and Supervision

The library employs **Staff Members**.

Each Staff Member:
- Has a unique StaffID
- FirstName
- LastName
- Position
- HireDate

A Staff Member may supervise other Staff Members.

Each Staff Member may have at most one supervisor.


---

## Required Tasks

### Task 1 – ER Diagram (Crow’s Foot Notation)

Design a complete ER diagram that includes:

- Entities and attributes
- Primary Keys
- Foreign Keys
- Relationship names
- Cardinality (1:1, 1:M, M:N)
- Participation (mandatory / optional)
- One unary relationship

---

### Task 2 – Relational Schema

Convert your ER model into relational schema.

Example format:

Book(BookID int PK, Title String, ISBN String, PublicationYear Date, Publisher String)
BookCopy(CopyID int PK, BookID int FK → Book(BookID), Barcode String, Status String)





Ensure that:

- All Primary Keys are defined
- All Foreign Keys are clearly indicated
- M:N relationships are resolved using junction (associative) tables

---

### Task 3 – Database Implementation

Using  PostgreSQL:

1. Define all tables with:
    - Primary Keys
    - Foreign Keys
    - NOT NULL constraints
    - UNIQUE constraints
    - CHECK constraints (where appropriate)
2. Insert sample data into each table
    - At least 3 rows per table
    - Ensure relationships are testable

---

### Task 4 – Validation

Demonstrate:

- A Member borrowing multiple books
- A Book written by multiple Authors
- A Staff supervision hierarchy
- Proper enforcement of referential integrity


---

## Deliverables

Form a pdf report including the following parts:

1. Brief explanation  of design decisions
2. ER Diagram (PDF or image format)
3. Relational Schema (documented clearly)
4. SQL Script file (.sql)

---


## Notes

- Use Crow’s Foot notation for the ER model.
- Surrogate primary keys are recommended.
- Ensure normalization (minimize redundancy).
- Maintain semantic clarity and consistency in naming.




***

## Evaluation Criteria
***
The assignment will be evaluated based on two primary components:

1) Project Implementation: The quality and effectiveness of the project you implement.

2) Oral Exam Performance: Your performance during the oral exam, which will take place during your lab class in 
Week 7( the week starting March 2, 2026 ).

   

---

## Oral Exam Requirement

The oral exam is **mandatory** as part of the evaluation process. Students will be assessed 
based on their understanding of the material presented in their **reports** and **source code**.

### **During the Oral Exam:**
- Reports must be **open** and accessible.
- SQL code must be **ready to show** in the IDE.
- PostgreSQL must be **ready to run** for demonstration.

---

## Group Work
Students must complete the assignment **individually**.  

---

## Report Structure
While there is no standard template for the report, it must include the following essential components:

### 1. Cover Page
Student Information: Include your full name, course name, and date of submission.

Title of the Report: Clearly state the title of your study or project.

### 2. Study Explanation
The report must provide the explanation of your study, including:

- Objective: Clearly outline the purpose and goals of your study.
- Design and Implementation notes
- ER Diagram 
- Relational Schema 
- SQL Script file (.sql)
- Conclusion: Summarize the key points and findings of your study.

### 3. Additional Recommendations

Ensure that your report is well-organized and free of grammatical errors.

Use clear headings and subheadings to enhance readability.

---

## Email Submission

Students are required to attach their report as a PDF file and submit it via **email (cceken@ku.edu.kz)** before the 
oral examination.

* Email Subject: Use the following format for the subject line of your email:
    - dbms-assignment1-StudentFullName
* File Naming: Ensure that the report file is named appropriately, using the following format:
    - StudentFullName-report.pdf
  
---


## Late Submission and Oral Exam Policy
Students must submit their **reports and source code** before the **oral exam** (during their lab class in **Week 7**), 
as the oral exam time is crucial for evaluation.

If students are unable to attend the scheduled oral exam, they will be allowed to defend their project one week 
later during lab class hours.

However, this late defense of the oral exam will result in a 20% penalty on the total grade.

**Please note that there will not be another opportunity to defend the project beyond this timeframe.**

---

### By adhering to these guidelines and policies, you will ensure that your submission is complete and meets the evaluation criteria. 

***Good luck with your projects and oral exams!***
