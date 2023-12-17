package com.example.splitwise.Dtos;

import com.example.splitwise.commands.PayType;
import com.example.splitwise.commands.SplitType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddExpenseRequestDto {
    private List<Long> userIds;
    private Long groupId;
    private Long createdByUserId;
    private Long totalAmount;
    private List<Long> paidAmounts;
    private List<Long> splitAmounts;
    private PayType payType;
    private SplitType splitType;
    private String desc;
}
