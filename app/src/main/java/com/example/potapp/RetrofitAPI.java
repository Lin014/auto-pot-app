package com.example.potapp;

import com.example.potapp.entity.Data;
import com.example.potapp.entity.PlantData;
import com.example.potapp.entity.Pot;
import com.example.potapp.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {

    // ---------------------- user ----------------------
    @POST("user/register")
    Call<String> registerUser(@Body User user);

    @POST("user/login")
    Call<User> login(@Body User user);

    @POST("user/changePassword")
    Call<String> changePassword(@Body User user);

    @POST("user/clearUser")
    Call<String> clearUser(@Body User user);

    // ---------------------- pot ----------------------
    @POST("pot/addPot")
    Call<Pot> addPot(@Body Pot pot);

    @POST("pot/delPot")
    Call<String> delPot(@Body Pot pot);

    @GET("pot/getCountPot")
    Call<String> getCountPot();

    @GET("pot/getPot/{id}")
    Call<Pot> getPotById(@Path("id") int pot_id);

    @GET("pot/getPots/{id}")
    Call<List<Pot>> getPotsByUserId(@Path("id") int user_id);

    @POST("pot/changePotName")
    Call<Pot> changePotName(@Body Pot pot);

    @POST("pot/changePlantName")
    Call<Pot> changePlantName(@Body Pot pot);

    @POST("pot/changePlantImg")
    Call<Pot> changePlantImg(@Body Pot pot);

    @POST("pot/changePlantType")
    Call<Pot> changePlantType(@Body Pot pot);

    @POST("pot/changeWatering")
    Call<Pot> changeWatering(@Body Pot pot);

    @POST("pot/changeLight")
    Call<Pot> changeLight(@Body Pot pot);

    @POST("pot/changePlantNameState")
    Call<Pot> changePlantNameState(@Body Pot pot);

    @POST("pot/changePlantImgState")
    Call<Pot> changePlantImgState(@Body Pot pot);

    // ---------------------- data ----------------------
    @GET("data/getLastDataByPotId/{id}")
    Call<Data> getLastDataByPotId(@Path("id") int pot_id);

    @GET("data/isDataNull/{id}")
    Call<String> isDataNull(@Path("id") int pot_id);

    // ---------------------- plant data ----------------------
    @POST("/plantData/get")
    Call<PlantData> getPlantData(@Body PlantData plantData);
}
