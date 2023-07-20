package com.example.potapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.potapp.entity.Pot;
import com.example.potapp.entity.User;
import com.example.potapp.factory.CustomDialog;
import com.example.potapp.factory.CustomDialogFactory;
import com.example.potapp.factory.OneBtnMsgDialogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPotActivity extends AppCompatActivity {

    public static Activity addPotActivity;
    Toolbar toolbar;

    EditText editText;

    RetrofitAPI retrofitAPI;

    Pot addPot;
    User user;
    boolean isDuplicatePotName = false;

    // ---------------- socket start -----------------------------------
    Socket socket = null;
    String socketState = "failed";
    Thread SocketThread1 = null;
    Thread SocketThread3 = null;
    String serverIP;
    final int serverPort = 8080;

    String WiFiSSID;
    String WiFiPwd;
    String potName;
    String potMfName;

    Handler handler;
    ProgressDialog progressDialog;
    // ---------------- socket end -----------------------------------

    ImageButton back;
    Button connectBtn, positiveBtn;
    TextView connectMsg, positiveMsg;
    EditText enterPotName, enterWiFiSSID, enterWiFiPwd;

    Bitmap defaultPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pot);
        addPotActivity = this;

        // ---------------- ActionBar ----------------
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ------------- findViewById -----------------
        connectBtn = findViewById(R.id.connectBtn);
        connectMsg = findViewById(R.id.connectMsg);
        positiveBtn = findViewById(R.id.positiveBtn);
        enterPotName = findViewById(R.id.enterPotName);
        enterWiFiSSID = findViewById(R.id.enterWiFiSSID);
        enterWiFiPwd = findViewById(R.id.enterWiFiPwd);

        // ------------- setListener -----------------
        connectBtn.setOnClickListener(connectEvent);
        positiveBtn.setOnClickListener(positiveEvent);


        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, PackageManager.PERMISSION_GRANTED);

        defaultPhoto = BitmapFactory.decodeResource(this.getResources(), R.drawable.add_plant);

        user = PottedPlantMenu.user;

        // test
