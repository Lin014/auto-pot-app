package com.example.potapp.command;

import android.content.Context;

public class ChangePotNameCommand implements Command {
    PotReceiver potReceiver;
    String potName;

    public ChangePotNameCommand(PotReceiver potReceiver) {
        this.potName = potName;
        this.potReceiver = potReceiver;
    }

    @Override
    public void execute(int potPosition, Context context) {
        potReceiver.changePotName(potPosition, context);
    }

    @Override
    public String getCommandName() {
        return Constant.ChangePotName;
    }
}
