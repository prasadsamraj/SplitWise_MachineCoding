package com.example.splitwise.commands;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CommandExecutor {
    private List<Command> commands = new ArrayList<>();
    public void addCommand(Command command){
        commands.add(command);
    }
    public void execute(String input){
        boolean flag = false;
        for(Command command:commands){
            if(command.matches(input)) {
                command.execute(input);
                flag = true;
            }
        }
        if(!flag)
            System.out.println("Invalid command..");
    }
}
