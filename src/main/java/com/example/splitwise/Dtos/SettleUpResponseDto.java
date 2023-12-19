package com.example.splitwise.Dtos;

import com.example.splitwise.Service.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettleUpResponseDto {
    private String messsage;
    private ResponseStatus responseStatus;
    private List<Transaction> transactions;
}
