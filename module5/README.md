# Module 5: NoSQL Databases
---
<!-- TOC -->
  * [# Module 5: NoSQL Databases](#-module-5-nosql-databases)
  * [NoSQL Databases](#nosql-databases)
    * [Comparison with Relational DB:](#comparison-with-relational-db)
    * [Main Types of NoSQL Databases](#main-types-of-nosql-databases)
    * [MongoDB NoSQL Database](#mongodb-nosql-database)
  * [Using a Programming Language to Interact With a Database](#using-a-programming-language-to-interact-with-a-database)
    * [Database Drivers – Core Functions](#database-drivers--core-functions)
    * [Database Operations with Java and MongoDB](#database-operations-with-java-and-mongodb)
      * [Example Workflow (Conceptual)](#example-workflow-conceptual)
  * [Hands-on Exercise](#hands-on-exercise)
<!-- TOC -->

---

## NoSQL Databases
- **NoSQL** (AKA Not Only SQL) databases are  designed for scalability, flexibility, and high-performance.
- They are capable of handling large-scale, unstructured, semi-structured, and rapidly changing data.

### Comparison with Relational DB:

![](../resources/figures/sql-vs-nosql.png)



### Main Types of NoSQL Databases

1. **Document-Oriented Databases**
- Store data as documents (usually in JSON or BSON format).
- Each document can have a flexible structure.
- **Examples:** MongoDB, CouchDB, Firebase Firestore
- **Sample:**
  ```json
  {
    "id": 101,
    "name": "Jane Lee",
    "email": "jl@email.com",
    "orders": [
      { "orderId": 1, "total": 250 },
      { "orderId": 2, "total": 180 }
    ]
  }
  ```
- **Used in:**
    - Content management systems (CMS)
    - E-commerce product catalogs
    - Mobile and web apps with dynamic data structures

2. **Key–Value Stores**
- Store data as key–value pairs (like a dictionary or map).
- Very fast for lookups and caching.
- **Examples:** Redis, Amazon DynamoDB, Riak
- **Sample:**
  ```
  "user:101" -> { "name": "Alice", "balance": 300 }
  "user:102" -> { "name": "Bob", "balance": 450 }
  ```
- **Used in:**
    - Caching user sessions
    - Real-time analytics and leaderboards
    - High-speed transaction systems

3. **Column-Family Stores**
- Organize data into columns and column families instead of rows.
- Designed for high performance and scalability across distributed systems.
- **Examples:** Apache Cassandra, HBase, ScyllaDB
    - **Sample:**
      ```
      UserProfiles (Column Family)
      └── Row Key: user_101
          ├── name: "Jane"
          ├── email: "jane@email.com"
          └── country: "Kazakhstan"
    
      └── Row Key: user_102
          ├── name: "Jack"
          └── email: "jck@email.com"

      Customer (Column Family)
      └── Row Key: user_101
          ├── name: Alice
          ├── email: alice@email.com
          └── orders: [1, 2, 3]
      ```
- **Used in:**
    - Time-series data storage
    - IoT and sensor data collection
    - Large-scale analytics applications

4. **Graph Databases**
- Represent data as nodes (entities) and edges (relationships).
- Ideal for analyzing complex, interconnected data.
- **Examples:** Neo4j, Amazon Neptune, ArangoDB
- **Sample (relationships):**
  ```
  (Alice) -[:FRIEND]-> (Bob)
  (Alice) -[:PURCHASED]-> (Product_A)
  (Bob) -[:PURCHASED]-> (Product_B)
  ```
- **Used in:**
    - Social networking platforms
    - Recommendation engines
    - Fraud detection and knowledge graphs


### MongoDB NoSQL Database

MongoDB is a popular NoSQL database that stores data in a flexible, document-oriented format instead of the traditional
table-and-row structure used by relational databases. Data is organized into collections, and each collection contains
documents represented in BSON (Binary JSON). Because documents can have varying structures, MongoDB is schema-less,
allowing applications to evolve without modifying fixed table definitions.

One of MongoDB’s key strengths is its ability to handle large volumes of unstructured or semi-structured data while
maintaining high performance. It offers rich querying capabilities, indexing, and aggregation tools similar to SQL
features but designed for flexible document data. MongoDB also provides automatic generation of a unique _id field
for each document, acting like a primary key.

MongoDB is designed for scalability and reliability. It supports replication for high availability and sharding for
horizontal scaling across multiple servers, making it suitable for modern distributed systems. Thanks to its
flexibility, scalability, and ease of use, MongoDB is widely used in web applications, real-time analytics, IoT
systems, and any environment where data structure can change over time.

In MongoDB, a cluster refers to a group of interconnected servers (nodes) that work together as a single unified
database system, providing scalability, redundancy, and high availability.

1) A replica set (cluster) provides high availability by copying the same data across multiple servers. In a replica set, read
   performance can also be improved by directing read operations to the nearest replica node.
```text
               +----------------------+
               |     Client App       |
               +-----------+----------+
                           |
                           v
                 +--------------------+
                 |     PRIMARY        |
                 |  (Read & Write)    |
                 +----+-----------+---+
                      |           |
        --------------+           +--------------
        |                                      |
        v                                      v
+--------------------+               +--------------------+
|   SECONDARY 1      |               |   SECONDARY 2      |
|  (Read / Failover) |               |  (Read / Failover) |
+--------------------+               +--------------------+

SECONDARIES replicate data **from the PRIMARY**
If PRIMARY fails → one SECONDARY becomes PRIMARY
```

2) Sharding (with sharding cluster) splits big data across multiple servers (shards). The mongos router directs client
   requests to the correct shard.

```text

                               Config Servers
                              ┌──────┬──────┬──────┐
                              │  C1  │  C2  │  C3  │
                              └──────┴──────┴──────┘
                                    ↑
                              ┌─────────────┐
        Clients → → → → → → → │   MONGOS    │ ← Query Router
                              └─────────────┘
                                    ↓
        ┌─────────────┬────────────────┬───────────────┬
        │             │                │               │
┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
│   SHARD A   │ │   SHARD B   │ │   SHARD C   │ │   SHARD D   │
│  (Replica   │ │  (Replica   │ │  (Replica   │ │  (Replica   │
│    Set)     │ │    Set)     │ │    Set)     │ │    Set)     │
│ ┌─────────┐ │ │ ┌─────────┐ │ │ ┌─────────┐ │ │ ┌─────────┐ │
│ │ Primary │ │ │ │ Primary │ │ │ │ Primary │ │ │ │ Primary │ │
│ ├─────────┤ │ │ ├─────────┤ │ │ ├─────────┤ │ │ ├─────────┤ │
│ │Secondary│ │ │ │Secondary│ │ │ │Secondary│ │ │ │Secondary│ │
│ ├─────────┤ │ │ ├─────────┤ │ │ ├─────────┤ │ │ ├─────────┤ │
│ │Secondary│ │ │ │Secondary│ │ │ │Secondary│ │ │ │Secondary│ │
│ └─────────┘ │ │ └─────────┘ │ │ └─────────┘ │ │ └─────────┘ │
└─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘

```


**Example**

Assume that we have the following docs in table view.

```text

+-----------------------------------------------------------+
|                        PRODUCTS                           |
+----+----------------------+---------+----------------------+
| ID |        name          | price   |      category        |
+----+----------------------+---------+----------------------+
|  1 | Laptop               | 1500.00 | Electronics          |
|  2 | Smartphone           | 999.99  | Electronics          |
|  3 | Headphones           | 199.99  | Electronics          |
|  4 | Monitor              | 300.00  | Electronics          |
|  5 | Keyboard             | 49.99   | Accessories          |
|  6 | Mouse                | 29.99   | Accessories          |
|  7 | Backpack             | 75.00   | Travel               |
|  8 | Water Bottle         | 20.00   | Lifestyle            |
|  9 | Camera               | 650.00  | Electronics          |
| 10 | Tripod               | 120.00  | Accessories          |
+-----------------------------------------------------------+
```

In replication, all nodes have EXACT same documents.
```text
                   REPLICA SET
         (High Availability, NOT data distribution)

            +-----------------------+
            |       PRIMARY         |
            +-----------------------+
            | Docs 1 to 10 (ALL)    |
            +-----------------------+

           /                         \
          /                           \
         v                             v

+-----------------------+   +-----------------------+
|     SECONDARY 1       |   |     SECONDARY 2       |
+-----------------------+   +-----------------------+
| Docs 1 to 10 (ALL)    |   | Docs 1 to 10 (ALL)    |
+-----------------------+   +-----------------------+

**Replica Set Rule:** Every node stores **all documents**.

```
In sharding, the data is partitioned and distributed across multiple shards, so each shard stores only a portion of the
overall dataset, not the full copy.
Each shard is itself normally a Replica Set, so each shard’s data is also replicated internally.

```text
            SHARDED CLUSTER (Data Partitioning)

                      +------------+
                      |  mongos    |
                      |  router    |
                      +------------+
                       /          \
                      /            \
                     v              v

           +--------------------+      +--------------------+
           |      SHARD 1       |      |      SHARD 2       |
           | (IDs 1–5 only)     |      | (IDs 6–10 only)    |
           +---------+----------+      +----------+---------+
                     |                            |
                     v                            v

+-----------------------------------------------------+
|                 SHARD 1 DATA (Replica Set)          |
+----+----------------------+---------+----------------+
| ID | name                 | price   | category       |
+----+----------------------+---------+----------------+
|  1 | Laptop               | 1500.00 | Electronics    |
|  2 | Smartphone           | 999.99  | Electronics    |
|  3 | Headphones           | 199.99  | Electronics    |
|  4 | Monitor              | 300.00  | Electronics    |
|  5 | Keyboard             | 49.99   | Accessories    |
+-----------------------------------------------------+

+-----------------------------------------------------+
|                 SHARD 2 DATA (Replica Set)          |
+----+----------------------+---------+----------------+
| ID | name                 | price   | category       |
+----+----------------------+---------+----------------+
|  6 | Mouse                | 29.99   | Accessories    |
|  7 | Backpack             | 75.00   | Travel         |
|  8 | Water Bottle         | 20.00   | Lifestyle      |
|  9 | Camera               | 650.00  | Electronics    |
| 10 | Tripod               | 120.00  | Accessories    |
+-----------------------------------------------------+

Assume sharding by ID range:
Shard 1 → IDs 1–5
Shard 2 → IDs 6–10
```




## Using a Programming Language to Interact With a Database

Modern applications often need to store, retrieve, and manipulate data dynamically.
To perform these database operations from within an application, database drivers are essential.
These drivers act as a bridge between the programming language and the database management system (DBMS).

### Database Drivers – Core Functions
Database drivers typically provide the following core capabilities:
- **Establishing a connection** to the database.
- **Executing queries**.
- **Retrieving results** and processing query outputs.
- **Managing transactions** to ensure data consistency.
- **Closing the connection** after operations are completed.



### Database Operations with Java and MongoDB

You can use MongoDB Cloud (https://account.mongodb.com/account/login
) without installation, or install MongoDB on your computer from the following
link: https://www.mongodb.com/try/download/community

#### Example Workflow (Conceptual)
1. **Load the driver** using maven package manager.

`pom.xml`
```xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cc.ku</groupId>
    <artifactId>java-projects</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- postgresql -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.8</version>
        </dependency>
        <!-- mongodb -->
        <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver-bom</artifactId>
            <version>5.6.0</version>
            <type>pom</type>
        </dependency>
        <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver-sync</artifactId>
            <version>5.6.0</version>
        </dependency>
      
    </dependencies>
</project>


```
2. **Establish a connection** to the MongoDB database using
   a connection string (URL(socket address), username, and password).
3. **Perform db operations**.
4. **Process the results** returned by the query.
5. **Close** the connection.



**Code Example**

* Define `ecommercedb.products` collection.

![DB Class Diagram](../resources/db-class-diagram.png)


```java


```


***
## Hands-on Exercise

1. Define the `ecommercedb` database and `products` table in `PostgreSQL`, using the SQL statements provided above the
   Class Diagram.
2. Define `ecommercedb.products` collection in `MongoDB`.
3. Initialize a new project with Maven support.
4. Include the necessary database drivers for PostgreSQL and MongoDB operations.
5. Define a package named `cc.ku.ict.module5.exercises.exercise1`.
6. Run the program given above that performs operations on the  PostgreSQL database (Check the db connection parameters).
7. Discuss how you can also add MariaDB support to the application without modifying the algorithm in the `ClientService`.
8. Define `findByName(String name)` and `findByPrice(double price)` methods in the product repository and modify
   the `ClientService` accordingly to test these methods.

