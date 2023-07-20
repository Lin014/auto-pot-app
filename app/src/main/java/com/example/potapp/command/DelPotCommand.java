package com.example.potapp.command;

import android.content.Context;

public class DelPotCommand implements Command {
    PotReceiver potReceiver;

    public DelPotCommand(PotReceiver potReceiver) {
        this.potReceiver = potReceiver;
    }

    @Override
    public void execute(int potPosition, Context context) {
        potReceiver.delPot(potPosition, context);
    }

    @Override
    public String getCommandName() {
        return Constant.DeletePot;
    }
}
