# Hands-on Exercise 1
## Employee and Project Management System

---

## Objective

In this exercise, you will practice transforming **business rules** into an **Entity–Relationship (ER) model** using **Crow’s Foot notation**.  
You are required to identify **entities**, **attributes**, **relationships**, **cardinalities**, and **constraints** based on a given business scenario.

---

## Task 1: Analyze Business Rules

Below are the business rules for a company’s **Employee and Project Management System**.  
Your task is to extract the necessary entities, define their attributes, determine relationships, and establish constraints.

**Business Rules**

A Company has a name, phone number, and address.

Each Department has a unique Department ID, a Name, and a Budget.

A Company can have multiple Departments, and each Department belongs to one Company.

An Employee has a unique Employee ID, a Name, Age, Gender, and Job Title.

Each Department is managed by one Employee, and an Employee can manage only one Department.

Each Employee works in one Department, and a Department can employ many Employees.

Each Project has a unique Project ID, a Name, a Start Date, and an End Date.

An Employee can be assigned to multiple Projects, and a Project can have multiple Employees working on it.

Each Employee–Project assignment specifies a Role describing the employee’s responsibility in that project.

Each Employee must be assigned to at least one Project.

Supervisors are also Employees.

A Supervisor can supervise multiple Employees, but each Employee can have at most one Supervisor.

The company records Education information for employees, including Degree Name, Institution, and Graduation Year.

An Employee can have multiple Education records, and each Education record belongs to one Employee.


---

## Task 2: Construct the ER Model

Using the business rules provided, perform the following steps:

1. **Identify and list all entities** along with their attributes.
2. **Determine the relationships** between the entities.
3. **Identify cardinalities** for each relationship  
   (one-to-one, one-to-many, or many-to-many).
4. **Design an ER diagram using Crow’s Foot notation** that accurately represents the business rules, including:
    - Primary keys
    - Foreign keys
    - Relationships
    - Associative (junction) entities where required

---

## Expected Outcome

By completing this exercise, you should be able to:
- Translate textual business rules into a structured ER model
- Correctly apply **Crow’s Foot symbols** to represent cardinality and participation
- Identify **associative entities** and **self-referencing relationships**
- Prepare a clear conceptual blueprint for database design









***









## Hands-on Exercise 2: Learning Management System (LMS) – Student Affairs
### Business Rules to ER Model with Crow’s Foot Notation

---

### Objective
In this exercise, you will practice transforming business rules into an Entity-Relationship (ER) model using **Crow’s Foot notation**.  
You are required to identify **entities**, **attributes**, **relationships**, **cardinalities**, and **constraints** based on a Learning Management System (LMS) scenario focused on student affairs.

---

## Task 1: Analyze Business Rules

Below are the business rules for a **Learning Management System** used by a university to manage students, courses, instructors, and enrollments.  
Your task is to analyze these rules and prepare them for ER modeling.

---

### Business Rules (Sentence Form)

1. A **University** has a **Name** and a **Campus Location**.

2. A **University** offers **multiple Programs**, and each **Program** belongs to **one University**.

3. Each **Program** has a **unique Program ID** and a **Program Name**.

4. A **Student** has a **unique Student ID**, **First Name**, **Last Name**, **Date of Birth**, **Email**, and **Enrollment Year**.

5. Each **Student** is enrolled in **one Program**, and a **Program** can have **many Students**.

6. An **Instructor** has a **unique Instructor ID**, **Name**, **Email**, and **Academic Rank**.

7. A **Course** has a **unique Course ID**, **Course Title**, **Credit Value**, and **Semester**.

8. An **Instructor** can teach **multiple Courses**, and each **Course** is taught by **one Instructor**.

9. A **Student** can enroll in **multiple Courses**, and each **Course** can have **multiple Students**.

10. Each **Enrollment** records the **Enrollment Date** and the **Final Grade** obtained by the student.

11. A **Student** must be enrolled in **at least one Course**.

12. A **Course** may be offered **multiple times** across different semesters.

13. A **Student** may have an **Academic Advisor**, who is an **Instructor**.

14. An **Instructor** can advise **multiple Students**, but each **Student** can have **at most one Advisor**.

15. The system maintains **Attendance records** for students in courses.

16. Each **Attendance record** includes the **Date** and **Attendance Status** (Present, Absent, Excused).

---

## Task 2: Construct the ER Model

Using the business rules provided above, perform the following steps:

