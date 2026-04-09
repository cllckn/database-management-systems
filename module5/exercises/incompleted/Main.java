package cc.ku.dbms.module5.exercises.exercise1.incompleted;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
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
public class Main {

    // ── Connection settings ───────────────────────────────────────────────────
    private static final String URI         ="mongodb+srv://lectureuser:lecturepassword@cluster0.zzzplef.mongodb.net/?appName=Cluster0";
    //private static final String URI         = "mongodb+srv://lectureuser:lecturepassword@cluster0.zxbhndn.mongodb.net/?appName=Cluster0";
    private static final String DB_NAME     = "northwinddb";
    private static final String COLLECTION  = "categories";

    // ── Entry point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {

        // try-with-resources: MongoClient closes automatically
        try (MongoClient mongoClient = MongoClients.create(URI)) {

            MongoDatabase database   = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> customers = database.getCollection(COLLECTION);

            System.out.println("Connected to MongoDB — database: " + DB_NAME + "\n");

            // ── READ (all) ────────────────────────────────────────────────────
            System.out.println("\n=== READ (all) ===");
            readAllCustomers(customers);

            // ── READ (single) ─────────────────────────────────────────────────
            System.out.println("\n=== READ (single: NEWCO) ===");
            readCustomerById(customers, "NEWCO");

        }
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