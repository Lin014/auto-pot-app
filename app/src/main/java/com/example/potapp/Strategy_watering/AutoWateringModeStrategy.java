package com.example.potapp.Strategy_watering;

import android.widget.TextView;
import android.widget.Toast;

import com.example.potapp.MainActivity;
import com.example.potapp.PotManager;
import com.example.potapp.RetrofitAPI;
import com.example.potapp.RetrofitManger;
import com.example.potapp.entity.Pot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoWateringModeStrategy implements WateringModeStrategy {

    @Override
    public void ChangeWateringMode(TextView CurWaterMode, TextView showWaterTime) {
        System.out.println("auto mode");

        PotManager.setWateringMode("auto");
        PotManager.setWateringStartTime(null);
        PotManager.setWateringEndTime(null);

        RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<Pot> call = retrofitAPI.changeWatering(PotManager.getPot());
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if (response.code() == 200) {
                    CurWaterMode.setText("自動");
                    showWaterTime.setText("無");
                    Toast.makeText(MainActivity.mainContext, "澆水模式-自動", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.mainContext, "操作失敗，請再試一次", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Toast.makeText(MainActivity.mainContext, "伺服器故障", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