1. Identify and list all **entities** and their **attributes**.
2. Determine the **relationships** between entities.
3. Identify the **cardinality** of each relationship (1:1, 1:M, M:N).
4. Identify any **mandatory participation constraints** (minimum cardinality).
5. Use **Crow’s Foot notation** to design an ER diagram that accurately represents the business rules.

---

### Notes
- Pay special attention to **many-to-many relationships** and determine whether **junction (associative) entities** are required.
- Clearly distinguish between **entities**, **attributes**, and **relationship attributes**.
- Ensure that **primary keys** and **foreign keys** are clearly identified in your ER diagram.






***






## Hands-on Exercise 3: Simple E-Commerce System
### Business Rules to ER Model with Crow’s Foot Notation

---

### Objective
In this exercise, you will practice transforming business rules into an Entity-Relationship (ER) model using **Crow’s Foot notation**.  
You are required to identify **entities**, **attributes**, **relationships**, **cardinalities**, and **constraints** based on a simple **E-Commerce system** scenario.

---

## Task 1: Analyze Business Rules

Below are the business rules for a **simple E-Commerce system** that manages customers, products, orders, and payments.  
Your task is to analyze these rules and prepare them for ER modeling.

---

### Business Rules (Sentence Form)

1. A **Customer** has a **unique Customer ID**, **First Name**, **Last Name**, **Email**, **Phone Number**, and **Shipping Address**.

2. Each **Customer** can place **multiple Orders**, but each **Order** is placed by **one Customer**.

3. An **Order** has a **unique Order ID**, **Order Date**, and **Order Status**.

4. An **Order** must include **at least one Product**.

5. A **Product** has a **unique Product ID**, **Product Name**, **Description**, **Unit Price**, and **Stock Quantity**.

6. A **Product** can appear in **many Orders**, and an **Order** can contain **many Products**.

7. For each product included in an order, the system records the **Quantity Ordered** and **Unit Price at the time of order**.

8. Each **Order** has exactly **one Payment**.

9. A **Payment** has a **unique Payment ID**, **Payment Date**, **Payment Method** (e.g., Credit Card, PayPal), and **Payment Amount**.

10. A **Customer** can make **multiple Payments** over time.

11. The system keeps track of **Product Categories**.

12. Each **Category** has a **unique Category ID** and a **Category Name**.

13. A **Category** can include **multiple Products**, but each **Product** belongs to **one Category**.

14. The system stores **Order Shipping Information**, including **Shipping Address** and **Shipping Date**.

15. An **Order** may be marked as **Canceled**, **Shipped**, or **Delivered**.

---

## Task 2: Construct the ER Model

Using the business rules provided above, perform the following steps:

1. Identify and list all **entities** and their **attributes**.
2. Determine the **relationships** between the entities.
3. Identify the **cardinality** of each relationship (1:1, 1:M, M:N).
4. Identify any **mandatory participation constraints** (minimum cardinality).
5. Resolve **many-to-many relationships** using **junction (associative) entities** where necessary.
6. Use **Crow’s Foot notation** to design an ER diagram that accurately represents the business rules.

---

### Notes
- Pay special attention to the **Order–Product** relationship and identify the correct **associative entity**.
- Clearly distinguish between **entity attributes** and **relationship attributes**.
- Ensure that **primary keys (PKs)** and **foreign keys (FKs)** are clearly identified in your ER diagram.
- Use consistent naming conventions for entities and attributes.


## Hands-on Exercise 4: Telephone Contact Records System
### Business Rules to ER Model with Crow’s Foot Notation

---

### Objective
In this exercise, you will practice transforming business rules into an Entity-Relationship (ER) model using **Crow’s Foot notation**.  
You are required to identify **entities**, **attributes**, **relationships**, **cardinalities**, and **constraints** based on a **telephone contact records** system.

---

## Task 1: Analyze Business Rules

Below are the business rules for a system that manages **telephone contacts**, their phone numbers, and communication details.  
Your task is to analyze these rules and prepare them for ER modeling.

---

### Business Rules (Sentence Form)

1. A **Contact** has a **unique Contact ID**, **First Name**, **Last Name**, and **Notes**.

2. A **Contact** can have **multiple Phone Numbers**, and each **Phone Number** belongs to **one Contact**.

3. Each **Phone Number** has a **unique Phone Number ID**, the **Phone Number Value**, and a **Phone Type** (e.g., Mobile, Home, Work).

4. A **Contact** may have **multiple Email Addresses**, and each **Email Address** belongs to **one Contact**.

5. Each **Email Address** has a **unique Email ID** and an **Email Address Value**.

6. A **Contact** can belong to **multiple Groups** (e.g., Family, Friends, Work), and each **Group** can include **multiple Contacts**.

