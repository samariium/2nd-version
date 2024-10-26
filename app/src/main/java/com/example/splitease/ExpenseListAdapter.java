package com.example.splitease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Custom adapter for displaying expenses in a ListView
public class ExpenseListAdapter extends ArrayAdapter<Expense> {
    private Context context;
    private ArrayList<Expense> expenseList;

    public ExpenseListAdapter(Context context, ArrayList<Expense> expenses) {
        super(context, 0, expenses);
        this.context = context;
        this.expenseList = expenses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the expense item for this position
        Expense expense = expenseList.get(position);

        // Check if an existing view is being reused, otherwise inflate a new view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);
        }

        // Lookup view for data population
        TextView amountTextView = convertView.findViewById(R.id.textAmount);
        TextView descriptionTextView = convertView.findViewById(R.id.textDescription);

        // Populate the data into the template view using the expense object
        amountTextView.setText("Amount: $" + expense.getAmount());
        descriptionTextView.setText("Description: " + expense.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }
}
