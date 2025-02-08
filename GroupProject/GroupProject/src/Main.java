import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final AuthService authService = new AuthService();
    private static final QueueService queueService = new QueueService();

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        if (!DatabaseManager.connect()) {
            System.out.println(RED + "❌ Database connection failed. Exiting..." + RESET);
            return;
        }

        System.out.println(YELLOW + "\n🎉 Welcome to QueueFree! 🎉" + RESET);

        while (true) {
            System.out.println("\n" + CYAN + "═══════════════════════════════════" + RESET);
            System.out.println(" " + GREEN + "📌 MAIN MENU:" + RESET);
            System.out.println(CYAN + "═══════════════════════════════════" + RESET);
            System.out.println(" 1️⃣  Register");
            System.out.println(" 2️⃣  Login");
            System.out.println(" 3️⃣  List available services");
            System.out.println(" 4️⃣  Join a queue");
            System.out.println(" 5️⃣  Check queue status");
            System.out.println(" 6️⃣  Cancel queue entry");
            System.out.println(" 7️⃣  Exit");
            System.out.print("\n🔹 " + YELLOW + "Enter your choice: " + RESET);

            int choice = getIntInput();
            System.out.println(); // Пустая строка для читаемости

            switch (choice) {
                case 1 -> authService.register(in);
                case 2 -> authService.login(in);
                case 3 -> queueService.listAvailableServices();
                case 4 -> queueService.joinQueue(in);
                case 5 -> queueService.checkQueueStatus(in);
                case 6 -> queueService.cancelQueue(in);
                case 7 -> exit();
                default -> System.out.println(RED + "❌ Invalid choice. Try again." + RESET);
            }
        }
    }

    private static int getIntInput() {
        while (!in.hasNextInt()) {
            System.out.print(RED + "❌ Invalid input. Enter a number: " + RESET);
            in.next();
        }
        int value = in.nextInt();
        in.nextLine();
        return value;
    }

    private static void exit() {
        System.out.println(GREEN + "👋 Goodbye! Have a great day!" + RESET);
        System.exit(0);
    }
}
