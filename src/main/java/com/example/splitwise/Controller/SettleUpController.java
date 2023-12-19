package com.example.splitwise.Controller;

import com.example.splitwise.Dtos.ResponseStatus;
import com.example.splitwise.Dtos.SettleUpRequestDto;
import com.example.splitwise.Dtos.SettleUpResponseDto;
import com.example.splitwise.Service.SettleUpService;
import com.example.splitwise.Service.Transaction;
import com.example.splitwise.exceptions.GroupIdInvalidException;
import com.example.splitwise.exceptions.MemberIdInvalidException;
import com.example.splitwise.exceptions.UserIdInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SettleUpController {
    SettleUpService settleUpService;
    @Autowired
    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    public SettleUpResponseDto settleUp(SettleUpRequestDto settleUpRequestDto) {
        SettleUpResponseDto settleUpResponseDto = new SettleUpResponseDto();
        List<Transaction> transactions = new ArrayList<>();
        try{
            if(settleUpRequestDto.getGroupId()!=null)
                transactions = settleUpService.settleUpGroup(settleUpRequestDto.getUserId(), settleUpRequestDto.getGroupId());
            else
                transactions = settleUpService.settleUpUser(settleUpRequestDto.getUserId());
        }catch (UserIdInvalidException | GroupIdInvalidException | MemberIdInvalidException e){
            settleUpResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            settleUpResponseDto.setMesssage(e.getMessage());
            return settleUpResponseDto;
        }
        settleUpResponseDto.setTransactions(transactions);
        settleUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        settleUpResponseDto.setMesssage("Settle up request processed successfully");
        return settleUpResponseDto;
    }
}
