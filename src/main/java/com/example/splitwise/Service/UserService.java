package com.example.splitwise.Service;

import com.example.splitwise.exceptions.UserAlreadyExistsException;
import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String userName, String password, String email) throws UserAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) throw new UserAlreadyExistsException();
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setEmail(email);
        return userRepository.save(user);
    }
}
