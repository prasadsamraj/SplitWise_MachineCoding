package com.example.splitwise.exceptions;

public class ExpenseInvalidFormatException extends Exception {
    public ExpenseInvalidFormatException() {
        super("Expense input format is invalid.");
    }
}
