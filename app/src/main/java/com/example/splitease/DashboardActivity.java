package com.example.splitease;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {
    private String username; // Variable to hold the username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Set the dashboard layout

        // Get the username from the intent
        username = getIntent().getStringExtra("username");

        // Initialize buttons
        Button buttonAddExpense = findViewById(R.id.buttonAddExpense);
        Button buttonShowExpenses = findViewById(R.id.buttonShowExpenses);
        Button buttonLogout = findViewById(R.id.buttonLogout);

        // Set up click listeners for buttons
        buttonAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AddExpenseActivity.class);
            intent.putExtra("username", username); // Pass the username to the AddExpenseActivity
            startActivity(intent);
        });

        buttonShowExpenses.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ShowExpensesActivity.class);
            intent.putExtra("username", username); // Pass the username to the ShowExpensesActivity
            startActivity(intent);
        });

        buttonLogout.setOnClickListener(v -> {
            // Handle logout logic, e.g., finish the activity or clear user session
            finish(); // Close the dashboard activity
        });
    }
}
