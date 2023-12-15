package com.example.splitwise.exceptions;

public class MemberAlreadyInGroupException extends Exception {
    public MemberAlreadyInGroupException() {
        super("Given user is already a member of this group.");
    }
}
