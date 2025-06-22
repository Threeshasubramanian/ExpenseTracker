public class Expense {
    private double amount;
    private String category;
    private String description;
    private String date;

    public Expense(double amount, String category, String description, String date) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("Rs.%.2f | %s | %s | %s", amount, category, description, date);
    }

    public String toFileString() {
        return amount + "," + category + "," + description + "," + date;
    }
}
