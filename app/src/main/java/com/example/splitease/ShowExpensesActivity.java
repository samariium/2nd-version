package com.example.splitease;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent; // Make sure to import Intent
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ShowExpensesActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper; // DatabaseHelper instance for database operations
    private ListView listViewExpenses; // ListView to display the list of expenses
    private LinearLayout emptyView; // Layout to display when there are no expenses
    private String username; // Variable to hold the username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses); // Set the XML layout for ShowExpensesActivity

        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username"); // Get the username passed from MainActivity

        // Initialize UI components
        listViewExpenses = findViewById(R.id.listViewExpenses);
        emptyView = findViewById(R.id.emptyView);
        Button buttonAddExpense = findViewById(R.id.buttonAddExpense);

        // Set up the button click listener
        buttonAddExpense.setOnClickListener(v -> addExpense()); // Navigate to add expense

        // Load expenses from the database
        loadExpenses(); // Load expenses when the activity is created
    }

    // Method to load expenses from the database
    private void loadExpenses() {
        ArrayList<Expense> expenseList = dbHelper.getAllExpenses(username); // Fetch expenses from the database

        if (expenseList.isEmpty()) {
            listViewExpenses.setVisibility(View.GONE); // Hide ListView if no expenses
            emptyView.setVisibility(View.VISIBLE); // Show empty view
        } else {
            emptyView.setVisibility(View.GONE); // Hide empty view
            listViewExpenses.setVisibility(View.VISIBLE); // Show ListView

            // Set up the custom adapter to display expenses in the ListView
            ExpenseListAdapter adapter = new ExpenseListAdapter(this, expenseList);
            listViewExpenses.setAdapter(adapter); // Set adapter to the ListView
        }
    }

    // Method to navigate to AddExpenseActivity
    private void addExpense() {
        Intent intent = new Intent(ShowExpensesActivity.this, AddExpenseActivity.class);
        intent.putExtra("username", username); // Pass the username to the next activity if needed
        startActivity(intent); // Start AddExpenseActivity
    }
}
