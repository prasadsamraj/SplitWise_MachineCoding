package com.example.splitwise.Controller;

import com.example.splitwise.Dtos.RegisterUserRequestDto;
import com.example.splitwise.Dtos.RegisterUserResponseDto;
import com.example.splitwise.Dtos.ResponseStatus;
import com.example.splitwise.Service.UserService;
import com.example.splitwise.exceptions.UserAlreadyExistsException;
import com.example.splitwise.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto){
        User user;
        RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
        try{
            user = userService.registerUser
                    (registerUserRequestDto.getUserName(),
                            registerUserRequestDto.getPassword(),
                            registerUserRequestDto.getEmail());
        }catch(UserAlreadyExistsException e){
            registerUserResponseDto.setMessage(e.getMessage());
            registerUserResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            return registerUserResponseDto;
        }
        registerUserResponseDto.setUserId(user.getId());
        registerUserResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return registerUserResponseDto;
    }
}
