import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/queuefree_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "0000";
    private static Connection conn;

    public static boolean connect() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✅ Connected to database.");
            createTables();
            return true;
        } catch (SQLException ex) {
            System.out.println("❌ Database error: " + ex.getMessage());
            return false;
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    private static void createTables() {
        String usersTableQuery = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                salt TEXT NOT NULL,
                created_at TIMESTAMP DEFAULT NOW()
            )
        """;

        String queueTableQuery = """
            CREATE TABLE IF NOT EXISTS queue_entries (
                id SERIAL PRIMARY KEY,
                user_id INT REFERENCES users(id) ON DELETE CASCADE,
                service VARCHAR(100) NOT NULL,
                position INT NOT NULL,
                status VARCHAR(20) DEFAULT 'waiting',
                created_at TIMESTAMP DEFAULT NOW()
            )
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(usersTableQuery);
            stmt.executeUpdate(queueTableQuery);
            System.out.println("✅ Tables are ready!");
        } catch (SQLException ex) {
            System.out.println("❌ SQL error: " + ex.getMessage());
        }
    }
}
