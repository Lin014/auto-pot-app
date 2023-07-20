package com.example.potapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManger {

    // Singleton
    private static RetrofitManger mInstance = new RetrofitManger();
    private Retrofit retrofit;
    private RetrofitAPI retrofitAPI;

    private RetrofitManger() {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://87ae-140-125-221-134.jp.ngrok.io/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    public static RetrofitManger getInstance() {
        return mInstance;
    }

    public RetrofitAPI getAPI() {
        return retrofitAPI;
    }
}
