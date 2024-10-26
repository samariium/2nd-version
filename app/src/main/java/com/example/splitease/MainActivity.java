package com.example.splitease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

// Main activity for the application, where users can add and view expenses
public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper; // DatabaseHelper instance for database operations
    private LinearLayout emptyView; // Layout to display when there are no expenses
    private String username; // Variable to hold the username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the XML layout for MainActivity

        // Initialize the DatabaseHelper and fetch the username
        dbHelper = new DatabaseHelper(this);
        username = getUsername(); // Replace with actual method to retrieve username

        // Initialize the empty view
        emptyView = findViewById(R.id.emptyView); // Ensure this ID exists in your XML layout
        emptyView.setVisibility(View.GONE); // Initially hidden

        // Load expenses from the database
        loadExpenses(); // Load expenses from the database

        // Set up button click listeners
        setupButtons(); // Call method to set up buttons
    }

    // Method to load expenses from the database
    private void loadExpenses() {
        // Fetch expenses from the database
        ArrayList<Expense> expenseList = dbHelper.getAllExpenses(username); // Get expenses

        // Check if expenses list is empty and update empty view accordingly
        updateEmptyView(expenseList.isEmpty());
    }

    // Method to update visibility of the empty view based on the expense list
    private void updateEmptyView(boolean isEmpty) {
        if (isEmpty) {
            emptyView.setVisibility(View.VISIBLE); // Show empty view
        } else {
            emptyView.setVisibility(View.GONE); // Hide empty view
        }
    }

    // Method to set up button click listeners
    private void setupButtons() {
        Button buttonAddExpense = findViewById(R.id.buttonAddExpense); // Ensure this ID exists in your XML layout
        Button buttonShowExpenses = findViewById(R.id.buttonShowExpenses); // Ensure this ID exists in your XML layout

        buttonAddExpense.setOnClickListener(v -> addExpense()); // Listener to add a new expense
        buttonShowExpenses.setOnClickListener(v -> showExpenses()); // Listener to show existing expenses
    }

    // Method to navigate to the ShowExpensesActivity
    private void showExpenses() {
        Intent intent = new Intent(MainActivity.this, ShowExpensesActivity.class);
        intent.putExtra("username", username); // Pass the username to the next activity if needed
        startActivity(intent); // Start ShowExpensesActivity
    }

    // Method to navigate to the AddExpenseActivity
    private void addExpense() {
        Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
        intent.putExtra("username", username); // Pass the username to the next activity if needed
        startActivity(intent); // Start AddExpenseActivity
    }

    // Placeholder method to retrieve the username (replace with actual logic)
    private String getUsername() {
        // TODO: Implement actual logic to retrieve the username
        return "example_username"; // Replace with the actual method to retrieve username
    }
}
