package com.example.splitease;

public class Expense {
    private int id; // Unique identifier for the expense
    private double amount; // Amount of the expense
    private String description; // Description of the expense
    private String username; // Username associated with the expense

    // Constructor to initialize the expense object with values for id, amount, description, and username
    public Expense(int id, double amount, String description, String username) {
        this.id = id; // Assign the id
        this.amount = amount; // Assign the amount
        this.description = description; // Assign the description
        this.username = username; // Assign the username
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username; // Getter for username
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", username='" + username + '\'' + // Include username in the toString output
                '}';
    }
}
