package com.example.splitwise.Controller;

import com.example.splitwise.Dtos.AddExpenseRequestDto;
import com.example.splitwise.Dtos.AddExpenseResponseDto;
import com.example.splitwise.Dtos.ResponseStatus;
import com.example.splitwise.Service.ExpenseService;
import com.example.splitwise.exceptions.GroupIdInvalidException;
import com.example.splitwise.exceptions.MemberIdInvalidException;
import com.example.splitwise.exceptions.UserIdInvalidException;
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
        AddExpenseResponseDto addExpenseResponseDto = new AddExpenseResponseDto();
        try {
            if (requestDto.getGroupId() == null) {
                expenseService.addExpenseUsers(requestDto.getCreatedByUserId(),
                        requestDto.getUserIds(),
                        requestDto.getTotalAmount(),
                        requestDto.getDesc(),
                        requestDto.getPayType(),
                        requestDto.getPaidAmounts(),
                        requestDto.getSplitType(),
                        requestDto.getSplitAmounts());
            } else {
                expenseService.addExpenseGroup(requestDto.getCreatedByUserId(),
                        requestDto.getGroupId(),
                        requestDto.getTotalAmount(),
                        requestDto.getDesc(),
                        requestDto.getPayType(),
                        requestDto.getPaidAmounts(),
                        requestDto.getSplitType(),
                        requestDto.getSplitAmounts());
            }
        } catch (UserIdInvalidException | GroupIdInvalidException | MemberIdInvalidException e) {
            addExpenseResponseDto.setMessage(e.getMessage());
            addExpenseResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            return addExpenseResponseDto;
        }
        addExpenseResponseDto.setMessage("Expense Successfully Added");
        addExpenseResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return addExpenseResponseDto;
    }
}
