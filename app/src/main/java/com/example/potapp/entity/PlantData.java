package com.example.potapp.entity;

public class PlantData {

    private int plantData_id;
    private String plant_type;
    private String light_start_time;
    private String light_end_time;

    public void setPlant_type(String plant_type) {
        this.plant_type = plant_type;
    }

    public int getPlantData_id() {
        return plantData_id;
    }

    public String getPlant_type() {
        return plant_type;
    }

    public String getLight_start_time() {
        return light_start_time;
    }

    public String getLight_end_time() {
        return light_end_time;
    }
}
