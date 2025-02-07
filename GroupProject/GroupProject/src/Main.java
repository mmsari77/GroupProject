import Places.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/queuefree_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "0000";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("connected");
        } catch (SQLException ex) {
            System.out.println(" not connected " + ex.getMessage());
            return;
        }

        while (true) {
            System.out.println("Welcome to QueueFree!");
            System.out.println("\nChoose one of the following options:");
            System.out.println("1. Register");
            System.out.println("2. Join a queue");
            System.out.println("3. Check status");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nWhere do you want to register?");
                    System.out.println("1. Hospital service");
                    System.out.println("2. Restaurant service");
                    System.out.println("3. PSC(ЦОН) service");
                    System.out.println("4. Exit");
                    System.out.print("Enter your choice: ");
                    int registerChoice = in.nextInt();
                    in.nextLine();

                    if (registerChoice == 1) {
                        registerForHospital(in);
                    } else if (registerChoice == 2) {
                        registerForRestaurant(in);
                    } else if (registerChoice == 3) {
                        registerForPSC(in);
                    } else {
                        System.out.println("Invalid registration option.");
                    }
                    break;

                case 2:
                    break;

                case 3:
                    List<User> users = getUsers();
                    if (users.isEmpty()) {
                        System.out.println("No users found.");
                    } else {
                        for (User user : users) {
                            System.out.println(user);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void registerForHospital(Scanner in) {
        while (true) {
            System.out.println("\n1. View Doctors");
            System.out.println("2. Make an appointment");
            System.out.println("3. View my appointments");
            System.out.println("4. Rate a doctor");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");

            if (!in.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                in.next();
                continue;
            }

            int rateChoice = in.nextInt();
            in.nextLine();

            switch (rateChoice) {
                case 1 -> viewDoctors(in);
                case 2 -> makeAppointment(in, "Hospital");
                case 3 -> viewMyAppointments();
                case 4 -> rateDoctor(in);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private static void viewDoctors(Scanner in) {
        System.out.println("\n1. Dr. Alikhan Bazarbayev");
        System.out.println("2. Dr. Saule Aitzhanova");
        System.out.println("3. Dr. Nurgali Serikbayev");
        System.out.println("4. Dr. Gulzhan Nurtayeva");
        System.out.println("5. Dr. Aiman Tursunova");
        System.out.println("0. Go back");
        System.out.print("Enter your choice: ");
        int doctorChoice = in.nextInt();
        in.nextLine();

        switch (doctorChoice) {
            case 1:
                System.out.println("\nDr. Alikhan Bazarbayev is a highly experienced cardiologist with over 15 years of practice.");
                System.out.println("Specialization: Cardiology");
                break;
            case 2:
                System.out.println("\nDr. Saule Aitzhanova is a renowned pediatrician with a strong reputation for her patient care.");
                System.out.println("Specialization: Pediatrics");
                break;
            case 3:
                System.out.println("\nDr. Nurgali Serikbayev is an expert in internal medicine with a focus on holistic treatment.");
                System.out.println("Specialization: Internal Medicine");
                break;
            case 4:
                System.out.println("\nDr. Gulzhan Nurtayeva is a skilled neurologist with significant contributions to research.");
                System.out.println("Specialization: Neurology");
                break;
            case 5:
                System.out.println("\nDr. Aiman Tursunova is a dedicated gynecologist known for her compassionate care.");
                System.out.println("Specialization: Gynecology");
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void makeAppointment(Scanner in, String place) {
        System.out.print("Enter your name: ");
        String name = in.nextLine();

        System.out.print("Enter your surname: ");
        String surname = in.nextLine();

        System.out.print("Enter your gender (M/F): ");
        String gender = in.nextLine();
        if (gender.equals("M")) {
            gender = "Male";
        } else if (gender.equals("F")) {
            gender = "Female";
        }

        System.out.print("Enter your phone number: ");
        String phone = in.nextLine();

        addUser(new User(0, place, name, surname, gender, phone));
        System.out.println("User registered successfully!");
    }

    private static void rateDoctor(Scanner in) {
        System.out.print("Enter doctor's ID : ");
        String doctor1 = "Dr. Alikhan Bazarbayev";
        String doctor2 = "Dr. Saule Aitzhanova";
        String doctor3 = "Dr. Nurgali Serikbayev";
        String doctor4 = "Dr. Gulzhan Nurtayeva";
        String doctor5 = "Dr. Aiman Tursunova";

        System.out.println("\n1 "+ doctor1);
        System.out.println("2 "+ doctor2);
        System.out.println("3 "+ doctor3);
        System.out.println("4 "+ doctor4);
        System.out.println("5 "+ doctor5);
        String[] arrayOfDoctors = {doctor1, doctor2, doctor3, doctor4, doctor5};

        int doctorChoice = in.nextInt();

        switch (doctorChoice){
            case 1:
                System.out.println("Rate doctor from 1 to 10");
                int doctorChoice1 = in.nextInt();
                System.out.println("Your rate for " + arrayOfDoctors[doctorChoice-1] + " is " + doctorChoice1);
                break;
                case 2:
                    System.out.println("Rate doctor from 1 to 10");
                    int doctorChoice2 = in.nextInt();
                    System.out.println("Your rate for " + arrayOfDoctors[doctorChoice-1] + " is " + doctorChoice2);
        }


    }

    private static void viewMyAppointments() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your phone number: ");
        String phoneNumber = in.nextLine();

        String query = "SELECT * FROM users WHERE phone = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No appointments found for this phone number.");
            } else {
                System.out.println("Your appointments:");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String place = rs.getString("place");

                    System.out.printf("Appointment ID: %d, Place: %s", id, place);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }
    }

    private static void registerForRestaurant(Scanner in) {
        System.out.println("Registering for Restaurant...");
    }

    private static void registerForPSC(Scanner in) {
        System.out.println("Registering for PSC...");
    }

    private static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, place , name, surname, gender, phone FROM users")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String place = rs.getString("place");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");

                users.add(new User(id, place, name, surname, gender, phone));
            }
        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
        }
        return users;
    }

    private static void addUser(User user) {
        String query = "INSERT INTO users (place, name, surname, gender, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getPlace());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getSurname());
            pstmt.setString(4, user.getGender());
            pstmt.setString(5, user.getPhone());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected > 0 ? "User added successfully!" : "No rows affected.");
        } catch (SQLException ex) {
            System.out.printf("SQL error: %s%n", ex.getMessage());
        }
    }}