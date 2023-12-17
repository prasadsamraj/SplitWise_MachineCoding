package com.example.splitwise.commands;

import com.example.splitwise.Dtos.AddExpenseRequestDto;
import com.example.splitwise.exceptions.ExpenseInvalidFormatException;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCommand implements Command{

    @Override
    public boolean matches(String input) {
        String[] split =input.split(" ");
        return split[1].equals(CommandKeywords.expense);
    }

    @Override
    public void execute(String input) {
        AddExpenseRequestDto addExpenseRequestDto = new AddExpenseRequestDto();
        try {
            addExpenseRequestDto = ExpenseInputToDtoConverter.parseExpenseInput(input);
        } catch (ExpenseInvalidFormatException e) {
            System.out.println(e.getMessage());
        }

    }
}
