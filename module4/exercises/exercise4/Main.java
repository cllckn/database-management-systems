package cc.ku.dbms.module4.exercises.exercise4;

import java.sql.*;

/**
 * Full CRUD operations on the Northwind (nw) PostgreSQL database.
 * Table: customers (CustomerID, CompanyName, ContactName, Country)
 *
 * Ensure the PostgreSQL JDBC driver is on the classpath before running.
 */
public class Main {

    // ── Connection settings ───────────────────────────────────────────────────
    private static final String URL      = "jdbc:postgresql://localhost:5432/northwinddb";
    private static final String USER     = "lectureuser";
    private static final String PASSWORD = "lecturepassword";

    // ── Entry point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            if (conn != null)
                System.out.println("Connected to the database!\n");
            else {
                System.out.println("Connection attempt failed!");
                return;
            }

            // ── CREATE ────────────────────────────────────────────────────────
            System.out.println("=== CREATE ===");
            addNewCategory(conn, 10, "new category", "description");

            // ── READ (all) ────────────────────────────────────────────────────
            System.out.println("\n=== READ (all) ===");
            readAllCategories(conn);

            // ── READ (single) ─────────────────────────────────────────────────
            System.out.println("\n=== READ (single: NEWCO) ===");
            readCategoryById(conn, 4);

            // ── UPDATE ────────────────────────────────────────────────────────
            System.out.println("\n=== UPDATE ===");
            updateCustomer(conn, "NEWCO", "Updated Company Ltd.", "Bob Jones", "France");

            // ── READ after update ─────────────────────────────────────────────
            System.out.println("\n=== READ after UPDATE ===");
            readCategoryById(conn, 5);

            // ── DELETE ────────────────────────────────────────────────────────
            System.out.println("\n=== DELETE ===");
            deleteCategory(conn, 10);

            // ── Confirm deletion ──────────────────────────────────────────────
            System.out.println("\n=== READ after DELETE ===");
            readCategoryById(conn, 5);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ── CREATE ────────────────────────────────────────────────────────────────
    /**
     * Inserts a new customer row.
     * Uses a PreparedStatement to prevent SQL injection.
     */
    public static void addNewCategory(Connection conn,
                                      int CategoryID,
                                      String CategoryName,
                                      String Description
                                      ) throws SQLException {

        String sql = "INSERT INTO categories (\"CategoryID\", \"CategoryName\", \"Description\") "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CategoryID);
            pstmt.setString(2, CategoryName);
            pstmt.setString(3, Description);


            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s). CategoryID: " + CategoryID);
        }
    }

    // ── READ (all) ────────────────────────────────────────────────────────────
    /**
     * Reads and prints all customers.
     */
    public static void readAllCategories(Connection conn) throws SQLException {

        String sql = "SELECT \"CategoryID\", \"CategoryName\", \"Description\" FROM categories";

        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {

            int count = 0;
            while (rs.next()) {
                printCategory(rs);
                count++;
            }
            System.out.println("Total rows: " + count);
        }
    }

    // ── READ (single) ─────────────────────────────────────────────────────────
    /**
     * Reads and prints a single customer by CustomerID.
     */
    public static void readCategoryById(Connection conn, int categoryID) throws SQLException {

        String sql = "SELECT \"CategoryID\", \"CategoryName\", \"Description\" FROM categories WHERE \"CategoryID\" = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    printCategory(rs);
                else
                    System.out.println("No customer found with ID: " + categoryID);
            }
        }
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    /**
     * Updates an existing customer's CompanyName, ContactName, and Country.
     */
    public static void updateCustomer(Connection conn,
                                      String customerID,
                                      String newCompanyName,
                                      String newContactName,
                                      String newCountry) throws SQLException {

        String sql = "UPDATE customers "
                + "SET \"CompanyName\" = ?, \"ContactName\" = ?, \"Country\" = ? "
                + "WHERE \"CustomerID\" = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newCompanyName);
            pstmt.setString(2, newContactName);
            pstmt.setString(3, newCountry);
            pstmt.setString(4, customerID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("Updated CustomerID: " + customerID);
            else
                System.out.println("No customer found with ID: " + customerID);
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    /**
     * Deletes a customer by CustomerID.
     */
    public static void deleteCategory(Connection conn, int categoryID) throws SQLException {

        String sql = "DELETE FROM categories WHERE \"CategoryID\" = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("Deleted CustomerID: " + categoryID);
            else
                System.out.println("No customer found with ID: " + categoryID);
        }
    }

    // ── Helper ────────────────────────────────────────────────────────────────
    /**
     * Prints a single customer row from the current ResultSet position.
     */
    private static void printCategory(ResultSet rs) throws SQLException {
        System.out.printf("ID: %-5d | Category: %-20s | Description: %-30s\n ",
                rs.getInt("CategoryID"),
                rs.getString("CategoryName"),
                rs.getString("Description"));
    }
}