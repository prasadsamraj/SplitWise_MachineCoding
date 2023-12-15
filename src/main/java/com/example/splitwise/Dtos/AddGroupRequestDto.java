package com.example.splitwise.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGroupRequestDto {
    private Long UserId;
    private String groupName;
}
