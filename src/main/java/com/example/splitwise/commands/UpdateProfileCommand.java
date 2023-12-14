package com.example.splitwise.commands;

import com.example.splitwise.Controller.UserController;
import com.example.splitwise.Dtos.UpdateProfileRequestDto;
import com.example.splitwise.Dtos.UpdateProfileResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProfileCommand implements Command{
    UserController userController;
    @Autowired
    public UpdateProfileCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        String[] splitInput = input.split(" ");
        return splitInput.length==3 && splitInput[1].equals(CommandKeywords.updateProfile);
    }

    @Override
    public void execute(String input) {
        String[] splitInput = input.split(" ");
        UpdateProfileRequestDto request = new UpdateProfileRequestDto();
        request.setUserId(Long.parseLong(splitInput[0]));
        request.setPassword(splitInput[2]);
        UpdateProfileResponseDto response = userController.updateProfile(request);
        System.out.println(response.getMessage());
    }
}
