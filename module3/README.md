# Module 3: Relational Database Model

<!-- TOC -->
* [Module 3: Relational Database Model](#module-3-relational-database-model)
  * [Relational Databases and Basic SQL](#relational-databases-and-basic-sql)
    * [**Setting Up the Working Environment for RDBMS**](#setting-up-the-working-environment-for-rdbms)
      * [**Installing PostgreSQL**](#installing-postgresql)
      * [**Installing pgAdmin**](#installing-pgadmin)
      * [**Importing and Exporting Sample Databases**](#importing-and-exporting-sample-databases)
    * [Basic SQL](#basic-sql)
      * [CREATE DATABASE](#create-database)
      * [CREATE TABLE](#create-table)
      * [SELECT](#select)
      * [INSERT INTO](#insert-into)
      * [**UPDATE**](#update)
      * [**DELETE**](#delete)
  * [Using a Programming Language to Interact With a Database](#using-a-programming-language-to-interact-with-a-database)
    * [Database Drivers – Core Functions](#database-drivers--core-functions)
    * [Database Operations with Java and PostgreSQL](#database-operations-with-java-and-postgresql)
      * [Example Workflow (Conceptual)](#example-workflow-conceptual)
<!-- TOC -->
## Relational Databases and Basic SQL

- **Relational Databases** organize data into **tables**, where:
    - Rows represent **records (tuples)**.
    - Columns represent **attributes**.
- Relationships are established using:
    - **Primary Keys** → uniquely identify records.
    - **Foreign Keys** → define relationships between tables.
- **SQL (Structured Query Language)** is the standard language used to define, query, and manipulate relational data.

> In later modules, these concepts will be contrasted with **NoSQL databases**, which use alternative data models and query mechanisms to address scalability, performance, and flexibility requirements.



### **Setting Up the Working Environment for RDBMS**

To work with SQL databases efficiently, a proper working environment is required.

#### **Installing PostgreSQL**
PostgreSQL is an open-source and powerful relational database management system.
- **Download Link**: [Download PostgreSQL](https://www.postgresql.org/download)
- Supports Windows, macOS, and Linux.
- Complies with SQL standards and offers extensive plugin support.

#### **Installing pgAdmin**
pgAdmin is the most commonly used graphical management tool for PostgreSQL.
- It is distributed along with PostgreSQL.
- Provides a user-friendly interface for managing databases, executing queries, and visualizing schemas.

#### **Importing and Exporting Sample Databases**

* **Northwind Sample Database**
    - A well-known example database that represents the data structure of a trading company.
    - Can be imported using **pgAdmin**.
    - [Download](../resources/dbs/northwind.backup)


### Basic SQL


| Command             | Description                             |
|---------------------|-----------------------------------------|
| **CREATE DATABASE** | Constructs a new database.              |
| **CREATE TABLE**    | Defines a new table.                    |
| **SELECT**          | Retrieves data from one or more tables. |
| **INSERT**          | Adds new data into a table.             |
| **UPDATE**          | Modifies existing records in a table.   |
| **DELETE**          | Removes specific records from a table.  |


#### CREATE DATABASE

Constructs a new database.

```sql
CREATE DATABASE ecommercedb
```

#### CREATE TABLE

Defines a new table.

```sql
CREATE TABLE products (  
    id SERIAL,
    code CHAR(6) NOT NULL,
    name VARCHAR(40) NOT NULL,
    date DATE DEFAULT '2019-01-01',
    price MONEY,
    quantity SMALLINT DEFAULT 0,
    CONSTRAINT "productsPK" PRIMARY KEY(id),
    CONSTRAINT "productsUnique" UNIQUE(code),
    CONSTRAINT "productsCheck" CHECK(quantity >= 0)
);

```

---
**The following queries are based on the Northwind sample database.**

---


#### SELECT

Retrieves data from one or more tables.

```sql
SELECT * FROM "customers" WHERE "Country"='Spain' OR "Country"='Türkiye'
ORDER BY "CustomerID";
```


**INNER JOIN**
Returns only the records that have matching values in both tables.
- Only products with a valid `CategoryID` in the **Categories** table are included.
- Any product with a `NULL` `CategoryID` is excluded.

```sql
SELECT "ProductID", "ProductName", "CategoryName"
FROM "products"
INNER JOIN "categories" ON "products"."CategoryID" = "categories"."CategoryID";
```

#### INSERT INTO

Add new records to a table.
- **Data integrity constraints** are enforced during the insertion process.
- It is possible to insert values into only specific columns.
- Columns that are not explicitly assigned a value will be set to **NULL** (empty).

```sql
INSERT INTO "customers" 
("CustomerID", "CompanyName", "ContactName", "Address", "City", "Country") 
VALUES ('X1', 'ABC', 'Jane', 'Address1', 'Astana', 'Kazakhstan');
```


#### **UPDATE**

The **UPDATE** statement is used to modify existing records in a table.
- **Data integrity constraints** are enforced during the update process.
- The `WHERE` clause is used to specify which records should be updated.
- If the `WHERE` clause is **not** included, **all rows** in the table are updated.


```sql
UPDATE "customers"
SET "ContactName" = 'Jane Lee',
    "City" = 'Astana'
WHERE "CustomerID" = '1';

```

#### **DELETE**

The **DELETE** statement is used to remove one or more records from a table.
- **Data integrity constraints** are enforced during the deletion process.
- The `WHERE` clause specifies which records should be deleted.
- If the `WHERE` clause is **not** included, **all records** in the table are deleted.


```sql
DELETE FROM "customers"
WHERE "CustomerID" = '1';
```



## Using a Programming Language to Interact With a Database

Modern applications often need to store, retrieve, and manipulate data dynamically.
To perform these database operations from within an application, database drivers are essential.
These drivers act as a bridge between the programming language and the database management system (DBMS).

### Database Drivers – Core Functions
Database drivers typically provide the following core capabilities:
- **Establishing a connection** to the database.
- **Executing queries** (e.g., `SELECT`, `INSERT`, `UPDATE`, `DELETE`).
- **Retrieving results** and processing query outputs.
- **Managing transactions** to ensure data consistency.
- **Closing the connection** after operations are completed.


### Database Operations with Java and PostgreSQL

Java applications can seamlessly interact with PostgreSQL databases using **JDBC** (Java Database Connectivity).  
JDBC is a **standard API** that defines a set of interfaces and classes for connecting to relational databases,
sending SQL statements, and processing results.

It provides:
- **Connection management**
- **Statement execution**
- **Result retrieval**
- **Error handling and transaction control**

The **PostgreSQL JDBC driver** implements this API and allows Java programs to work directly with PostgreSQL databases.

You can download the PostgreSQL JDBC driver from:  
[https://jdbc.postgresql.org/download/](https://jdbc.postgresql.org/download/)


#### Example Workflow (Conceptual)
1. **Load the driver**(library or JAR file) in the project environment so that the Java application can
   communicate with the database.
2. **Establish a connection** to the PostgreSQL database using a connection string (URL(socket address), username, and password).
3. **Define and execute SQL statements**.
4. **Process the results** returned by the query.
5. **Close** the statement and connection to free resources.


***The Repository Pattern abstracts the logic of data access and storage from the business logic of an application.
It provides a clean separation between the domain layer and the data access layer.
Repositories act as mediators between the business logic and the data source (e.g., database, API, or file).
This abstraction improves maintainability, testability, and supports dependency inversion.***


**Code Example**

```sql
CREATE DATABASE ecommercedb;
```

```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION CHECK (price >= 0)
);
```


![DB Class Diagram](../resources/db-class-diagram.png)

>[ProductRepositoryMain.java](./repository/ProductRepositoryMain.java) | [IProductRepository.java](./repository/IProductRepository.java) | [ClientService.java](./repository/ClientService.java) | [Product.java](./repository/Product.java) | [ProductPostgresqlImplementation.java](./repository/ProductPostgresqlImplementation.java) | [ProductMongodbImplementation.java](./repository/ProductMongodbImplementation.java)

