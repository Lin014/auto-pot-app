package com.example.potapp.Strategy;

import android.widget.TextView;
import android.widget.Toast;

import com.example.potapp.MainActivity;
import com.example.potapp.PotManager;
import com.example.potapp.RetrofitAPI;
import com.example.potapp.RetrofitManger;
import com.example.potapp.entity.PlantData;
import com.example.potapp.entity.Pot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmartModeStrategy implements LightModeStrategy{

    String smartMode;
    String startTime, endTime;
    RetrofitAPI retrofitAPI;

    public SmartModeStrategy(String smartMode){
        this.smartMode = smartMode;
    }

    @Override
    public void ChangeLightMode(TextView CurLightMode, TextView showLightTime) {
        System.out.println(smartMode);
        // 獲取智慧模式時間
        retrofitAPI = RetrofitManger.getInstance().getAPI();
        PlantData plantData = new PlantData();
        plantData.setPlant_type(PotManager.getPlantType());
        Call<PlantData> call = retrofitAPI.getPlantData(plantData);
        call.enqueue(new Callback<PlantData>() {
            @Override
            public void onResponse(Call<PlantData> call, Response<PlantData> response) {
                if (response.code() == 200) {
                    PotManager.setLightMode("smart");
                    PotManager.setLightStartTime(response.body().getLight_start_time());
                    PotManager.setLightEndTime(response.body().getLight_end_time());
                    // 更改光模式與時間
                    retrofitAPI = RetrofitManger.getInstance().getAPI();
                    Call<Pot> call1 = retrofitAPI.changeLight(PotManager.getPot());
                    call1.enqueue(new Callback<Pot>() {
                        @Override
                        public void onResponse(Call<Pot> call, Response<Pot> response) {
                            if (response.code() == 200) {
                                CurLightMode.setText("智慧");
                                String smartTime = PotManager.getLightStartTime() + "-" + PotManager.getLightEndTime();
                                showLightTime.setText(smartTime);
                                Toast.makeText(MainActivity.mainContext, "燈光模式-智慧： " + smartMode, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.mainContext, "操作失敗，請再試一次", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Pot> call, Throwable t) {
                            Toast.makeText(MainActivity.mainContext, "伺服器故障", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.mainContext, "操作失敗，請再試一次", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantData> call, Throwable t) {
                Toast.makeText(MainActivity.mainContext, "伺服器故障", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
