# Hands-on Exercise1: Design and Implementation of a Bank Account Database


## **Objective**
In this assignment, you will design a **banking system database** while applying essential 
**SQL constraints** such as **NOT NULL, DEFAULT, PRIMARY KEY, FOREIGN KEY (NO ACTION), CHECK,** and **UNIQUE**.

## **Business Rules**

1. A customer has an id, first name, last name, email, phone number, date of birth, and registered date.
2. A customer can have multiple accounts, but each account must belong to a single customer.
3. Each account has an id, customer id, unique account number, account type, balance, and opening date.
4. An account must be of type "Individual" or "Business".
5. An account balance must be non-negative.
6. Each transaction has an id, account id, amount, transaction type ("Deposit" or "Withdrawal"), and transaction date.
7. Each transaction must be linked to an account, an account may have many transactions.
8. Transaction amounts must always be greater than zero.
9. System-generated timestamps should be recorded for customer registration, account opening, and transactions.



## **Task 1: Design an ER Diagram**
- Using **Crow’s Foot Notation**, design an **ER Diagram** representing the banking system based on the business rules above.



## **Task 2: Define a Relational Schema**
- Construct a **text-based relational schema** using the **business rules**.
- Ensure that **data types** and **constraints** are clearly specified.


## **Task 3: Implement Database Schema**
Define the following **tables** with appropriate **data types** and constraints:


### **BankDB Database**

Construct the BankDB database.

### **Customers Table**
Stores customer information.

| Column Name  | Data Type | Constraints | Description |
|-------------|----------|------------|-------------|
| `id` | `SERIAL` | `PRIMARY KEY` | Unique identifier for each customer |
| `first_name` | `VARCHAR(50)` | `NOT NULL` | Customer’s first name |
| `last_name` | `VARCHAR(50)` | `NOT NULL` | Customer’s last name |
| `email` | `VARCHAR(100)` | `UNIQUE, NOT NULL` | Ensures each email is unique |
| `phone` | `VARCHAR(15)` | `UNIQUE` | Customer phone number (optional but must be unique) |
| `date_of_birth` | `DATE` | `NOT NULL` | Customer’s date of birth |
| `registered_on` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | Auto-filled registration date |

### **Accounts Table**
Stores bank account details.

