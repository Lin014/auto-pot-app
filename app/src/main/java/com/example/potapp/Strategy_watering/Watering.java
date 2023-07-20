package com.example.potapp.Strategy_watering;

import android.widget.TextView;

public class Watering {
    protected WateringModeStrategy wateringModeStrategy;
    private TextView CurWaterMode;
    private TextView showWaterTime;

    public Watering(TextView CurWaterMode,TextView showWaterTime){
        this.CurWaterMode=CurWaterMode;
        this.showWaterTime=showWaterTime;
    }

    public void setWateringModeStrategy(WateringModeStrategy wateringModeStrategy){
        this.wateringModeStrategy=wateringModeStrategy;
    }
    public void changeWateringMode(){
        wateringModeStrategy.ChangeWateringMode(CurWaterMode,showWaterTime);
    }
}
