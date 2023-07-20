package com.example.potapp.command;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class PotInvoker {
    private List<Command> commandList;

    public PotInvoker() {
        commandList = new ArrayList<>();
    }

    public void addCommand(Command command) {
        commandList.add(command);
    }

    public void run(String cmdName, int potPosition, Context context) {
        for(Command c : commandList){
            if(c.getCommandName().equals(cmdName)){
                c.execute(potPosition, context);
                break;
            }
        }
    }
}
