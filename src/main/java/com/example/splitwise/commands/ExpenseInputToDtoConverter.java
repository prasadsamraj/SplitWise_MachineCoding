package com.example.splitwise.commands;

import com.example.splitwise.Dtos.AddExpenseRequestDto;
import com.example.splitwise.exceptions.ExpenseInvalidFormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpenseInputToDtoConverter {
    public static AddExpenseRequestDto parseExpenseInput(String input) throws ExpenseInvalidFormatException {
        AddExpenseRequestDto addExpenseRequestDto = new AddExpenseRequestDto();
        try {
            String[] splitInput = input.split(" ");
            if(splitInput[0].charAt(0)!='u') throw new ExpenseInvalidFormatException();
            addExpenseRequestDto.setCreatedByUserId(Long.parseLong(splitInput[0].substring(1)));
            List<Long> userIds = new ArrayList<>();
            int index = 2;
            if(splitInput[index].charAt(0)=='g') {
                addExpenseRequestDto.setGroupId(Long.parseLong(splitInput[index].substring(1)));
                index++;
            }
            else {
                userIds.add(addExpenseRequestDto.getCreatedByUserId());
                while (splitInput[index].charAt(0) == 'u') {
                    userIds.add(Long.parseLong(splitInput[index].substring(1)));
                    index++;
                }
            }
            addExpenseRequestDto.setUserIds(userIds);
            addExpenseRequestDto.setPayType(PayType.valueOf(splitInput[index]));
            if(addExpenseRequestDto.getPayType().equals(PayType.MultiPay) && addExpenseRequestDto.getGroupId()!=null)
                throw new ExpenseInvalidFormatException();
            index++;
            List<Long> paidAmounts = new ArrayList<>();
            if(addExpenseRequestDto.getPayType().equals(PayType.iPay)){
                paidAmounts.add(Long.parseLong(splitInput[index]));
                index++;
            }else{
                for(int i=0; i<userIds.size(); i++){
                    paidAmounts.add(Long.parseLong(splitInput[index]));
                    index++;
                }
            }
            addExpenseRequestDto.setPaidAmounts(paidAmounts);
            addExpenseRequestDto.setTotalAmount(paidAmounts.stream().reduce(Long::sum).get());
            addExpenseRequestDto.setSplitType(SplitType.valueOf(splitInput[index]));
            index++;
            if(addExpenseRequestDto.getGroupId()!=null && !addExpenseRequestDto.getSplitType().equals(SplitType.Equal))
                throw new ExpenseInvalidFormatException();
            List<Long> splitAmounts = new ArrayList<>();
            if(!addExpenseRequestDto.getSplitType().equals(SplitType.Equal)){
                for(int i=0; i<userIds.size(); i++){
                    splitAmounts.add(Long.parseLong(splitInput[index]));
                    index++;
                }
            }
            if(addExpenseRequestDto.getSplitType().equals(SplitType.Percent)){
                if(splitAmounts.stream().reduce(Long::sum).get()!=100)
                    throw new ExpenseInvalidFormatException();
            }else if(addExpenseRequestDto.getSplitType().equals(SplitType.Exact)){
                if(!splitAmounts.stream().reduce(Long::sum).get().equals(addExpenseRequestDto.getTotalAmount()))
                    throw new ExpenseInvalidFormatException();
            }
            addExpenseRequestDto.setSplitAmounts(splitAmounts);
            if(!splitInput[index].equals("Desc")) throw new ExpenseInvalidFormatException();
            index++;
            addExpenseRequestDto.setDesc(String.join(" ", Arrays.copyOfRange(splitInput, index, splitInput.length)));
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException | ExpenseInvalidFormatException e) {
            throw new ExpenseInvalidFormatException();
        }
        return addExpenseRequestDto;
    }
}
