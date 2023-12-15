package com.example.splitwise.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super("User with same email already exists in the system.");
    }
}
