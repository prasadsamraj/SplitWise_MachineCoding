package com.example.splitwise.exceptions;

public class MemberIdInvalidException extends Exception {
    public MemberIdInvalidException() {
        super("Member id is invalid.");
    }
}
