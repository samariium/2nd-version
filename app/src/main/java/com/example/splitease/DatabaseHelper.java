package com.example.splitease;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database name and version constants
    private static final String DATABASE_NAME = "splitwise.db";
    private static final int DATABASE_VERSION = 2; // Incremented version for schema updates

    // Constants for the expenses table
    public static final String TABLE_EXPENSES = "expenses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_USERNAME = "username"; // Column for expenses table

    // Constants for the users table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_PASSWORD = "password";

    // Constructor to initialize the database helper with the context
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create expenses table with username to associate expenses with users
        String createExpensesTable = "CREATE TABLE " + TABLE_EXPENSES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " REAL, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_USERNAME + " TEXT)"; // Add username column
        db.execSQL(createExpensesTable);

        // Create users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " + // Username is the primary key
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db); // Recreate tables
    }

    // Updated method to add an expense to the database
    // Updated method to add an expense to the database
    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_DESCRIPTION, expense.getDescription());
        values.put(COLUMN_USERNAME, expense.getUsername()); // Store associated username

        try {
            db.insert(TABLE_EXPENSES, null, values);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            db.close(); // Always close the database after use
        }
    }


    // Method to retrieve all expenses from the database for a specific user
    public ArrayList<Expense> getAllExpenses(String username) {
        ArrayList<Expense> expenseList = new ArrayList<>(); // List to hold expenses
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query to get all rows from the expenses table for a specific user
            cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSES + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT));
                    @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    expenseList.add(new Expense(id, amount, description, username)); // Add the expense to the list, including the username
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor after use
            }
            db.close(); // Always close the database after use
        }
        return expenseList; // Return the list of expenses
    }

    // Method to add a user to the database
    public void addUser(String username, String password) {
        // Check if the username already exists
        if (isUsernameExists(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username); // Store the username
        values.put(COLUMN_PASSWORD, password); // Store the password

        try {
            db.insert(TABLE_USERS, null, values);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            db.close(); // Always close the database after use
        }
    }

    // Method to check if a username already exists in the database
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query to check if the username exists
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});
            return cursor != null && cursor.getCount() > 0; // Return true if the username exists
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return false;
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor after use
            }
            db.close(); // Always close the database after use
        }
    }

    // Method to validate user credentials (username and password)
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query to check if the username and password match
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                    COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
            return cursor != null && cursor.moveToFirst(); // Return true if the user is found
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return false;
        } finally {
            if (cursor != null) {
                cursor.close(); // Close the cursor after use
            }
            db.close(); // Always close the database after use
        }
    }
}
