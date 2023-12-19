package com.example.splitwise.commands;

import com.example.splitwise.Controller.SettleUpController;
import com.example.splitwise.Dtos.ResponseStatus;
import com.example.splitwise.Dtos.SettleUpRequestDto;
import com.example.splitwise.Dtos.SettleUpResponseDto;
import com.example.splitwise.Service.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpCommand implements Command{
    SettleUpController settleUpController;
    @Autowired
    public SettleUpCommand(SettleUpController settleUpController) {
        this.settleUpController = settleUpController;
    }

    @Override
    public boolean matches(String input) {
        String[] inputSplit = input.split(" ");
        return inputSplit.length>=2 && inputSplit[1].equals(CommandKeywords.settleUp);
    }

    @Override
    public void execute(String input) {
        String[] inputSplit = input.split(" ");
        SettleUpRequestDto settleUpRequestDto = new SettleUpRequestDto();
        try {
            settleUpRequestDto.setUserId(Long.parseLong(inputSplit[0]));
            if(inputSplit.length==3){
                settleUpRequestDto.setGroupId(Long.parseLong(inputSplit[2]));
            }
        } catch (NumberFormatException e) {
            System.out.println("User Id invalid.");
            return;
        }
        SettleUpResponseDto settleUpResponseDto = settleUpController.settleUp(settleUpRequestDto);
        System.out.println(settleUpResponseDto.getMesssage());
        if(settleUpResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
            if(settleUpResponseDto.getTransactions().isEmpty()) {
                System.out.println("No transactions available");
                return;
            }
            for(Transaction transaction:settleUpResponseDto.getTransactions()){
                System.out.println(transaction.getFromUserId()+" has to pay "+transaction.getToUserId()+" amount: "+transaction.getAmount());
            }
        }
    }
}
