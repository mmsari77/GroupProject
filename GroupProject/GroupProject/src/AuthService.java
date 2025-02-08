import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Scanner;

public class AuthService {
    public void register(Scanner in) {
        System.out.print("Enter your name: ");
        String name = in.nextLine();

        System.out.print("Enter your email: ");
        String email = in.nextLine();

        System.out.print("Enter your password: ");
        String password = in.nextLine();

        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        String query = "INSERT INTO users (name, email, password_hash, salt) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, hashedPassword);
            pstmt.setString(4, salt);
            pstmt.executeUpdate();
            System.out.println("✅ Registration successful!");
        } catch (SQLException ex) {
            System.out.println("❌ SQL error: " + ex.getMessage());
        }
    }


    public boolean login(Scanner in) {
        System.out.print("Enter your email: ");
        String email = in.nextLine();

        System.out.print("Enter your password: ");
        String password = in.nextLine();

        String query = "SELECT password_hash, salt FROM users WHERE email = ?";
        try (PreparedStatement pstmt = DatabaseManager.getConnection().prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String salt = rs.getString("salt");

                if (storedHash.equals(hashPassword(password, salt))) {
                    System.out.println("✅ Login successful!");
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("❌ SQL error: " + ex.getMessage());
        }
        System.out.println("❌ Invalid email or password.");
        return false;
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
