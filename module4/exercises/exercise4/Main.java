import java.sql.*;

/**
 * Full CRUD operations on the Northwind (nw) PostgreSQL database.
 * Table: categories
 * - CategoryID (Primary Key)
 * - CategoryName
 * - Description
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


            // ── READ (all) ────────────────────────────────────────────────────
            System.out.println("\n=== READ (all) ===");
            readAllCategories(conn);

            // ── READ (single) ─────────────────────────────────────────────────
            System.out.println("\n=== READ (single: NEWCO) ===");
            readCategoryById(conn, 4);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // ── READ (all) ────────────────────────────────────────────────────────────
    /**
     * Reads and prints all categories.
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
     * Reads and prints a single customer by CategoryID.
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



    // ── Helper ────────────────────────────────────────────────────────────────
    /**
     * Prints a single category row from the current ResultSet position.
     */
    private static void printCategory(ResultSet rs) throws SQLException {
        System.out.printf("ID: %-5d | Category: %-20s | Description: %-30s\n ",
                rs.getInt("CategoryID"),
                rs.getString("CategoryName"),
                rs.getString("Description"));
    }
}
