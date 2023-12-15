package com.example.splitwise.Controller;

import com.example.splitwise.Dtos.*;
import com.example.splitwise.Service.GroupService;
import com.example.splitwise.exceptions.*;
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
        } catch (UserIdInvalidException | DuplicateGroupException e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
        responseDto.setGroupId(group.getId());
        responseDto.setMessage("Group created successfully. Your Group Id is "+group.getId());
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }
    public AddMemberResponseDto addMember(AddMemberRequestDto requestDto){
        AddMemberResponseDto responseDto = new AddMemberResponseDto();
        try{
            groupService.addMember(requestDto.getAdminId(), requestDto.getGroupId(), requestDto.getMemberId());
        }catch (UserIdInvalidException | MemberAlreadyInGroupException | AdminGroupMismatchException |
                GroupIdInvalidException | MemberIdInvalidException e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
        responseDto.setMessage("Member added Successfully");
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }

    public ListGroupsResponseDto listGroups(ListGroupsRequestDto requestDto) {
        ListGroupsResponseDto responseDto = new ListGroupsResponseDto();
        try{
           groupService.listGroups(requestDto.getUserId());
        }catch (UserIdInvalidException e){
            responseDto.setMessage(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
        responseDto.setMessage("Execution Success");
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }
}
