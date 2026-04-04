# Module 5: NoSQL Databases


<!-- TOC -->
* [Module 5: NoSQL Databases](#module-5-nosql-databases)
  * [1. NoSQL Databases](#1-nosql-databases)
    * [1.1 Comparison with Relational DB:](#11-comparison-with-relational-db)
    * [1.2 Main Types of NoSQL Databases](#12-main-types-of-nosql-databases)
    * [1.3 MongoDB NoSQL Database](#13-mongodb-nosql-database)
      * [Two Key Structures in a Cluster**](#two-key-structures-in-a-cluster)
  * [2. Using a Programming Language to Interact With a Database](#2-using-a-programming-language-to-interact-with-a-database)
    * [2.1 Database Drivers – Core Functions](#21-database-drivers--core-functions)
    * [2.2 Database Operations with Java and MongoDB](#22-database-operations-with-java-and-mongodb)
      * [Initializing A New Project Using IntelliJ IDEA for Java-Based Program Development](#initializing-a-new-project-using-intellij-idea-for-java-based-program-development)
      * [Loading Database Driver Into The Project Environment](#loading-database-driver-into-the-project-environment)
      * [Example Workflow (Conceptual)](#example-workflow-conceptual)
      * [A Java-based program for MongoDB CRUD operations](#a-java-based-program-for-mongodb-crud-operations)
  * [3. Implementing Relationships: Embedding vs Linking](#3-implementing-relationships-embedding-vs-linking)
    * [3.1 What is Embedding?](#31-what-is-embedding)
    * [3.2 What is Linking (Referencing)?](#32-what-is-linking-referencing)
    * [3.3 Case Study: Book & Author Storage Strategies](#33-case-study-book--author-storage-strategies)
<!-- TOC -->



## 1. NoSQL Databases
- **NoSQL** (AKA Not Only SQL) databases are  designed for scalability, flexibility, and high-performance.
- They are capable of handling large-scale, unstructured, semi-structured, and rapidly changing data.

---

### 1.1 Comparison with Relational DB:

![](../resources/figures/sql-vs-nosql.png)

---

### 1.2 Main Types of NoSQL Databases

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

---

### 1.3 MongoDB NoSQL Database

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

A cluster is a group of interconnected servers (nodes) that work
together as a single unified database system, providing:
- Scalability
- Redundancy
- High availability

---

#### Two Key Structures in a Cluster**

1) **Replica Set**

Provides  redundancy and high availability. 

The same data is copied across multiple nodes.


- One **Primary** node handles all writes
- One or more **Secondary** nodes replicate data from the primary
- If the primary fails, a secondary is **automatically promoted** to primary

In a replica set, read performance can also be improved by directing read operations to the nearest replica node.


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
---

2) **Sharding**
   
Provides **horizontal scalability**.

Large datasets are split and distributed across multiple (shards).


- Data is divided into **shards** — each shard holds a portion of the data
- A **shard key** determines how data is distributed
- A **mongos router** directs queries to the correct shard



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

---

**Key Difference**

| | Replica Set | Sharding |
|---|---|---|
| Purpose | Redundancy & availability | Scalability & performance |
| Data distribution | Same data on every node | Different data on each node |
| Handles | Node failures | Large data volumes & high traffic |


**In Practice**

A production MongoDB cluster typically uses **both together** —
sharding splits the data for scale, and each shard is itself
a replica set for fault tolerance.

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


---

## 2. Using a Programming Language to Interact With a Database

Modern applications often need to store, retrieve, and manipulate data dynamically.
To perform these database operations from within an application, database drivers are essential.
These drivers act as a bridge between the programming language and the database management system (DBMS).

---

### 2.1 Database Drivers – Core Functions
Database drivers typically provide the following core capabilities:
- **Establishing a connection** to the database.
- **Executing queries**.
- **Retrieving results** and processing query outputs.
- **Managing transactions** to ensure data consistency.
- **Closing the connection** after operations are completed.


---

### 2.2 Database Operations with Java and MongoDB

#### Initializing A New Project Using IntelliJ IDEA for Java-Based Program Development


To develop a Java program, an IDE and JDK are required.

**IntelliJ IDEA**
- Popular IDE for Java based development.
- Download Link: [IntelliJ](https://www.jetbrains.com/idea/download)
- Install and start IntelliJ IDEA
- File -> New -> Project -> Java
  - give a name, choose a location
  - Build system: Maven
  - JDK -> choose a proper JDK, download if not exist
  - check "add sample code" option

  
#### Loading Database Driver Into The Project Environment

**Load the drivers** using maven package manager.

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

--- 

#### Example Workflow (Conceptual)
1. **Load the driver** in the project environment so that the Java application can
   communicate with the database.
2. **Establish a connection** to the MongoDB database using a connection string (URL(socket address), username, and password).
3. **Define and execute MongoDB statements**.
4. **Process the results** returned by the query.
5. **Close** the statement and connection to free resources.


---


#### A Java-based program for MongoDB CRUD operations

**Prerequisite Setup**

To construct MongoDB database, you can use MongoDB Cloud (https://account.mongodb.com/account/login
) without installation, or install MongoDB on your computer from the following
link: https://www.mongodb.com/try/download/community

1. Define `nw.customers` collection in MongoDB. 
2. Insert the following sample records.


```json
[
  {
    "CustomerID": "ALFKI",
    "CompanyName": "Alfreds Futterkiste",
    "ContactName": "Maria Anders",
    "Country": "Germany"
  },
  {
    "CustomerID": "ANATR",
    "CompanyName": "Ana Trujillo Emparedados y Helados",
    "ContactName": "Ana Trujillo",
    "Country": "Mexico"
  },
  {
    "CustomerID": "ANTON",
    "CompanyName": "Antonio Moreno Taquería",
    "ContactName": "Antonio Moreno",
    "Country": "Mexico"
  },
  {
    "CustomerID": "AROUT",
    "CompanyName": "Around the Horn",
    "ContactName": "Thomas Hardy",
    "Country": "UK"
  },
  {
    "CustomerID": "BERGS",
    "CompanyName": "Berglunds snabbköp",
    "ContactName": "Christina Berglund",
    "Country": "Sweden"
  }
]
```

```java

package cc.ku.dbms.module5;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Full CRUD operations on the Northwind MongoDB database.
 * Collection: customers
 * Fields: CustomerID, CompanyName, ContactName, Country
 *
 * Dependencies (add to pom.xml or build.gradle):
 *   MongoDB Java Driver: org.mongodb:mongodb-driver-sync:4.11.0
 */
public class MongoDBCRUDOperations {

    // ── Connection settings ───────────────────────────────────────────────────
    private static final String URI         = "mongodb+srv://lectureuser:lecturepassword@cluster0.zxbhndn.mongodb.net/?appName=Cluster0";
    private static final String DB_NAME     = "nw";
    private static final String COLLECTION  = "customers";

    // ── Entry point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {

        // try-with-resources: MongoClient closes automatically
        try (MongoClient mongoClient = MongoClients.create(URI)) {

            MongoDatabase database   = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> customers = database.getCollection(COLLECTION);

            System.out.println("Connected to MongoDB — database: " + DB_NAME + "\n");

            // ── INSERT ────────────────────────────────────────────────────────
            System.out.println("=== INSERT ===");
            addNewCustomer(customers, "NEWCO", "New Company Ltd.", "Alice Smith", "Germany");

            // ── READ (all) ────────────────────────────────────────────────────
            System.out.println("\n=== READ (all) ===");
            readAllCustomers(customers);

            // ── READ (single) ─────────────────────────────────────────────────
            System.out.println("\n=== READ (single: NEWCO) ===");
            readCustomerById(customers, "NEWCO");

            // ── UPDATE ────────────────────────────────────────────────────────
            System.out.println("\n=== UPDATE ===");
            updateCustomer(customers, "NEWCO", "Updated Company Ltd.", "Bob Jones", "Spain");

            // ── READ after update ─────────────────────────────────────────────
            System.out.println("\n=== READ after UPDATE ===");
            readCustomerById(customers, "NEWCO");

            // ── DELETE ────────────────────────────────────────────────────────
            System.out.println("\n=== DELETE ===");
            //deleteCustomer(customers, "NEWCO");

            // ── Confirm deletion ──────────────────────────────────────────────
            System.out.println("\n=== READ after DELETE ===");
            readCustomerById(customers, "NEWCO");
        }
    }

    // ── INSERT ────────────────────────────────────────────────────────────────
    /**
     * Inserts a new customer document into the collection.
     */
    public static void addNewCustomer(MongoCollection<Document> collection,
                                      String customerID,
                                      String companyName,
                                      String contactName,
                                      String country) {

        Document newCustomer = new Document("CustomerID",   customerID)
                .append("CompanyName",  companyName)
                .append("ContactName",  contactName)
                .append("Country",      country);

        InsertOneResult result = collection.insertOne(newCustomer);
        System.out.println("Inserted document ID : " + result.getInsertedId());
        System.out.println("CustomerID           : " + customerID);
    }

    // ── READ (all) ────────────────────────────────────────────────────────────
    /**
     * Reads and prints all customer documents.
     */
    public static void readAllCustomers(MongoCollection<Document> collection) {

        FindIterable<Document> docs = collection.find();

        int count = 0;
        for (Document doc : docs) {
            printCustomer(doc);
            count++;
        }
        System.out.println("Total documents: " + count);
    }

    // ── READ (single) ─────────────────────────────────────────────────────────
    /**
     * Finds a single customer document by CustomerID.
     */
    public static void readCustomerById(MongoCollection<Document> collection,
                                        String customerID) {

        Bson filter = Filters.eq("CustomerID", customerID);
        Document doc = collection.find(filter).first();

        if (doc != null)
            printCustomer(doc);
        else
            System.out.println("No customer found with CustomerID: " + customerID);
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    /**
     * Updates CompanyName, ContactName, and Country for a given CustomerID.
     * Uses $set so only the specified fields are modified.
     */
    public static void updateCustomer(MongoCollection<Document> collection,
                                      String customerID,
                                      String newCompanyName,
                                      String newContactName,
                                      String newCountry) {

        Bson filter = Filters.eq("CustomerID", customerID);
        Bson update = Updates.combine(
                Updates.set("CompanyName", newCompanyName),
                Updates.set("ContactName", newContactName),
                Updates.set("Country",     newCountry)
        );

        UpdateResult result = collection.updateOne(filter, update);

        if (result.getMatchedCount() > 0)
            System.out.println("Updated CustomerID  : " + customerID
                    + " | Modified count: " + result.getModifiedCount());
        else
            System.out.println("No customer found with CustomerID: " + customerID);
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    /**
     * Deletes a customer document by CustomerID.
     */
    public static void deleteCustomer(MongoCollection<Document> collection,
                                      String customerID) {

        Bson filter       = Filters.eq("CustomerID", customerID);
        DeleteResult result = collection.deleteOne(filter);

        if (result.getDeletedCount() > 0)
            System.out.println("Deleted CustomerID  : " + customerID);
        else
            System.out.println("No customer found with CustomerID: " + customerID);
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    /**
     * Prints a customer document in a formatted table row.
     */
    private static void printCustomer(Document doc) {
        System.out.printf("ID: %-10s | Company: %-30s | Contact: %-20s | Country: %s%n",
                doc.getString("CustomerID"),
                doc.getString("CompanyName"),
                doc.getString("ContactName"),
                doc.getString("Country"));
    }
}

```

---

## 3. Implementing Relationships: Embedding vs Linking


### 3.1 What is Embedding?

Storing related data **inside** the same document.

The child data lives as a nested array or object within the parent.

```json
{
  "name": "Parent Document",
  "children": [
    { "field": "value" },
    { "field": "value" }
  ]
}
```


**Embedding Orders into Customer**
```json
{
  "CustomerID": "ALFKI",
  "CompanyName": "Alfreds Futterkiste",
  "Country": "Germany",
  "Orders": [
    { "OrderID": 10643, "OrderDate": "1997-08-25", "TotalAmount": 814.50 },
    { "OrderID": 10692, "OrderDate": "1997-10-03", "TotalAmount": 878.00 }
  ]
}
```


**When to use**
- Related data is always read together
- Child data belongs to one parent only
- Child count is small and bounded

**Pros**
- Single query fetches everything
- Faster reads, no joins needed
- Atomic updates on the whole document

**Cons**
- Document grows large over time
- Child data cannot be queried independently
- Updating one child rewrites the whole document


**Retrieve customer with embedded orders**
```java
MongoCollection<Document> customers = db.getCollection("customers");

Document customer = customers
        .find(Filters.eq("CustomerID", "ALFKI"))
        .first();

if (customer != null) {
    System.out.println("Company : " + customer.getString("CompanyName"));

    List<Document> orders = customer.getList("Orders", Document.class);
    for (Document order : orders) {
        System.out.println("  OrderID : " + order.getInteger("OrderID")
                + " | Date: "  + order.getString("OrderDate")
                + " | Total: " + order.getDouble("TotalAmount"));
    }
}
```


---

### 3.2 What is Linking (Referencing)?

Storing related data in **separate collections** and connecting
them via a shared reference field (like a foreign key in SQL).

```json
// parent collection
{ "_id": "P1", "name": "Parent Document" }

// child collection
{ "_id": "C1", "parentId": "P1", "field": "value" }
{ "_id": "C2", "parentId": "P1", "field": "value" }
```



**Linking Orders to Customer**

```json
// customers collection
{ "CustomerID": "ALFKI", "CompanyName": "Alfreds Futterkiste", "Country": "Germany" }

// orders collection
{ "OrderID": 10643, "CustomerID": "ALFKI", "OrderDate": "1997-08-25", "TotalAmount": 814.50 }
{ "OrderID": 10692, "CustomerID": "ALFKI", "OrderDate": "1997-10-03", "TotalAmount": 878.00 }
```



**When to use**
- Child data needs to be queried independently
- A parent can have many or unbounded children
- Child data is shared across multiple parents

**Pros**
- Collections stay lean and manageable
- Child data can be queried, filtered, updated independently
- Scales well for large datasets

**Cons**
- Requires two queries or `$lookup` to retrieve related data
- No atomic updates across both collections by default



**Retrieve customer then fetch linked orders separately**

```java
MongoCollection<Document> customers = db.getCollection("customers");
MongoCollection<Document> orders    = db.getCollection("orders");

// Step 1: fetch the customer
Document customer = customers
        .find(Filters.eq("CustomerID", "ALFKI"))
        .first();

if (customer != null) {
    System.out.println("Company : " + customer.getString("CompanyName"));

    // Step 2: fetch all orders belonging to that customer
    FindIterable<Document> customerOrders = orders
            .find(Filters.eq("CustomerID", "ALFKI"));

    for (Document order : customerOrders) {
        System.out.println("  OrderID : " + order.getInteger("OrderID")
                + " | Date: "  + order.getString("OrderDate")
                + " | Total: " + order.getDouble("TotalAmount"));
    }
}
```


---

**Recommendation for Northwind**
Use **linking** — customers accumulate orders over time (unbounded),
and orders are frequently queried independently by date, amount, or
product. 

---

### 3.3 Case Study: Book & Author Storage Strategies

---

**Option 1: Embedding Authors into Book**

Best when the number of authors is small and bounded, and authors
are always read together with the book.
```json
{
  "_id": 1,
  "title": "Database Systems",
  "isbn": "123456",
  "authors": [
    { "authorId": 1, "name": "John Doe" },
    { "authorId": 2, "name": "Jane Smith" }
  ]
}
```

**When to use**
- A book has a small, fixed number of authors (rarely more than 3–5)
- Authors are always displayed together with the book
- You do not need to search or update authors independently

**Pros**
- Single query fetches book + all authors
- Simple structure, no joins needed

**Cons**
- Searching authors across all books requires scanning nested arrays
- Updating an author (e.g. name change) requires updating every book they appear in

---

**Option 2: Linking Authors to Book**

Best when authors need to be searched, updated, or queried
independently across all books.
```json
// books collection
{ "_id": 1, "title": "Database Systems", "isbn": "123456", "authorIds": [1, 2] }

// authors collection
{ "_id": 1, "name": "John Doe",   "country": "USA", "bio": "..." }
{ "_id": 2, "name": "Jane Smith", "country": "UK",  "bio": "..." }
```

**When to use**
- You need to search authors by name, country, or other fields
- Authors appear in multiple books and have rich profiles
- Author data needs to be updated independently

**Pros**
- Authors collection can be indexed directly — fast searches
- Updating an author updates it once for all books
- Authors can be queried independently (e.g. all books by an author)

**Cons**
- Requires two queries to fetch a book with its authors
- Slightly more complex code


---

**Summary**

| | Embedding | Linking |
|---|---|---|
| Author count | Small and bounded | Any size |
| Always read with book? | Yes | No |
| Frequent author searches? | No | Yes |
| Independent author updates? | No | Yes |
| Query style | Single query | Two queries |
| Index on authors? | Not effective | Yes — fast |

