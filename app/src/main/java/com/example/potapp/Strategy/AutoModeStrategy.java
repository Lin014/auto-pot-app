package com.example.potapp.Strategy;

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

public class AutoModeStrategy implements LightModeStrategy{

    @Override
    public void ChangeLightMode(TextView CurLightMode, TextView showLightTime) {
        System.out.println("auto mode");

        PotManager.setLightMode("auto");
        PotManager.setLightStartTime(null);
        PotManager.setLightEndTime(null);

        RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<Pot> call = retrofitAPI.changeLight(PotManager.getPot());
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if (response.code() == 200) {
                    CurLightMode.setText("自動");
                    showLightTime.setText("無");
                    Toast.makeText(MainActivity.mainContext, "燈光模式-自動", Toast.LENGTH_SHORT).show();
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
