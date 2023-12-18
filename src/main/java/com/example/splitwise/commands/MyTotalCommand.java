package com.example.splitwise.commands;

import com.example.splitwise.Controller.ExpenseController;
import com.example.splitwise.Dtos.MyTotalRequestDto;
import com.example.splitwise.Dtos.MyTotalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyTotalCommand implements Command{
    ExpenseController expenseController;
    @Autowired
    public MyTotalCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        String[] inputSplit = input.split(" ");
        return inputSplit.length==2 && inputSplit[1].equals(CommandKeywords.myTotal);
    }

    @Override
    public void execute(String input) {
        String[] inputSplit = input.split(" ");
        Long userId;
        try {
            userId = Long.parseLong(inputSplit[0]);
        } catch (NumberFormatException e) {
            System.out.println("User Id should be a number");
            return;
        }
        MyTotalRequestDto myTotalRequestDto = new MyTotalRequestDto();
        myTotalRequestDto.setUserId(userId);
        MyTotalResponseDto myTotalResponseDto = expenseController.myTotal(myTotalRequestDto);
        System.out.println(myTotalResponseDto.getMessage());
    }
}
