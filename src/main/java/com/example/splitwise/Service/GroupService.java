package com.example.splitwise.Service;

import com.example.splitwise.Repository.GroupRepository;
import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.exceptions.DuplicateGroupException;
import com.example.splitwise.exceptions.UserIdInvalidException;
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
}
