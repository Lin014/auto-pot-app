package com.example.potapp.entity;

public class Data {

    final private int data_id;
    private String data_time;
    private String data_water_motor_state;
    private String data_water_level_state;
    private int data_soil_moisture;
    private int data_water_level;
    private String data_light_state;
    private int data_light_lux;
    private Pot pot;

    public Data(int data_id, String data_time, String data_water_motor_state, String data_water_level_state, int data_soil_moisture, int data_water_level, String data_light_state, int data_light_lux, Pot pot) {
        this.data_id = data_id;
        this.data_time = data_time;
        this.data_water_motor_state = data_water_motor_state;
        this.data_water_level_state = data_water_level_state;
        this.data_soil_moisture = data_soil_moisture;
        this.data_water_level = data_water_level;
        this.data_light_state = data_light_state;
        this.data_light_lux = data_light_lux;
        this.pot = pot;
    }

    public void setData_water_motor_state(String data_water_motor_state) {
        this.data_water_motor_state = data_water_motor_state;
    }

    public void setData_water_level_state(String data_water_level_state) {
        this.data_water_level_state = data_water_level_state;
    }

    public void setData_soil_moisture(int data_soil_moisture) {
        this.data_soil_moisture = data_soil_moisture;
    }

    public void setData_water_level(int data_water_level) {
        this.data_water_level = data_water_level;
    }

    public void setData_light_state(String data_light_state) {
        this.data_light_state = data_light_state;
    }

    public void setData_light_lux(int data_light_lux) {
        this.data_light_lux = data_light_lux;
    }

    public void setPot(Pot pot) {
        this.pot = pot;
    }

    public int getData_id() {
        return data_id;
    }

    public String getData_time() {
        return data_time;
    }

    public String getData_water_motor_state() {
        return data_water_motor_state;
    }

    public String getData_water_level_state() {
        return data_water_level_state;
    }

    public int getData_soil_moisture() {
        return data_soil_moisture;
    }

    public int getData_water_level() {
        return data_water_level;
    }

    public String getData_light_state() {
        return data_light_state;
    }

    public int getData_light_lux() {
        return data_light_lux;
    }

    public Pot getPot() {
        return pot;
    }
}