| Column Name      | Data Type | Constraints                                              | Description |
|------------------|----------|----------------------------------------------------------|-------------|
| `id`             | `SERIAL` | `PRIMARY KEY`                                            | Unique identifier for each account |
| `customer_id`    | `INTEGER` | `FOREIGN KEY` references `id` in `Customers` (NO ACTION) | Links to Customers table |
| `account_number` | `VARCHAR(15)` | `UNIQUE, NOT NULL`                                       | Ensures each account number is unique |
| `account_type`   | `VARCHAR(20)` | `CHECK (account_type IN ('Individual', 'Business'))`     | Restricts account type to Savings or Checking |
| `balance`        | `DECIMAL(15,2)` | `CHECK (balance >= 0) DEFAULT 0.00`                      | Ensures non-negative balance |
| `opening_date`   | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP`                              | Auto-filled account creation date |

### **Transactions Table**
Stores account transactions.

| Column Name  | Data Type | Constraints | Description |
|-------------|----------|------------|-------------|
| `id` | `SERIAL` | `PRIMARY KEY` | Unique identifier for each transaction |
| `account_id` | `INTEGER` | `FOREIGN KEY` references `id` in `Accounts` (NO ACTION) | Links to Accounts table |
| `amount` | `DECIMAL(15,2)` | `CHECK (amount > 0)` | Ensures only positive transaction amounts |
| `transaction_type` | `VARCHAR(10)` | `CHECK (transaction_type IN ('Deposit', 'Withdrawal'))` | Restricts values to Deposit or Withdrawal |
| `transaction_date` | `TIMESTAMP` | `DEFAULT CURRENT_TIMESTAMP` | Auto-filled transaction timestamp |



## **Task 4: Execution Steps**
1. **Write SQL statements** to **initialize the database**.
2. **Write SQL statements** to **construct tables** with appropriate constraints.
3. **Test constraints** by:
    - Inserting a customer without an email (**should fail**).
    - Inserting an account with a negative balance (**should fail**).
    - Inserting a transaction with an invalid type (**should fail**).
    - Inserting valid data and verifying the records.







---
   
# **Hands-on Exercise 2: Advanced Constraints and Database Normalization**

## **Objective**
In this exercise, you will extend the **banking system database** by:
- Introducing **new constraints** to improve data consistency.
- **Normalizing** the database by creating separate tables for **account types** and **transaction types** to avoid data repetition.



## **New Business Rules**
1. A **customer's first name and last name** must start with a capital letter.
2. A **customer's date of birth** must ensure they are at least **18 years old**.
3. An **account type** should be stored in a separate table and referenced in the **Accounts** table.
4. A **transaction type** should be stored in a separate table and referenced in the **Transactions** table.



## **Task 1: Extend the ER Diagram**
- Modify the **ER Diagram** using **Crow’s Foot Notation** to include:
   - A new **AccountTypes** table.
   - A new **TransactionTypes** table.
   - Updated relationships to reference these tables.



## **Task 2: Define the Extended Relational Schema**
- Construct an **updated text-based relational schema** considering the new business rules.



## **Task 3: Implement the Extended Database Schema**
Define the following **tables** with appropriate **data types** and constraints:





--- 

# **Hands-on Exercise 3: Design and Implementation of a Blog System Database**

## **Objective**
In this assignment, you will design a **blogging system database** while applying essential **SQL constraints**, including **NOT NULL, DEFAULT, PRIMARY KEY, FOREIGN KEY (NO ACTION), CHECK,** and **UNIQUE**.

---

## **Business Rules**
1. Each **author** must have a unique **email** and **username**.
2. An **author** can write multiple **posts**, but a **post** must belong to a single **author**.
3. Each **post** has a **title, content, and a timestamp** of when it was published.
4. Each **post** must belong to a **category**.
5. A **category name** must be unique.
6. A **post** can have multiple **images**, but each **image** must belong to a single **post**.
7. System-generated timestamps should be recorded for **author registration, post publishing, and image uploads**.

---

## **Task 1: Design an ER Diagram**
- Using **Crow’s Foot Notation**, design an **ER Diagram** representing the blog system based on the business rules above.

---

## **Task 2: Define a Relational Schema**
- Construct a **text-based relational schema** using the **business rules**.
- Ensure that **data types** and **constraints** are clearly specified.

---

## **Task 3: Implement Database Schema**

Define the following **tables** with appropriate **data types** and constraints:

### **Authors Table**
Stores information about authors.

| Column Name   | Data Type     | Constraints                        | Description                            |
|--------------|--------------|------------------------------------|----------------------------------------|
| `id`         | `SERIAL`      | `PRIMARY KEY`                     | Unique identifier for each author     |
| `username`   | `VARCHAR(50)` | `UNIQUE, NOT NULL`                | Ensures each username is unique       |
| `email`      | `VARCHAR(100)`| `UNIQUE, NOT NULL`                | Ensures each email is unique          |
| `registered_on` | `TIMESTAMP`  | `DEFAULT CURRENT_TIMESTAMP`      | Auto-filled registration date         |

---

### **Categories Table**
Stores post categories.

| Column Name | Data Type     | Constraints         | Description                      |
|------------|--------------|---------------------|----------------------------------|
| `id`       | `SERIAL`      | `PRIMARY KEY`      | Unique identifier for category  |
| `name`     | `VARCHAR(50)` | `UNIQUE, NOT NULL` | Unique name for each category   |

---

### **Posts Table**
Stores blog posts.

| Column Name   | Data Type     | Constraints                                      | Description                               |
|--------------|--------------|--------------------------------------------------|-------------------------------------------|
| `id`         | `SERIAL`      | `PRIMARY KEY`                                   | Unique identifier for each post          |
| `author_id`  | `INTEGER`     | `FOREIGN KEY` references `id` in `Authors` (NO ACTION) | Links post to an author        |
| `category_id`| `INTEGER`     | `FOREIGN KEY` references `id` in `Categories` (NO ACTION) | Links post to a category      |
| `title`      | `VARCHAR(255)`| `NOT NULL`                                     | Title of the post                        |
| `content`    | `TEXT`        | `NOT NULL`                                     | Main content of the post                 |
| `published_on` | `TIMESTAMP`  | `DEFAULT CURRENT_TIMESTAMP`                   | Auto-filled post publication timestamp   |

---

### **Post Images Table**
Stores images related to blog posts.

| Column Name | Data Type     | Constraints                                     | Description                         |
|------------|--------------|-------------------------------------------------|-------------------------------------|
| `id`       | `SERIAL`      | `PRIMARY KEY`                                  | Unique identifier for each image   |
| `post_id`  | `INTEGER`     | `FOREIGN KEY` references `id` in `Posts` (NO ACTION) | Links image to a post          |
| `image_url`| `TEXT`        | `NOT NULL`                                     | URL/path of the image              |
| `uploaded_on` | `TIMESTAMP`  | `DEFAULT CURRENT_TIMESTAMP`                   | Auto-filled upload timestamp       |

---

## **Task 4: Execution Steps**
1. **Write SQL statements** to **initialize the database**.
2. **Write SQL statements** to **construct tables** with appropriate constraints.
3. **Test constraints** by:
    - Inserting an author without an email (**should fail**).
    - Inserting a post without a category (**should fail**).
    - Inserting an image without linking it to a post (**should fail**).
    - Inserting valid data and verifying the records.
