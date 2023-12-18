package com.example.splitwise.commands;

import com.example.splitwise.Controller.ExpenseController;
import com.example.splitwise.Dtos.AddExpenseRequestDto;
import com.example.splitwise.Dtos.AddExpenseResponseDto;
import com.example.splitwise.exceptions.ExpenseInvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCommand implements Command{
    ExpenseController expenseController;
    @Autowired
    public ExpenseCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        String[] split =input.split(" ");
        return split[1].equals(CommandKeywords.expense);
    }

    @Override
    public void execute(String input) {
        AddExpenseRequestDto addExpenseRequestDto;
        try {
            addExpenseRequestDto = ExpenseInputToDtoConverter.parseExpenseInput(input);
        } catch (ExpenseInvalidFormatException e) {
            System.out.println(e.getMessage());
            return;
        }
        AddExpenseResponseDto addExpenseResponseDto = expenseController.addExpense(addExpenseRequestDto);
        System.out.println(addExpenseResponseDto.getMessage());
    }
}
