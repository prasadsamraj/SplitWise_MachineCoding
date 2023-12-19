package com.example.splitwise.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettleUpRequestDto {
    private Long userId;
    private Long groupId;
}
