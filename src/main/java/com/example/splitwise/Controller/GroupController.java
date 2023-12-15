package com.example.splitwise.Controller;

import com.example.splitwise.Dtos.AddGroupRequestDto;
import com.example.splitwise.Dtos.AddGroupResponseDto;
import com.example.splitwise.Dtos.ResponseStatus;
import com.example.splitwise.Service.GroupService;
import com.example.splitwise.exceptions.UserIdInvalidException;
import com.example.splitwise.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {
    GroupService groupService;
    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    public AddGroupResponseDto addGroup(AddGroupRequestDto requestDto){
        Group group;
        AddGroupResponseDto responseDto = new AddGroupResponseDto();
        try {
            group = groupService.addGroup(requestDto.getUserId(), requestDto.getGroupName());
        } catch (UserIdInvalidException e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setMessage("Group not created (Reason: User Id invalid)");
            return responseDto;
        }
        responseDto.setGroupId(group.getId());
        responseDto.setMessage("Group created successfully. Your Group Id is "+group.getId());
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }
}
