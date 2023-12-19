package com.example.splitwise.strategy;

import com.example.splitwise.Service.Transaction;
import com.example.splitwise.models.ExpenseUser;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settleUp(List<ExpenseUser> expenseUsers);
}
