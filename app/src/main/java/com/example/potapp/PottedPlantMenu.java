package com.example.potapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.potapp.entity.Pot;
import com.example.potapp.entity.User;
import com.example.potapp.factory.CustomDialog;
import com.example.potapp.factory.CustomDialogFactory;
import com.example.potapp.factory.OneBtnMsgDialogFactory;
import com.example.potapp.factory.TwoBtnMsgDialogFactory;
import com.example.potapp.observer.PotSubject;
import com.example.potapp.proxy.Plant;
import com.example.potapp.proxy.PlantProxy;
import com.example.potapp.proxy.RealPlant;

import java.util.List;

public class PottedPlantMenu extends AppCompatActivity {
    private RetrofitAPI retrofitAPI;
    private Toolbar toolbar;

    public static Context pottedPlantMenuContext;
    public static Activity pottedPlantMenuActivity;

    // data
    static User user;
    private List<Pot> potList = null;

    private RecyclerView recyclerView;
    static PlantAdapter plantAdapter;
    private Bitmap defaultPhoto;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potted_plant_menu);
        pottedPlantMenuActivity = this;
        pottedPlantMenuContext = this;

        // get user from login
        user = LoginActivity.user;

        // ---------------- ActionBar ----------------
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        // ---------------- findViewById -----------------------------------
        recyclerView = findViewById(R.id.recycler_view);
        Button addPot = findViewById(R.id.addPot);

        // ---------------- setListener -----------------------------------
        addPot.setOnClickListener(addPotEvent);

        // 設置RecyclerView為列表型態
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 設置格線
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RealPlant realPlant = new RealPlant(user);
        Plant plantProxy = new PlantProxy(realPlant, user, this);
        plantProxy.display(recyclerView);
    }

    final private View.OnClickListener addPotEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(PottedPlantMenu.this, AddPotActivity.class);
            startActivity(intent);
        }
    };

    protected void onStart() {
        super.onStart();
        if (LoginActivity.loginActivity != null) {
            LoginActivity.loginActivity.finish();
        }
        if (AddPotActivity.addPotActivity != null) {
            AddPotActivity.addPotActivity.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 設置要用哪個menu檔做為選單
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;   //返回true表顯示
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        LayoutInflater inflater = LayoutInflater.from(PottedPlantMenu.this);
        final View v;

        // 取得點選項目的id
        int id = item.getItemId();

        // 依照id判斷點了哪個項目並做相應事件
        if (id == R.id.setting) {
            // 按下「設定」要做的事
            intent.setClass(PottedPlantMenu.this, SettingActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.help) {

            // factory
            CustomDialogFactory dialogFactory = new OneBtnMsgDialogFactory(PottedPlantMenu.this, "訊息", "抱歉，這個功能正在開發中，敬請期待！", "OK");
            CustomDialog customDialog = dialogFactory.createCustomDialog();
            customDialog.showDialog();

            return true;
        } else if (id == R.id.logout) {

            // factory
            CustomDialogFactory dialogFactory = new TwoBtnMsgDialogFactory(PottedPlantMenu.this, "登出", "確定要登出嗎?", "確定", "取消");
            CustomDialog customDialog = dialogFactory.createCustomDialog();
            customDialog.showDialog();

            customDialog.findViewById(R.id.positiveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PotSubject.clearInstance();
                    PotListManager.clearInstance();
                    intent.setClass(PottedPlantMenu.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(PottedPlantMenu.this, "已登出", Toast.LENGTH_SHORT).show();
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}