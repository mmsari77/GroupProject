import java.sql.*;
import java.util.Scanner;

public class QueueService {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";

    public void listAvailableServices() {
        System.out.println("\n" + CYAN + "════════════════════════════════════════" + RESET);
        System.out.println(" " + GREEN + "📋 Available Services:" + RESET);
        System.out.println(CYAN + "════════════════════════════════════════" + RESET);
        System.out.println(" 1️⃣  Hospital - General doctor");
        System.out.println(" 2️⃣  Hospital - Cardiologist");
        System.out.println(" 3️⃣  Hospital - Neurologist");
        System.out.println(" 4️⃣  Restaurant - Table reservation");
        System.out.println(" 5️⃣  PSC - Document processing");
        System.out.println(" 6️⃣  PSC - Passport renewal");
        System.out.println(CYAN + "════════════════════════════════════════\n" + RESET);
    }

    public void joinQueue(Scanner in) {
        listAvailableServices();

        System.out.print("\n🔹 " + YELLOW + "Enter service (write exactly as in the list): " + RESET);
        String service = in.nextLine();

        System.out.print("Enter your email: ");
        String email = in.nextLine();

        System.out.print("Any special request or comment? (optional): ");
        String comments = in.nextLine();

        if (comments.isEmpty()) {
            comments = null;
        }

        String query = """
            INSERT INTO queue_entries (user_id, service, position, comments)
            VALUES ((SELECT id FROM users WHERE email = ?), ?, 
            (SELECT COALESCE(MAX(position), 0) + 1 FROM queue_entries WHERE service = ?), ?)
        """;

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, service);
            pstmt.setString(3, service);
            pstmt.setString(4, comments);
            pstmt.executeUpdate();
            System.out.println(GREEN + "✅ You joined the queue for " + service + "!" + RESET);

            if (comments != null) {
                System.out.println(BLUE + "📌 Your request: " + comments + RESET);
            }
        } catch (SQLException ex) {
            System.out.println(RED + "❌ SQL error: " + ex.getMessage() + RESET);
        }
    }

    public void checkQueueStatus(Scanner in) {
        System.out.print("\n🔹 Enter your email: ");
        String email = in.nextLine();

        String query = "SELECT position, service, comments FROM queue_entries WHERE user_id = (SELECT id FROM users WHERE email = ?)";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int position = rs.getInt("position");
                String service = rs.getString("service");
                String comments = rs.getString("comments");

                System.out.println("\n" + CYAN + "════════════════════════════════════════" + RESET);
                System.out.println(" " + GREEN + "📊 Queue Status:" + RESET);
                System.out.println(CYAN + "════════════════════════════════════════" + RESET);
                System.out.println("📌 Service: " + YELLOW + service + RESET);
                System.out.println("📊 Position in queue: " + GREEN + position + RESET);

                if (comments != null) {
                    System.out.println("📝 Your request: " + BLUE + comments + RESET);
                }
                System.out.println(CYAN + "════════════════════════════════════════\n" + RESET);
            } else {
                System.out.println(RED + "❌ You are not in the queue." + RESET);
            }
        } catch (SQLException ex) {
            System.out.println(RED + "❌ SQL error: " + ex.getMessage() + RESET);
        }
    }

    public void cancelQueue(Scanner in) {
        System.out.print("Enter your email: ");
        String email = in.nextLine();

        String query = "DELETE FROM queue_entries WHERE user_id = (SELECT id FROM users WHERE email = ?)";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query)) {
            pstmt.setString(1, email);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println(GREEN + "✅ You have been removed from the queue." + RESET);
            } else {
                System.out.println(RED + "❌ You were not in the queue." + RESET);
            }
        } catch (SQLException ex) {
            System.out.println(RED + "❌ SQL error: " + ex.getMessage() + RESET);
        }
    }
}
