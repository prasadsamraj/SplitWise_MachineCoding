package com.example.splitwise.Service;

import com.example.splitwise.Repository.GroupRepository;
import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.exceptions.*;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    UserRepository userRepository;
    GroupRepository groupRepository;
    @Autowired
    public GroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }
    public Group addGroup(Long userId, String groupName) throws UserIdInvalidException, DuplicateGroupException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new UserIdInvalidException();
        User user = optionalUser.get();
        List<Group> userGroups = groupRepository.findAllByAdmin(user);
        for(Group group:userGroups){
            if(group.getName().equals(groupName)) throw new DuplicateGroupException();
        }
        Group group = new Group();
        group.setName(groupName);
        group.setAdmin(user);
        List<User> users = new ArrayList<>();
        users.add(user);
        group.setUsers(users);
        return groupRepository.save(group);
    }

    public void addMember(Long adminId, Long groupId, Long memberId) throws UserIdInvalidException, GroupIdInvalidException, AdminGroupMismatchException, MemberIdInvalidException, MemberAlreadyInGroupException {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if(optionalGroup.isEmpty()) throw new GroupIdInvalidException();
        Group group = optionalGroup.get();
        if(!adminId.equals(group.getAdmin().getId())) throw new AdminGroupMismatchException();
        Optional<User> optionalMember = userRepository.findById(memberId);
        if(optionalMember.isEmpty()) throw new MemberIdInvalidException();
        User member = optionalMember.get();
        List<User> members = group.getUsers();
        for(User user:members){
            if(user.getId().equals(member.getId())) throw new MemberAlreadyInGroupException();
        }
        members.add(member);
        groupRepository.save(group);
    }

    public void listGroups(Long userId) throws UserIdInvalidException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new UserIdInvalidException();
        User user = optionalUser.get();
        List<Group> groups = groupRepository.findAll();
        boolean flag = false;
        for(Group group:groups){
            List<User> users = group.getUsers();
            for(User member:users){
                if(member.getId().equals(user.getId())){
                    flag = true;
                    System.out.println("Group Id: "+group.getId()+ " | Group Name: " + group.getName());
                    break;
                }
            }
        }
        if(!flag) System.out.println("This user is not a part of any groups");
    }
}
