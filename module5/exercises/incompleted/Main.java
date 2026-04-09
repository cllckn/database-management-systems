package cc.st;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Full CRUD operations on the Northwind MongoDB database.
 * Collection: categories
 * Fields: _id, name, description
 *
 * Dependencies (add to pom.xml ):
 *
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
            MongoCollection<Document> categories = database.getCollection(COLLECTION);

            System.out.println("Connected to MongoDB — database: " + DB_NAME + "\n");

            // ── READ (all) ────────────────────────────────────────────────────
            System.out.println("\n=== READ (all) ===");
            readAllCategories(categories);

            // ── READ (single) ─────────────────────────────────────────────────
            System.out.println("\n=== READ (single: 69d6a6652cec4485bc7701fd) ===");
            readCategoriesById(categories, "69d6a6652cec4485bc7701fd");

        }
    }


    // ── READ (all) ────────────────────────────────────────────────────────────
    /**
     * Reads and prints all categories documents.
     */
    public static void readAllCategories(MongoCollection<Document> collection) {

        FindIterable<Document> docs = collection.find();

        int count = 0;
        for (Document doc : docs) {
            printCategory(doc);
            count++;
        }
        System.out.println("Total documents: " + count);
    }

    // ── READ (single category) ─────────────────────────────────────────────────────────

    public static void readCategoriesById(MongoCollection<Document> collection,
                                          String id) {

        // Convert String to ObjectId
        //String idString = "65f1a2b3c4d5e6f7a8b9c0d1";
        ObjectId objectId = new ObjectId(id);

        Bson filter = Filters.eq("_id", objectId);
        Document doc = collection.find(filter).first();

        if (doc != null)
            printCategory(doc);
        else
            System.out.println("No category found with id: " + id);
    }


    // ── Helper ────────────────────────────────────────────────────────────────
    /**
     * Prints a customer document in a formatted table row.
     */
    private static void printCategory(Document doc) {
        Object id = doc.get("_id");
        String idString = (id != null) ? id.toString() : null;
        System.out.printf("ID: %-10s | name: %-30s | description: %-20s\n ",
                idString,
                doc.getString("name"),
                doc.getString("description"));
    }
}
