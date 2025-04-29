# Hands-On Exercise 1: Design and Implementation of a Blogging Platform

**Objective:** 

Model, implement, and enforce business rules in a PostgreSQL database using ER design, SQL schema, functions, and triggers.



## Assignment Description

You are required to desig and implement a database for a blogging platform. 
This system should allow authors to write posts, categorize them, and attach images. 
You will build the ER diagram, define the relational schema, and use triggers and functions to maintain data quality.


## Business Rules

1. Each author must have a name, unique email and username.
2. An author can write multiple posts, but a post must belong to one author.
3. Each post includes:
   - A title
   - Content
   - A publish timestamp
4. Each post must belong to one category.
5. A category name must be unique.
6. A post can have multiple images, but each image must belong to one post.



## Tasks

### 1. ER Diagram
Draw an Entity-Relationship (ER) diagram based on the business rules. Identify entities, attributes, and relationships 
using proper notation.

### 2. Relational Schema
Transform your ER diagram into relational schema 

### 3. Implementation using SQL DDL statements. 
Define tables, relationships, and other constraints.

### 3. Function / Stored Procedure
Write a stored procedure or function to insert a new post. The function should:
- Accept the title, content, author ID, and category ID as input
- Insert the post into the database with the current timestamp

### 4. Trigger
Define a trigger on the authors table to enforce consistent and valid usernames:
- Automatically trims spaces
- Convert to all lower case characters
- Validate that the username is not null or empty; if it is, raise an exception to prevent insertion or update.

