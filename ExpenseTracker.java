import java.util.*;
import java.io.*;

public class ExpenseTracker {
    private static List<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadExpensesFromFile();

        while (true) {
            System.out.println("\n--- Personal Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Total Spent");
            System.out.println("4. View Spending by Category");
            System.out.println("5. Save and Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addExpense(sc);
                    break;
                case 2:
                    viewAllExpenses();
                    break;
                case 3:
                    viewTotalSpent();
                    break;
                case 4:
                    viewSpendingByCategory();
                    break;
                case 5:
                    saveExpensesToFile();
                    System.out.println("Expenses saved. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addExpense(Scanner sc) {
        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // consume newline

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter description: ");
        String description = sc.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        Expense exp = new Expense(amount, category, description, date);
        expenses.add(exp);
        System.out.println("Expense added!");
    }

    private static void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses to display.");
            return;
        }
        System.out.println("\n--- All Expenses ---");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println(expenses.get(i));
        }
    }

    private static void viewTotalSpent() {
        double total = 0;
        for (int i = 0; i < expenses.size(); i++) {
            total += expenses.get(i).getAmount();
        }
        System.out.println("Total spent: Rs." + String.format("%.2f", total));
    }

    private static void viewSpendingByCategory() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses to display.");
            return;
        }
        Map<String, Double> categoryTotals = new HashMap<>();
        for (int i = 0; i < expenses.size(); i++) {
            Expense exp = expenses.get(i);
            String category = exp.getCategory();
            double currentTotal = categoryTotals.getOrDefault(category, 0.0);
            categoryTotals.put(category, currentTotal + exp.getAmount());
        }

        System.out.println("\n--- Spending by Category ---");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.println(entry.getKey() + ": Rs." + String.format("%.2f", entry.getValue()));
        }

    }

    private static void saveExpensesToFile() {
        try {
            PrintWriter writer = new PrintWriter("expenses.txt");
            for (int i = 0; i < expenses.size(); i++) {
                writer.println(expenses.get(i).toFileString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private static void loadExpensesFromFile() {
        File file = new File("expenses.txt");
        if (!file.exists()) {
            return; // No file to load
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",", -1);
                double amount = Double.parseDouble(parts[0]);
                String category = parts[1];
                String description = parts[2];
                String date = parts[3];

                expenses.add(new Expense(amount, category, description, date));
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}
