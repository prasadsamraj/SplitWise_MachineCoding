package com.example.splitwise.exceptions;

public class DuplicateGroupException extends Exception {
    public DuplicateGroupException() {
        super("Duplicate group name.");
    }
}
