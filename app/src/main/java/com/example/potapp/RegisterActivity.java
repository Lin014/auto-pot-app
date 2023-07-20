package com.example.potapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.potapp.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    static Activity register;
    Toolbar toolbar;

    EditText account, password, checkPassword;
    Button registerBtn;

    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = this;

        // ---------------- ActionBar ----------------
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ---------------- findViewById -----------------------------------
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        checkPassword = findViewById(R.id.checkPassword);
        registerBtn = findViewById(R.id.registerBtn);

        // ---------------- setListener -----------------------------------
        registerBtn.setOnClickListener(registerEvent);
    }

    final private View.OnClickListener registerEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String pwd = password.getText().toString();
            String checkPwd = checkPassword.getText().toString();
            String acc= account.getText().toString();

            if (Validation.isEmpty(pwd) || Validation.isEmpty(checkPwd) || Validation.isEmpty(acc)) {
                Toast.makeText(getApplicationContext(), "請確認所有欄位皆已填", Toast.LENGTH_SHORT).show();
            } else {
                if (checkAccount(acc) && checkPwd(pwd) && checkPwd(checkPwd)) {
                    if (!pwd.equals(checkPwd)) {
                        Toast.makeText(getApplicationContext(), "密碼不相符", Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User(acc, pwd);
                        registerUser(user);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "帳號或密碼格式錯誤", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private boolean checkAccount(String account) {
        return Validation.isFirsCharAlpha(account) && Validation.isCharValid(account) && Validation.isLengthValid(account);
    }

    private boolean checkPwd(String password) {
        return Validation.isLengthValid(password) && Validation.isCharValid(password);
    }

    private void registerUser(User user) {
        RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<String> call = retrofitAPI.registerUser(user);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast toast = new Toast(getApplicationContext());
                if (response.code() == 200) { // HttpStatus.OK
                    toast.setText("註冊成功");
                } else if (response.code() == 400) { // HttpStatus.BAD_REQUEST
                    toast.setText("帳戶已存在");
                } else {
                    toast.setText("伺服器故障，請通知維修人員");
                }
                toast.show();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}