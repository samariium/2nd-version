package com.example.splitease;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText editTextAmount; // EditText for amount input
    private EditText editTextDescription; // EditText for description input
    private DatabaseHelper dbHelper; // DatabaseHelper instance for database operations
    private String username; // Variable to hold the username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense); // Set the XML layout

        // Initialize UI components
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextDescription = findViewById(R.id.editTextDescription);
        Button buttonSubmitExpense = findViewById(R.id.buttonSubmitExpense);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username"); // Get the username passed from the previous activity

        // Set up button click listener
        buttonSubmitExpense.setOnClickListener(v -> submitExpense()); // Call method to submit expense
    }

    // Method to submit the expense to the database
    private void submitExpense() {
        String amountString = editTextAmount.getText().toString().trim(); // Get amount input
        String description = editTextDescription.getText().toString().trim(); // Get description input

        if (amountString.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show(); // Alert if fields are empty
            return;
        }

        double amount = Double.parseDouble(amountString); // Parse amount to double

        // Create Expense object and save it to the database
        Expense newExpense = new Expense(0, amount, description, username); // Pass the username to the constructor
        dbHelper.addExpense(newExpense); // Add the expense to the database

        Toast.makeText(this, "Expense added successfully!", Toast.LENGTH_SHORT).show(); // Confirmation message
        finish(); // Close the activity and return to the previous one
    }
}
