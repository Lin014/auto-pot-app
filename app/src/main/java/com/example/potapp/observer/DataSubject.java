package com.example.potapp.observer;

import com.example.potapp.entity.Data;

import java.util.ArrayList;

public class DataSubject implements Subject {

    private static DataSubject instance;

    private ArrayList<Observer> observers;
    private Data data;

    private DataSubject() {
        observers = new ArrayList<>();
    }

    public static DataSubject getInstance() {
        if (instance == null) {
            instance = new DataSubject();
            System.out.println("DataSubject created");

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

    public void setData(Data data) {
        this.data = data;
        notifyObserver();
    }

    public Data getData() {
        return data;
    }
}
