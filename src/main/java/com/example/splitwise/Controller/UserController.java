package com.example.splitwise.Controller;

import com.example.splitwise.Dtos.*;
import com.example.splitwise.Service.UserService;
import com.example.splitwise.exceptions.UserAlreadyExistsException;
import com.example.splitwise.exceptions.UserIdInvalidException;
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
            registerUserResponseDto.setMessage("User already exists in the system");
            registerUserResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            return registerUserResponseDto;
        }
        registerUserResponseDto.setUserId(user.getId());
        registerUserResponseDto.setMessage("User registered successfully with Id: "+user.getId());
        registerUserResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return registerUserResponseDto;
    }
    public UpdateProfileResponseDto updateProfile(UpdateProfileRequestDto request){
        UpdateProfileResponseDto response = new UpdateProfileResponseDto();
        try{
            userService.updateProfile(request.getUserId(), request.getPassword());
        }catch (UserIdInvalidException e){
            response.setMessage("User id is invalid");
            response.setResponseStatus(ResponseStatus.FAILURE);
            return response;
        }
        response.setMessage("Profile updated successfully");
        response.setResponseStatus(ResponseStatus.SUCCESS);
        return response;
    }
}
