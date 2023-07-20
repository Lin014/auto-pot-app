package com.example.potapp.state_light;

public class LightContext {

    LightState state;

    public LightContext() {

        state = new LightCloseState();
    }

    public void setState(LightState state){
        this.state = state;
    }

    public LightState getState(){
        return state;
    }

    public String Handle(String lightState) {
        switch (lightState) {
            case "open":
                setState(new LightOpenState());
                break;
            case "close":
                setState(new LightCloseState());
                break;
        }

        return state.Handle();
    }
}
