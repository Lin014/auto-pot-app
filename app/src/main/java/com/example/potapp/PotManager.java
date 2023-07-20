package com.example.potapp;

import android.graphics.Bitmap;

import com.example.potapp.entity.Pot;

import java.util.Arrays;
import java.util.List;

public class PotManager {

    private static int position;

    public static void setPosition(int potPosition) {
        position = potPosition;
    }

    public static void removePot(int position) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().removePottedPlant(position);
    }

    public static void setPlantNameChangeState(int state) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setPlantNameChangeState(position, state);
    }

    public static void setPlantImgChangeState(int state) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setPlantImgChangeState(position, state);
    }

    public static void setPotName(String potName) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setPotName(position, potName);
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().notifyItemChanged(position);
    }

    public static void setPlantName(String plantName) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setPlantName(position, plantName);
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().notifyItemChanged(position);
    }

    public static void setPlantImg(Bitmap plantImg) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setPlantImg(position, Arrays.toString(ImageConvert.BitmapToByte(plantImg)));
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().notifyItemChanged(position);
    }

    public static void setPlantType(String plantType) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setPlantType(position, plantType);
    }

    public static void setWateringMode(String wateringMode) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setWateringMode(position, wateringMode);
    }

    public static void setWateringStartTime(String wateringStartTime) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setWateringStartTime(position, wateringStartTime);
    }

    public static void setWateringEndTime(String wateringEndTime) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setWateringEndTime(position, wateringEndTime);
    }

    public static void setLightMode(String ligntMode) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setLightMode(position, ligntMode);
    }

    public static void setLightStartTime(String lightStartTime) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setLightStartTime(position, lightStartTime);
    }

    public static void setLightEndTime(String lightEndTime) {
        PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().setLightEndTime(position, lightEndTime);
    }

    public static Pot getPot() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position);
    }

    public static int getPotId() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getPot_id();
    }

    public static String getPotCreateTime() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getPot_create_time();
    }

    public static String getPotName() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getPot_name();
    }

    public static String getPlantName() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getPlant_name();
    }

    public static String getPlantImg() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getPlant_img();
    }

    public static String getPlantType() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getPlant_type();
    }

    public static String getWateringMode() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getWatering_mode();
    }

    public static String getWateringStartTime() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getWatering_start_time();
    }

    public static String getWateringEndTime() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getWatering_end_time();
    }

    public static String getLightMode() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getLight_mode();
    }

    public static String getLightStartTime() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getLight_start_time();
    }

    public static String getLightEndTime() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getLight_end_time();
    }

    public static List<Pot> getPotList() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList();
    }

    public static int getPlantNameChangeState() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getPlant_name_change_state();
    }

    public static int getPlantImgChangeState() {
        return PotListManager.getInstance(PottedPlantMenu.user).getPlantAdapter().getPotList().get(position).getPlant_img_change_state();
    }

}
