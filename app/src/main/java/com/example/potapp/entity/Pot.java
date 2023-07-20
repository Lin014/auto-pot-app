package com.example.potapp.entity;

public class Pot {

    private int pot_id;
    private int plant_name_change_state;
    private int plant_img_change_state;
    private String pot_mf_name;
    private String pot_name;
    private User user;
    private String pot_create_time;

    // plant
    private String plant_name;
    private String plant_img;
    private String plant_type;
    // watering
    private String watering_mode;
    private String watering_start_time;
    private String watering_end_time;
    // light
    private String light_mode;
    private String light_start_time;
    private String light_end_time;

    public Pot(String pot_mf_name, String pot_name, User user) {
        this.pot_mf_name = pot_mf_name;
        this.pot_name = pot_name;
        this.user = user;
    }

    public void setPlant_name_change_state(int plant_name_change_state) {
        this.plant_name_change_state = plant_name_change_state;
    }

    public void setPlant_img_change_state(int plant_img_change_state) {
        this.plant_img_change_state = plant_img_change_state;
    }

    public void setPot_mf_name(String pot_mf_name) {
        this.pot_mf_name = pot_mf_name;
    }

    public void setPot_name(String pot_name) {
        this.pot_name = pot_name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public void setPlant_img(String plant_img) {
        this.plant_img = plant_img;
    }

    public void setPlant_type(String plant_type) {
        this.plant_type = plant_type;
    }

    public void setWatering_mode(String watering_mode) {
        this.watering_mode = watering_mode;
    }

    public void setWatering_start_time(String watering_start_time) {
        this.watering_start_time = watering_start_time;
    }

    public void setWatering_end_time(String watering_end_time) {
        this.watering_end_time = watering_end_time;
    }

    public void setLight_mode(String light_mode) {
        this.light_mode = light_mode;
    }

    public void setLight_start_time(String light_start_time) {
        this.light_start_time = light_start_time;
    }

    public void setLight_end_time(String light_end_time) {
        this.light_end_time = light_end_time;
    }

    public int getPot_id() {
        return pot_id;
    }

    public String getPot_mf_name() {
        return pot_mf_name;
    }

    public String getPot_name() {
        return pot_name;
    }

    public User getUser() {
        return user;
    }

    public String getPot_create_time() {
        return pot_create_time;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public String getPlant_img() {
        return plant_img;
    }

    public String getPlant_type() {
        return plant_type;
    }

    public String getWatering_mode() {
        return watering_mode;
    }

    public String getWatering_start_time() {
        return watering_start_time;
    }

    public String getWatering_end_time() {
        return watering_end_time;
    }

    public String getLight_mode() {
        return light_mode;
    }

    public String getLight_start_time() {
        return light_start_time;
    }

    public String getLight_end_time() {
        return light_end_time;
    }

    public int getPlant_name_change_state() {
        return plant_name_change_state;
    }

    public int getPlant_img_change_state() {
        return plant_img_change_state;
    }
}
