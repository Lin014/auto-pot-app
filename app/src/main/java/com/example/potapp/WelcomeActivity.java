package com.example.potapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    static Activity welcomeActivity;
    private ImageView secondImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeActivity = this;
        secondImage = (ImageView) findViewById(R.id.imageView);
        secondImage.setImageResource(R.drawable.logo);

        // 兩秒鐘進入主頁面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //執行在主執行緒
                //啟動主頁面
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                //關閉當前頁面
            }
        },2000);
    }
}