package com.example.potapp.Strategy;

import android.widget.TextView;




public class Light {
    protected LightModeStrategy lightModeStrategy;
    private TextView CurLightMode;
    private TextView showLightTime;


    public Light(TextView CurLightMode, TextView showLightTime){
        this.CurLightMode=CurLightMode;
        this.showLightTime=showLightTime;
    }

    public void setLightModeStrategy(LightModeStrategy lightModeStrategy){
        this.lightModeStrategy=lightModeStrategy;
    }
    public void changeLightMode(){
        lightModeStrategy.ChangeLightMode(CurLightMode,showLightTime);
    }


}
