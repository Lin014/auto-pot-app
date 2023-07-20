package com.example.potapp.state;

public class ShortageState implements State {

    @Override
    public String Handle() {
        return "不足";
    }
}
