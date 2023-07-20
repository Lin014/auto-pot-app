package com.example.potapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.potapp.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    static Activity loginActivity;

    RetrofitAPI retrofitAPI;

    EditText enterAcc, enterPwd;
    Button loginBtn, registerBtn;
    Button btn_jump;

    private ProgressDialog progressDialog;

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;

        // ---------------- findViewById -----------------------------------
        registerBtn =  findViewById(R.id.registerBtn);
        enterAcc = findViewById(R.id.enterAcc);
        enterPwd = findViewById(R.id.enterPwd);
        loginBtn = findViewById(R.id.loginBtn);

        // ---------------- setListener -----------------------------------
        registerBtn.setOnClickListener(registerEvent);
        loginBtn.setOnClickListener(loginEvent);
    }

    final private View.OnClickListener loginEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String account = enterAcc.getText().toString();
            String password = enterPwd.getText().toString();
                                                      
            if (!(account.isEmpty() || password.isEmpty())) {
                User addUser = new User(account, password);
                login(addUser);
            } else {
                Toast.makeText(getApplicationContext(), "請確認所有欄位皆已填", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void login(User user1) {
        loadEvent(LoginActivity.this);

        retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<User> call = retrofitAPI.login(user1);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast toast = new Toast(getApplicationContext());
                if (response.code() == 200) {
                    user = response.body();
                    toast.setText("登入成功");

                    progressDialog.dismiss();

                    // turn to menu
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, PottedPlantMenu.class);
                    startActivity(intent);
                } else if (response.code() == 404){
                    toast.setText("帳號或密碼錯誤");
                    progressDialog.dismiss();
                } else {
                    toast.setText("伺服器故障，請通知維修人員");
                    progressDialog.dismiss();
                }
                toast.show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    final private View.OnClickListener registerEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    };

    protected void onStart() {
        super.onStart();
        WelcomeActivity.welcomeActivity.finish();
        if (PottedPlantMenu.pottedPlantMenuActivity != null) {
            PottedPlantMenu.pottedPlantMenuActivity.finish();
        }
        if (MainActivity.mainActivity != null) {
            MainActivity.mainActivity.finish();
        }
    }

    private void loadEvent(Context loadContext) {
        progressDialog = new ProgressDialog(loadContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
}