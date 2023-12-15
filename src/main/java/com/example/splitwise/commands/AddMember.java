package com.example.splitwise.commands;

import com.example.splitwise.Controller.GroupController;
import com.example.splitwise.Dtos.AddMemberRequestDto;
import com.example.splitwise.Dtos.AddMemberResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddMember implements Command {
    GroupController groupController;
    @Autowired
    public AddMember(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        String[] inputSplit = input.split(" ");
        return inputSplit.length==4 && inputSplit[1].equals(CommandKeywords.addMember);
    }

    @Override
    public void execute(String input) {
        String[] inputSplit = input.split(" ");
        AddMemberRequestDto requestDto = new AddMemberRequestDto();
        requestDto.setAdminId(Long.parseLong(inputSplit[0]));
        requestDto.setGroupId(Long.parseLong(inputSplit[2]));
        requestDto.setMemberId(Long.parseLong(inputSplit[3]));
        AddMemberResponseDto responseDto = groupController.addMember(requestDto);
        System.out.println(responseDto.getMessage());
    }
}