7. Each **Group** has a **unique Group ID** and a **Group Name**.

8. The system records **Call Logs** for contacts.

9. Each **Call Log** has a **unique Call Log ID**, **Call Date**, **Call Time**, **Call Duration**, and **Call Type** (Incoming, Outgoing, Missed).

10. Each **Call Log** is associated with **one Contact** and **one Phone Number**.

11. A **Contact** may have **zero or more Call Logs**.

12. A **Contact** may be marked as a **Favorite**.

13. The system stores **Address Information** for contacts.

14. Each **Address** has a **unique Address ID**, **Street**, **City**, **State**, and **Postal Code**.

15. A **Contact** can have **at most one Address**, and each **Address** belongs to **one Contact**.

---

## Task 2: Construct the ER Model

Using the business rules provided above, perform the following steps:

1. Identify and list all **entities** and their **attributes**.
2. Determine the **relationships** between entities.
3. Identify the **cardinality** of each relationship (1:1, 1:M, M:N).
4. Identify any **optional or mandatory participation constraints**.
5. Resolve **many-to-many relationships** using **junction (associative) entities** where necessary.
6. Use **Crow’s Foot notation** to design an ER diagram that accurately represents the business rules.

---

### Notes
- Pay special attention to **Contact–Group** and **Contact–Call Log** relationships.
- Clearly distinguish between **entity attributes** and **relationship attributes**.
- Ensure that **primary keys (PKs)** and **foreign keys (FKs)** are clearly identified in your ER diagram.
- Use consistent naming conventions throughout the model.





***






## Hands-on Exercise 5: Messaging System (WhatsApp-like)
### Business Rules to ER Model with Crow’s Foot Notation

---

### Objective
In this exercise, you will practice transforming business rules into an Entity-Relationship (ER) model using **Crow’s Foot notation**.  
You are required to identify **entities**, **attributes**, **relationships**, **cardinalities**, and **constraints** based on a **messaging system** similar to WhatsApp.

---

## Task 1: Analyze Business Rules

Below are the business rules for a **messaging system** that supports users, one-to-one chats, group chats, and message exchange.  
Your task is to analyze these rules and prepare them for ER modeling.

---

### Business Rules (Sentence Form)

1. A **User** has a **unique User ID**, **Phone Number**, **Display Name**, **Profile Status**, and **Registration Date**.

2. A **User** can participate in **multiple Chats**, and each **Chat** can include **multiple Users**.

3. A **Chat** has a **unique Chat ID**, **Chat Type** (Private or Group), and **Creation Date**.

4. A **Private Chat** involves **exactly two Users**.

5. A **Group Chat** can include **many Users**.

6. Each **Group Chat** has a **Group Name** and a **Group Creator**, who is a **User**.

7. A **User** can create **multiple Group Chats**.

8. A **Message** has a **unique Message ID**, **Content**, **Timestamp**, and **Message Type** (Text, Image, Audio, Video).

9. Each **Message** is sent by **one User** and belongs to **one Chat**.

10. A **Chat** can contain **many Messages**, but each **Message** belongs to **exactly one Chat**.

11. A **User** can send **many Messages**, but each **Message** is sent by **one User**.

12. A **Message** may have a **Delivery Status** (Sent, Delivered, Read).

13. A **User** can delete a **Message** they have sent.

14. The system stores **Contact relationships** between users.

15. A **User** can have **many Contacts**, and each **Contact relationship** links **two Users**.

16. The system maintains **Message Read Receipts**, recording when a message is read by a user.

17. Each **Read Receipt** includes a **Read Timestamp**.

---

## Task 2: Construct the ER Model

Using the business rules provided above, perform the following steps:

1. Identify and list all **entities** and their **attributes**.
2. Determine the **relationships** between the entities.
3. Identify the **cardinality** of each relationship (1:1, 1:M, M:N).
4. Identify **mandatory and optional participation constraints**.
5. Resolve **many-to-many relationships** using **junction (associative) entities** where necessary.
6. Use **Crow’s Foot notation** to design an ER diagram that accurately represents the business rules.

---

### Notes
- Clearly distinguish between **Private Chats** and **Group Chats** (specialization if needed).
- Pay special attention to **User–Chat**, **Chat–Message**, and **User–Message** relationships.
- Use **associative entities** where attributes belong to relationships (e.g., Contacts, Read Receipts).
- Ensure **primary keys (PKs)** and **foreign keys (FKs)** are clearly identified.
- Follow consistent naming conventions throughout the ER model.
