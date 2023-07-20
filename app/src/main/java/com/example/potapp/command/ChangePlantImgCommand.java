package com.example.potapp.command;

import android.content.Context;

public class ChangePlantImgCommand implements Command {

    PotReceiver potReceiver;
    String plantImg;

    public ChangePlantImgCommand(PotReceiver potReceiver) {
        this.plantImg = plantImg;
        this.potReceiver = potReceiver;
    }

    @Override
    public void execute(int potPosition, Context context) {
        potReceiver.changePlantImg(potPosition, context);
    }

    @Override
    public String getCommandName() {
        return Constant.ChangePlantImg;
    }
}
