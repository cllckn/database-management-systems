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


