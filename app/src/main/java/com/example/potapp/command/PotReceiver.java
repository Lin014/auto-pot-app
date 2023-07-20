package com.example.potapp.command;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.potapp.ImageActivity;
import com.example.potapp.MainActivity;
import com.example.potapp.PotManager;
import com.example.potapp.R;
import com.example.potapp.RetrofitAPI;
import com.example.potapp.RetrofitManger;
import com.example.potapp.entity.Pot;
import com.example.potapp.factory.CustomDialog;
import com.example.potapp.factory.CustomDialogFactory;
import com.example.potapp.factory.TwoBtnInputDialogFactory;
import com.example.potapp.factory.TwoBtnMsgDialogFactory;
import com.example.potapp.observer.PotSubject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PotReceiver {
    RetrofitAPI retrofitAPI;
    boolean isDuplicatePotName;
    public static Context context;


    private ProgressDialog progressDialog;

    public PotReceiver() {
    }

    private static final int REQUEST_CODE = 22;

    public void changePotName(int potPosition, Context context1) {
        context = context1;

        // factory
        CustomDialogFactory dialogFactory = new TwoBtnInputDialogFactory(context1, "更改盆栽名稱", "盆栽名稱: ", "確認", "取消");
        CustomDialog customDialog = dialogFactory.createCustomDialog();
        customDialog.showDialog();

        EditText editText = customDialog.findViewById(R.id.editText);
        editText.setHint("Ex: Cute Pot");
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        Button okBtn = customDialog.findViewById(R.id.positiveButton);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = customDialog.findViewById(R.id.editText);
                String potName = editText.getText().toString();

                if (potName.isEmpty()) {
                    Toast.makeText(context, "未輸入盆栽名稱", Toast.LENGTH_SHORT).show();
                } else {
                    // 查找同個使用者的盆栽名稱有無重複
                    List<Pot> potList = PotManager.getPotList();
                    for (Pot pot : potList) {
                        if (pot.getPot_name().equals(potName)) {
                            isDuplicatePotName = true;
                            break;
                        }
                    }
                    if (isDuplicatePotName) {
                        Toast.makeText(context, "盆栽名稱重複", Toast.LENGTH_SHORT).show();
                    } else {
                        loadEvent(context1);

                        PotManager.setPosition(potPosition);
                        Pot pot = PotManager.getPot();
                        pot.setPot_name(potName);
                        retrofitAPI = RetrofitManger.getInstance().getAPI();
                        Call<Pot> call = retrofitAPI.changePotName(pot);
                        call.enqueue(new Callback<Pot>() {
                            @Override
                            public void onResponse(Call<Pot> call, Response<Pot> response) {
                                if (response.code() == 200) {
                                    // update pot list
                                    PotManager.setPotName(potName);
                                    // update mainActivity potName
                                    PotSubject.getInstance().setPot(pot);

                                    progressDialog.dismiss();
                                    customDialog.dismiss();
                                    Toast.makeText(context1, "盆栽名稱更改成功", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Pot> call, Throwable t) {
                                Toast.makeText(context, "伺服器故障", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    public void changePlantName(int potPosition, Context context1) {
        context = context1;

        // factory
        CustomDialogFactory dialogFactory = new TwoBtnInputDialogFactory(context1, "更改植物名稱", "植物名稱: ", "確認", "取消");
        CustomDialog customDialog = dialogFactory.createCustomDialog();
        customDialog.showDialog();

        EditText editText = customDialog.findViewById(R.id.editText);
        editText.setHint("Ex: Cute Plant");
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        Button okBtn = customDialog.findViewById(R.id.positiveButton);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = customDialog.findViewById(R.id.editText);
                String plantName = editText.getText().toString();

                if (plantName.isEmpty()) {
                    Toast.makeText(context, "未輸入植物名稱", Toast.LENGTH_SHORT).show();
                } else {
                    loadEvent(context1);

                    PotManager.setPosition(potPosition);
                    Pot pot = PotManager.getPot();
                    pot.setPlant_name(plantName);

                    retrofitAPI = RetrofitManger.getInstance().getAPI();
                    Call<Pot> call = retrofitAPI.changePlantName(pot);
                    call.enqueue(new Callback<Pot>() {
                        @Override
                        public void onResponse(Call<Pot> call, Response<Pot> response) {
                            if (response.code() == 200) {
                                // update pot list
                                PotManager.setPlantName(plantName);
                                if (PotManager.getPlantNameChangeState() != 1) {
                                    changePlantNameState(pot, 1);
                                }
                                // update mainActivity plantName
                                PotSubject.getInstance().setPot(pot);
                                progressDialog.dismiss();
                                customDialog.dismiss();
                                Toast.makeText(context1, "植物名稱更改成功", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Pot> call, Throwable t) {
                            Toast.makeText(context, "伺服器故障", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void changePlantImg(int potPosition, Context context1) {
        context = context1;
        Intent intent1 = new Intent();
        intent1.setClass(context, ImageActivity.class);
        intent1.putExtra("potPosition", potPosition);
        context.startActivity(intent1);
    }

    public void delPot(int potPosition, Context context1) {
        context = context1;

        // factory
        CustomDialogFactory dialogFactory = new TwoBtnMsgDialogFactory(context1, "刪除盆栽", "您要刪除盆栽嗎?", "確認", "取消");
        CustomDialog customDialog = dialogFactory.createCustomDialog();
        customDialog.showDialog();

        Button okBtn = customDialog.findViewById(R.id.positiveButton);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEvent(context1);

                PotManager.setPosition(potPosition);
                Pot pot = PotManager.getPot();
                retrofitAPI = RetrofitManger.getInstance().getAPI();
                Call<String> call = retrofitAPI.delPot(pot);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "刪除成功", Toast.LENGTH_SHORT).show();
                            PotManager.removePot(potPosition);
                            if (MainActivity.mainActivity != null ) {
                                MainActivity.mainActivity.finish();
                            }
                            progressDialog.dismiss();
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context, "伺服器故障", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void changePlantNameState(Pot pot1, int state) {
        pot1.setPlant_name_change_state(state);
        RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<Pot> call = retrofitAPI.changePlantNameState(pot1);
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if (response.code() == 200) {
                    PotManager.setPlantNameChangeState(state);
//                    Toast.makeText(context, "更改植物名稱狀態成功: " + response.body().getPlant_name_change_state(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                Toast.makeText(context, "伺服器故障", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadEvent(Context loadContext) {
        progressDialog = new ProgressDialog(loadContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}
