package com.example.splitwise.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserResponseDto {
    private long UserId;
    private String message;
    private ResponseStatus responseStatus;
}
