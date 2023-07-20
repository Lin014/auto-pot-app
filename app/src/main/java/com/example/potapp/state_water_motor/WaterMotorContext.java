package com.example.potapp.state_water_motor;

public class WaterMotorContext {

    WaterMotorState state;

    public WaterMotorContext() {

        state = new CloseState();
    }

    public void setState(WaterMotorState state){
        this.state = state;
    }

    public WaterMotorState getState(){
        return state;
    }

    public String Handle(String waterMotorState) {
        switch (waterMotorState) {
            case "open":
                setState(new OpenState());
                break;
            case "close":
                setState(new CloseState());
                break;
        }

        return state.Handle();
    }
}
