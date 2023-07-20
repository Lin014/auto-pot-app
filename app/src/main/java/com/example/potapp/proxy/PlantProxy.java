package com.example.potapp.proxy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;

import com.example.potapp.PotListManager;
import com.example.potapp.entity.User;

public class PlantProxy implements Plant {

    private RealPlant realPlant;
    private Context context;
    private ProgressDialog progressDialog;
    private User user;

    public PlantProxy(RealPlant realPlant, User user, Context context) {
        this.realPlant = realPlant;
        this.user = user;
        this.context = context;
    }

    @Override
    public void display(RecyclerView recyclerView) {
        loadEvent();
        realPlant.display(recyclerView);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 100);
                if (PotListManager.getInstance(user).getPlantAdapter() != null) {
                    recyclerView.setAdapter(PotListManager.getInstance(user).getPlantAdapter());
                    progressDialog.dismiss();
                    handler.removeCallbacks(this);
                }
            }
        }, 100);

    }

    private void loadEvent() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}
