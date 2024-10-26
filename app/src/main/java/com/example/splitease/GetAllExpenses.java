package com.example.splitease;

import java.util.ArrayList;

// Class to handle fetching all expenses from the database
public class GetAllExpenses {
    // Instance of DatabaseHelper to interact with the database
    private DatabaseHelper dbHelper;

    // Constructor to initialize the DatabaseHelper object
    public GetAllExpenses(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper; // Assign the provided dbHelper
    }

    // Method to fetch all expenses from the database
    public ArrayList<Expense> fetchAllExpenses(String username) { // Accept username as a parameter
        ArrayList<Expense> expenses = new ArrayList<>(); // Create a list to hold the expenses

        // Check if username is not null or empty to avoid passing invalid value
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        try {
            // Get the list of expenses from the database using dbHelper
            expenses = dbHelper.getAllExpenses(username); // Pass the username
        } catch (Exception e) {
            // Handle any exceptions that might occur during database access
            e.printStackTrace(); // Print the stack trace for debugging
        }

        return expenses; // Return the fetched list of expenses
    }
}
