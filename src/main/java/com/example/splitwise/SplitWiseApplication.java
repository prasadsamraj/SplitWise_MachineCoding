package com.example.splitwise;
import com.example.splitwise.Controller.UserController;
import com.example.splitwise.commands.CommandExecutor;
import com.example.splitwise.commands.RegisterCommand;
import com.example.splitwise.commands.UpdateProfileCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;


@SpringBootApplication
public class SplitWiseApplication implements CommandLineRunner {
    @Autowired
    CommandExecutor commandExecutor;
    @Autowired
    UserController userController;
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        SpringApplication.run(SplitWiseApplication.class, args);

    }

    @Override
    public void run(String... args) {
        commandExecutor.addCommand(new RegisterCommand(userController));
        commandExecutor.addCommand(new UpdateProfileCommand(userController));
        while(true){
            System.out.println("Please enter the command:");
            String input = scanner.nextLine();
            commandExecutor.execute(input);
        }
    }
}
