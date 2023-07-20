package com.example.potapp.command;

import android.content.Context;

public class ChangePlantNameCommand implements Command {

    PotReceiver potReceiver;
    String plantName;

    public ChangePlantNameCommand(PotReceiver potReceiver) {
        this.plantName = plantName;
        this.potReceiver = potReceiver;
    }

    @Override
    public void execute(int potPosition, Context context) {
        potReceiver.changePlantName(potPosition, context);
    }

    @Override
    public String getCommandName() {
        return Constant.ChangePlantName;
    }
}
