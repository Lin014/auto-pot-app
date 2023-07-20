package com.example.potapp.state_water_motor;

public class CloseState implements WaterMotorState {

    @Override
    public String Handle() {
        return "關閉";
    }
}
