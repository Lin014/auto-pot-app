package com.example.potapp.state_water_motor;

public class OpenState implements WaterMotorState {

    @Override
    public String Handle() {
        return "澆水中";
    }
}
