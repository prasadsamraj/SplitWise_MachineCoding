package com.example.splitwise.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyTotalResponseDto {
    private String message;
    private ResponseStatus responseStatus;
    private Long myTotal;
}
