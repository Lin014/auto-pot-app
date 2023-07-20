package com.example.potapp.state;

public class WaterLevelContext {

    State state;

    public WaterLevelContext() {

        state = new AbundanceState();
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }

    public String Handle(String waterLevelState) {
        switch (waterLevelState) {
            case "enough":
                setState(new AbundanceState());
                break;
            case "notEnough":
                setState(new ShortageState());
                break;
        }

        return state.Handle();
    }

}
