package com.example.splitwise.commands;

import com.example.splitwise.Controller.GroupController;
import com.example.splitwise.Dtos.ListGroupsRequestDto;
import com.example.splitwise.Dtos.ListGroupsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ListGroups implements Command{
    GroupController groupController;
    @Autowired
    public ListGroups(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        String[] inputSplit = input.split(" ");
        return inputSplit.length==2 && inputSplit[1].equals(CommandKeywords.listGroups);
    }

    @Override
    public void execute(String input) {
        String[] inputSplit = input.split(" ");
        ListGroupsRequestDto requestDto = new ListGroupsRequestDto();
        requestDto.setUserId(Long.parseLong(inputSplit[0]));
        ListGroupsResponseDto responseDto = groupController.listGroups(requestDto);
        System.out.println(responseDto.getMessage());
    }
}
