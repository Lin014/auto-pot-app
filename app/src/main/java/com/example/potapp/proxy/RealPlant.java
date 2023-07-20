package com.example.potapp.proxy;

import androidx.recyclerview.widget.RecyclerView;

import com.example.potapp.PotListManager;
import com.example.potapp.entity.User;

public class RealPlant implements Plant {
    private User user;

    public RealPlant(User user) {
        this.user = user;
    }

    @Override
    public void display(RecyclerView recyclerView) {
        PotListManager.getInstance(user);
        recyclerView.setAdapter(PotListManager.getInstance(user).getPlantAdapter());
    }
}

