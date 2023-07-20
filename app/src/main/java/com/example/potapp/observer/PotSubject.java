package com.example.potapp.observer;

import com.example.potapp.entity.Pot;

import java.util.ArrayList;

public class PotSubject implements Subject {

    private static PotSubject instance;

    private ArrayList<Observer> observers;
    private Pot pot;

    private PotSubject() {
        observers = new ArrayList<>();
    }

    public static PotSubject getInstance() {
        if (instance == null) {
            instance = new PotSubject();
            System.out.println("PotSubject created");

        }
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update(instance);
        }
    }

    public void setPot(Pot pot) {
        this.pot = pot;
        notifyObserver();
    }

    public Pot getPot() {
        return pot;
    }
}
