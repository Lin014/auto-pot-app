package com.example.potapp.chain;

import android.content.Context;

import com.example.potapp.command.PotInvoker;
import com.example.potapp.command.PotReceiver;

public abstract class PlantHandler {

    protected PlantHandler next;
    PotReceiver potReceiver;
    PotInvoker potInvoker;

    public PlantHandler(PlantHandler next) {
        potReceiver = new PotReceiver();
        potInvoker = new PotInvoker();
        this.next = next;
    }

    public void toNext(int potPosition, Context context) {
        if (next != null) {
            next.handleRequest(potPosition, context);
        }
    }

    public abstract void handleRequest(int potPosition, Context context);
}
