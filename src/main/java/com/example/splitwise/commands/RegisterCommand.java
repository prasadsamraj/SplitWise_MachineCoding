package com.example.splitwise.commands;

import com.example.splitwise.Controller.UserController;
import com.example.splitwise.Dtos.RegisterUserRequestDto;
import com.example.splitwise.Dtos.RegisterUserResponseDto;
import com.example.splitwise.Dtos.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterCommand implements Command{
    UserController userController;
    @Autowired
    public RegisterCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        String[] inputSplit = input.split(" ");
        return inputSplit[0].equals(CommandKeywords.register) && inputSplit.length==4;
    }

    @Override
    public void execute(String input) {
        String[] inputSplit = input.split(" ");
        RegisterUserRequestDto request = new RegisterUserRequestDto();
        request.setUserName(inputSplit[1]);
        request.setEmail(inputSplit[2]);
        request.setPassword(inputSplit[3]);
        RegisterUserResponseDto response= userController.registerUser(request);
        if(response.getResponseStatus().equals(ResponseStatus.SUCCESS)){
            System.out.println("User registration successful");
            System.out.println("Your user id is: "+response.getUserId());
        }else{
            System.out.println("User already exists in the system");
        }
    }
}
