package com.example.potapp.Iterator;

public class History_Time implements History{
    //Time data.
    String time;
    public History_Time(String t) {
        this.time = t;
    }
    public Object get_data(){
        return time;
    }
}
