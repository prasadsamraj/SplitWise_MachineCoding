package com.example.splitwise.Controller;

import com.example.splitwise.Dtos.AddExpenseRequestDto;
import com.example.splitwise.Dtos.AddExpenseResponseDto;
import com.example.splitwise.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ExpenseController {
    ExpenseService expenseService;
    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public AddExpenseResponseDto addExpense(AddExpenseRequestDto requestDto){
        return null;
    }
}
