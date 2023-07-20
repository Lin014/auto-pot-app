package com.example.potapp.chain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.potapp.MainActivity;
import com.example.potapp.PotManager;
import com.example.potapp.RetrofitAPI;
import com.example.potapp.RetrofitManger;
import com.example.potapp.factory.CustomDialog;
import com.example.potapp.factory.CustomDialogFactory;
import com.example.potapp.factory.OneBtnMsgDialogFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckIsDataNull extends PlantHandler {

    protected String message = "目前無法取得資料。";
    private ProgressDialog progressDialog;

    public CheckIsDataNull(PlantHandler next) {
        super(next);
    }

    @Override
    public void handleRequest(int potPosition, Context context) {
        loadEvent(context);
        PotManager.setPosition(potPosition);
        RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<String> call = retrofitAPI.isDataNull(PotManager.getPot().getPot_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    if (response.body().equals("true")) {
                        progressDialog.dismiss();

                        // factory
                        CustomDialogFactory dialogFactory = new OneBtnMsgDialogFactory(
                                context, "訊息", message, "確認");
                        CustomDialog customDialog = dialogFactory.createCustomDialog();
                        customDialog.showDialog();

                    } else {
                        progressDialog.dismiss();

                        // 跳轉 MainActivity
                        Intent intent = new Intent ();
                        intent.setClass (context, MainActivity.class);
                        intent.putExtra("position", potPosition);
                        intent.putExtra("potId", PotManager.getPot().getPot_id());
                        intent.putExtra("potName", PotManager.getPot().getPot_name());
                        progressDialog.dismiss();
                        context.startActivity (intent);
                        toNext(potPosition, context);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
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
