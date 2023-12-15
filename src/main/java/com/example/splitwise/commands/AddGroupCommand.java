package com.example.splitwise.commands;

import com.example.splitwise.Controller.GroupController;
import com.example.splitwise.Dtos.AddGroupRequestDto;
import com.example.splitwise.Dtos.AddGroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddGroupCommand implements Command{
    GroupController groupController;
    @Autowired
    public AddGroupCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        String[] inputSplit = input.split(" ");
        return inputSplit.length==3 && inputSplit[1].equals(CommandKeywords.addgroup);
    }

    @Override
    public void execute(String input) {
        String[] inputSplit = input.split(" ");
        AddGroupRequestDto requestDto = new AddGroupRequestDto();
        requestDto.setUserId(Long.parseLong(inputSplit[0]));
        requestDto.setGroupName(inputSplit[2]);
        AddGroupResponseDto responseDto = groupController.addGroup(requestDto);
        System.out.println(responseDto.getMessage());
    }
}
