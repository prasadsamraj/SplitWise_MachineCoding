package com.example.splitwise.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGroupResponseDto {
    private Long groupId;
    private String message;
    private ResponseStatus responseStatus;
}
