package com.example.splitwise.commands;

import com.example.splitwise.Controller.UserController;
import com.example.splitwise.Dtos.RegisterUserRequestDto;
import com.example.splitwise.Dtos.RegisterUserResponseDto;
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
        return inputSplit.length==4 && inputSplit[0].equals(CommandKeywords.register);
    }

    @Override
    public void execute(String input) {
        String[] inputSplit = input.split(" ");
        RegisterUserRequestDto request = new RegisterUserRequestDto();
        request.setUserName(inputSplit[1]);
        request.setEmail(inputSplit[2]);
        request.setPassword(inputSplit[3]);
        RegisterUserResponseDto response= userController.registerUser(request);
        System.out.println(response.getMessage());
    }
}
