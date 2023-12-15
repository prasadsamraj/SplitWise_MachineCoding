package com.example.splitwise.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberRequestDto {
    private Long adminId;
    private Long groupId;
    private Long memberId;
}
