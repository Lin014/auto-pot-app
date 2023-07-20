package com.example.potapp.command;

import android.content.Context;

public interface Command {
    public void execute(int potPosition, Context context);
    String getCommandName();
}
