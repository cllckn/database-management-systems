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





## Hands-on Exercise 2: Telephone Contact Records System
### Business Rules to ER Model with Crow’s Foot Notation

---

### Objective
In this exercise, you will practice transforming business rules into an Entity-Relationship (ER) model using **Crow’s Foot notation**.  
You are required to identify **entities**, **attributes**, **relationships**, **cardinalities**, and **participation** 
based on a **telephone contact records** system.

---

## Task 1: Analyze Business Rules

Below are the business rules for a system that manages **telephone contacts**, their phone numbers, and communication details.  
Your task is to analyze these rules and prepare them for ER modeling.

---

### Business Rules

A Contact has a unique Contact ID, First Name, Last Name, Status (Active, Inactive, Archived) and Notes.
Each Phone Number has a unique Phone Number ID, the Phone Number Value, and a Phone Type (e.g., Mobile, Home, Work).
A Contact can have multiple Phone Numbers, and each Phone Number belongs to one Contact.
Each Email Address has a unique Email ID and an Email Address Value.
A Contact may have multiple Email Addresses, and each Email Address belongs to one Contact.
Each Group has a unique Group ID and a Group Name.
A Contact can belong to multiple Groups (e.g., Family, Friends, Work), and each Group can include multiple Contacts.
The system records Call Logs for contacts.
Each Call Log has a unique Call Log ID, Call Date, Call Time, Call Duration, and Call Type (Incoming, Outgoing, Missed).
Each Call Log is associated with one Contact and one Phone Number.
A Contact may have zero or more Call Logs. A Call Log cannot exist without a Contact.
A Contact may be marked as a Favorite.
Each Address has a unique Address ID, Street, City, State, and Postal Code.
The system stores Address Information for contacts.
A Contact can have at most one Address, and each Address belongs to one Contact.

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
- Use consistent naming conventions throughout the model.
- Ensure that **primary keys (PKs)** and **foreign keys (FKs)** are clearly identified in your ER diagram.
- Clearly distinguish between **entity attributes** and **relationship attributes**.


---

## Additional Challenges

Copy the existing ER Diagram and modify it considering the following business rules:

* A Contact may have exactly one other Contact designated as their `Emergency Contact`. An Emergency Contact can serve 
in this role for many different Contacts, or for none at all.
---
* An Address must be associated with exactly one City. A City is identified by a CityID and has a CityName and TimeZone.
* Each City must belong to exactly one Country. A Country is identified by a CountryCode (e.g., KZ, TR, RU) and has a 
CountryName and Currency.
---
* A Contact can be associated with many Addresses over time. 
* An Address can be shared by multiple Contacts (e.g., roommates or family members). 
* The system must record the Start Date, End Date, and Address Type (e.g., Current, Previous, Vacation) for every connection between a Contact and an Address.


