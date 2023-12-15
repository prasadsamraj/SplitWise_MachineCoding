package com.example.splitwise.exceptions;

public class UserIdInvalidException extends Exception{
    public UserIdInvalidException() {
        super("User Id is invalid");
    }
}
