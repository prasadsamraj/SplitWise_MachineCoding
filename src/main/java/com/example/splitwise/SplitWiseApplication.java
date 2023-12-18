package com.example.splitwise;
import com.example.splitwise.Controller.ExpenseController;
import com.example.splitwise.Controller.GroupController;
import com.example.splitwise.Controller.UserController;
import com.example.splitwise.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

@EnableJpaAuditing
@SpringBootApplication
public class SplitWiseApplication implements CommandLineRunner {
    @Autowired
    CommandExecutor commandExecutor;
    @Autowired
    UserController userController;
    @Autowired
    GroupController groupController;
    @Autowired
    ExpenseController expenseController;
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        SpringApplication.run(SplitWiseApplication.class, args);
    }

    @Override
    public void run(String... args) {
        commandExecutor.addCommand(new RegisterCommand(userController));
        commandExecutor.addCommand(new UpdateProfileCommand(userController));
        commandExecutor.addCommand(new AddGroupCommand(groupController));
        commandExecutor.addCommand(new AddMemberCommand(groupController));
        commandExecutor.addCommand(new ListGroups(groupController));
        commandExecutor.addCommand(new ExpenseCommand(expenseController));
        commandExecutor.addCommand(new MyTotalCommand(expenseController));
        while(true){
            System.out.println("Please enter the command:");
            String input = scanner.nextLine();
            if(input.equals("exit")) break;
            commandExecutor.execute(input);
        }
    }
}
