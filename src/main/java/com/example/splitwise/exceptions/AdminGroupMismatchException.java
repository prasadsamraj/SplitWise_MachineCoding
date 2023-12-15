package com.example.splitwise.exceptions;

public class AdminGroupMismatchException extends Exception {
    public AdminGroupMismatchException() {
        super("Admin and Group Id mismatching.");
    }
}