//        connectMsg.setText("連線成功");
    }

    final private View.OnClickListener connectEvent = new View.OnClickListener() {
        @Override
        public synchronized void onClick(View view) {
            connectMsg.setText("");
            if (socket == null) {
                serverIP = getServerAddress();
                System.out.println("ServerAddress: " + serverIP);
                System.out.println("ServerPort: " + serverPort);
                SocketThread1 = new Thread(new SocketThread1());
                SocketThread1.start();

                handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!connectMsg.getText().toString().equals("連線成功")) {
                            connectMsg.setText("連線失敗");
                        }
                    }
                };
                handler.postDelayed(runnable, 1000);

            }
        }
    };

    final private View.OnClickListener positiveEvent = new View.OnClickListener() {
        @Override
        public synchronized void onClick(View view) {
            if (
//                    !enterWiFiSSID.getText().toString().isEmpty() &&
//                    !enterWiFiPwd.getText().toString().isEmpty() &&
//                    !connectMsg.getText().toString().equals("還未連線") &&
                    !enterPotName.getText().toString().isEmpty()
            ) {
                potName = enterPotName.getText().toString();

                // 查找同個使用者的盆栽名稱有無重複
                List<Pot> potList = PotListManager.getInstance(user).getPlantAdapter().getPotList();
                for (Pot pot : potList) {
                    if (pot.getPot_name().equals(potName)) {
                        isDuplicatePotName = true;
                        break;
                    }
                }
                if (isDuplicatePotName) {
                    Toast.makeText(getApplicationContext(), "盆栽名稱重複", Toast.LENGTH_SHORT).show();
                } else {
                    loadEvent(AddPotActivity.this);
                    addPot = new Pot("test", potName, PottedPlantMenu.user);
                    addPot(addPot);

//                    WiFiSSID = enterWiFiSSID.getText().toString();
//                    WiFiPwd = enterWiFiPwd.getText().toString();
//                    SocketThread3 = new Thread(new SocketThread3(WiFiSSID + "/" + WiFiPwd));
//                    SocketThread3.start();



//                    new Thread(new SocketThread3(WiFiSSID + "/" + WiFiPwd)).start();
//                    positiveMsg.setText("新增成功");

                    // 測試用：查找盆栽有多少個，mfName = count + 1，確保mfName的唯一性
//                    retrofitAPI = RetrofitManger.getInstance().getAPI();
//                    Call<String> call = retrofitAPI.getCountPot();
//                    call.enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            if (response.code() == 200) {
//                                int count = Integer.parseInt(response.body());
//                                addPot = new Pot("Pot" + (count + 1), potName, PottedPlantMenu.user);
//                                addPot(addPot);
//                                System.out.println(count);
//                            } else {
//                                System.out.println("伺服器故障，請通知維修人員");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
//
//                        }
//                    });



//                try {
//                    Thread.sleep((int)(1000*Math.random()));
//                    socket.close();
//                    SocketThread1.interrupt();
//                    SocketThread3.interrupt();
//                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }

                }
            } else {
                if (connectMsg.getText().toString().equals("還未連線")) {
                    Toast.makeText(getApplicationContext(), "請先完成連線動作", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "有欄位為空", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void addPot(Pot addPot) {

        retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<Pot> call = retrofitAPI.addPot(addPot);

        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {
                if (response.code() == 200) {
                    PotListManager.getInstance(user).getPlantAdapter().addPottedPlant(response.body());
//                    PotListManager.getInstance(user).addPottedPlant(addPot.getPot_name(), defaultPhoto, "無");
                    CustomDialogFactory dialogFactory = new OneBtnMsgDialogFactory(AddPotActivity.this, "訊息", "盆栽新增成功", "關閉");
                    CustomDialog customDialog = dialogFactory.createCustomDialog();

                    progressDialog.dismiss();
                    customDialog.showDialog();
                } else {
                    positiveMsg.setText("伺服器故障，請通知維修人員");
                }
            }
            @Override
            public void onFailure(Call<Pot> call, Throwable t) {
                System.out.println("addPot Failed");
            }
        });
    }

    private String getServerAddress() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wm != null) {
            DhcpInfo info = wm.getDhcpInfo();
            if (info != null) {
                int tmp = info.serverAddress;
                String serverAddress = String.format("%d.%d.%d.%d", (tmp & 0xff), (tmp >> 8 & 0xff), (tmp >> 16 & 0xff), (tmp >> 24 & 0xff));
                return serverAddress;
            }
        }
        return "null";
    }

    private String getSSID() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wm.getConnectionInfo();
        String s = wifiInfo.getSSID();
        System.out.println("ssid: " + s);
        return s;
    }

    private String getIP() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wm != null) {
            WifiInfo wifiInfo = wm.getConnectionInfo();

            if (wifiInfo != null) {
                int tmp = wifiInfo.getIpAddress();
                String ip = String.format("%d.%d.%d.%d", (tmp & 0xff), (tmp >> 8 & 0xff), (tmp >> 16 & 0xff), (tmp >> 24 & 0xff));
                return ip;
            }
        }
        return "null";
    }

    private PrintWriter output;
    private BufferedReader input;

    class SocketThread1 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("SocketThread1: run...");
                socket = new Socket(serverIP, serverPort);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connectMsg.setText("連線成功");
                        System.out.println("SocketThread1: Connected");
                        potMfName = getSSID();
                        System.out.println("potMfName: " + potMfName);
                    }
                });
//                new Thread(new AddPotActivity.SocketThread2()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SocketThread3 implements Runnable {
        private String message;
        SocketThread3(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            output.write(message);
            output.flush();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("SocketThread3: Send: " + message);

                    Runnable addPotRunnable = new Runnable() {
                        @Override
                        public void run() {
                            potMfName = potMfName.substring(1, potMfName.length() - 1);
                            System.out.println("potMfName: " + potMfName);
                            addPot = new Pot(potMfName, potName, PottedPlantMenu.user);
                            addPot(addPot);
                        }
                    };
                    handler.postDelayed(addPotRunnable, 1000);

                }
            });
        }
    }

    private void loadEvent(Context loadContext) {
        progressDialog = new ProgressDialog(loadContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    //    class SocketThread2 implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    final String message = input.readLine();
//                    if (message != null) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                System.out.println("SocketThread2: Get: " + message);
//                            }
//                        });
//                    } else {
//                        SocketThread1 =  new Thread(new AddPotActivity.SocketThread1());
//                        SocketThread1.start();
//                        return;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}

