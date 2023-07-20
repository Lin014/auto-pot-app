package com.example.potapp;

import com.example.potapp.entity.Pot;
import com.example.potapp.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PotListManager {

    private static PotListManager pInstance;
    private PlantAdapter plantAdapter = null;

    private PotListManager(User user1) {
        RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<List<Pot>> call = retrofitAPI.getPotsByUserId(user1.getUser_id());

        call.enqueue(new Callback<List<Pot>>() {
            @Override
            public void onResponse(Call<List<Pot>> call, Response<List<Pot>> response) {
                if (response.code() == 200) {
                    plantAdapter = new PlantAdapter();
                    System.out.println(".....");
                    for (Pot pot : response.body()) {
                        plantAdapter.addPottedPlant(pot);
                    }
                    System.out.println("盆栽新增成功");
                } else if (response.code() == 404) {
                    plantAdapter = new PlantAdapter();
                    System.out.println("目前無盆栽");
                } else {
                    System.out.println("伺服器故障，請通知維修人員");
                }
            }
            @Override
            public void onFailure(Call<List<Pot>> call, Throwable t) {

            }
        });
    }

    public static PotListManager getInstance(User user) {
        if (pInstance == null) {
            pInstance = new PotListManager(user);
        }
        return pInstance;
    }

    public static void clearInstance() {
        pInstance = null;
    }

    public PlantAdapter getPlantAdapter() {
        return plantAdapter;
    }
}
