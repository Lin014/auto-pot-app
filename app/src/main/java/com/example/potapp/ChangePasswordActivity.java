package com.example.potapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChangePasswordActivity extends AppCompatActivity {
    Button saveBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // ---------------- ActionBar ----------------
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ---------------- findViewById -----------------------------------
        saveBtn = findViewById(R.id.saveBtn);

        // ---------------- setListener -----------------------------------
        saveBtn.setOnClickListener(confirmEvent);
    }

    final private View.OnClickListener confirmEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };
}