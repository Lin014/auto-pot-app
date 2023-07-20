package com.example.potapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.potapp.factory.CustomDialog;
import com.example.potapp.factory.CustomDialogFactory;
import com.example.potapp.factory.OneBtnMsgDialogFactory;

public class SettingActivity extends AppCompatActivity {

    static Activity settingActivity;

    Toolbar toolbar;
    Button passwordBtn, languageBtn, themeBtn, aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingActivity = this;

        // ---------------- ActionBar ----------------
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ---------------- findViewById -----------------------------------
        passwordBtn = findViewById(R.id.passwordBtn);
        languageBtn = findViewById(R.id.languageBtn);
        themeBtn = findViewById(R.id.themeBtn);
        aboutBtn = findViewById(R.id.aboutBtn);

        // ---------------- setListener -----------------------------------
        passwordBtn.setOnClickListener(changePasswordEvent);
        languageBtn.setOnClickListener(preparationEvent);
        themeBtn.setOnClickListener(preparationEvent);
        aboutBtn.setOnClickListener(aboutEvent);
    }

    final private View.OnClickListener preparationEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // factory
            CustomDialogFactory dialogFactory = new OneBtnMsgDialogFactory(SettingActivity.this, "訊息", "抱歉，這個功能正在開發中，敬請期待！", "OK");
            CustomDialog customDialog = dialogFactory.createCustomDialog();
            customDialog.showDialog();
        }
    };

    final private View.OnClickListener changePasswordEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(SettingActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        }
    };

    final private View.OnClickListener aboutEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // factory
            CustomDialogFactory dialogFactory = new OneBtnMsgDialogFactory(SettingActivity.this, "關於", "這是個植物應用程式", "我知道了");
            CustomDialog customDialog = dialogFactory.createCustomDialog();
            customDialog.showDialog();
        }
    };
}