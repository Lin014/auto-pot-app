package com.example.potapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.potapp.Iterator.Aggregate;
import com.example.potapp.Iterator.Iterator;
import com.example.potapp.Iterator.concrete_aggregate_light;
import com.example.potapp.Iterator.concrete_aggregate_soil;
import com.example.potapp.Iterator.concrete_aggregate_time;
import com.example.potapp.Iterator.concrete_aggregate_water;
import com.example.potapp.Memento.Caretaker;
import com.example.potapp.Memento.Originator;
import com.example.potapp.entity.Data;
import com.example.potapp.entity.Pot;
import com.example.potapp.fragment_plant.history;
import com.example.potapp.fragment_plant.plant_state;
import com.example.potapp.fragment_plant.pot_set;
import com.example.potapp.observer.DataSubject;
import com.example.potapp.observer.Observer;
import com.example.potapp.observer.PotSubject;
import com.example.potapp.observer.Subject;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Observer {

    Handler handler;
    Runnable getDataRunnable;
    public static Activity mainActivity;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    public static int potPosition;
    public static Context mainContext;

    public static Originator or = new Originator();
    public static Caretaker cr = new Caretaker();
    public static Aggregate c_a_t = new concrete_aggregate_time(4);
    public static Aggregate c_a_s = new concrete_aggregate_soil(4);
    public static Aggregate c_a_l = new concrete_aggregate_light(4);
    public static Aggregate c_a_w = new concrete_aggregate_water(4);
    public static Iterator it_t,it_s,it_l,it_w;
    public static int index,num = 0;
    public static String time;
    public static int soil,light,water;

    public Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        mainContext = getApplicationContext();

        // ---------------- ActionBar ----------------
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ---------------- findViewById -----------------------------------
        bottomNavigationView = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.viewPagerMain);

        // ---------------- setListener -----------------------------------
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        viewPager.setOnPageChangeListener(onPageChangeListener);

        // ---------------- set MainPagerAdapter ----------------
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new plant_state(), "PlantState");
        adapter.addFragment(new pot_set(), "PotSetting");
        adapter.addFragment(new history(), "History");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        // ---------------- set title ----------------
        Intent intent = getIntent();
        potPosition = intent.getIntExtra("position", 0);
        PotManager.setPosition(potPosition);
        System.out.println("potPosition: " + potPosition);
        String potName = intent.getStringExtra("potName");
        toolbar.setTitle(potName);

        // addObserver
        PotSubject.getInstance().addObserver(this);

        data = new Data(0, "0", "0", "0", 0, 0, "0", 0, PotManager.getPot());

        // dataSubject
        handler = new Handler();
        getDataRunnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 500);

                RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
                Call<Data> call = retrofitAPI.getLastDataByPotId(PotManager.getPotId());
                call.enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if (response.code() == 200) {
                            if (!response.body().getData_time().equals(data.getData_time())) {
                                System.out.println(response.body().getData_water_level_state());
                                data = response.body();
                                DataSubject.getInstance().setData(data);
                                System.out.println("dataTime: " + data.getData_time());
                                run_getdata(data);
                            }
                        } else if (response.code() == 404) {
                            Toast.makeText(getApplicationContext(), "無資料", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "伺服器故障", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "伺服器故障", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        handler.postDelayed(getDataRunnable, 1000);
    }

    // 製作BottomNavigationView按下個方法:
    final private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // BottomNavigationView按下時判斷Menu的ID，讓ViewPaper跳去相對應的Fragment:
            switch (item.getItemId()) {
                case R.id.plant_state:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.pot_set:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.history:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return false;
        }
    };

    final private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 將相對應的bottomNavigationView選項選取:
            menuItem = bottomNavigationView.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(getDataRunnable);
        PotSubject.getInstance().removeObserver(this);
        DataSubject.clearInstance();
        System.out.println("clear instance....");
    }

    private void run_getdata(Data responseFromAPI_water) {
        //Get the data and store the data in the concrete aggregate
        try {
            if (history.instant_data) {
                if (num == 0) {
                    c_a_t = new concrete_aggregate_time(4);
                    c_a_s = new concrete_aggregate_soil(4);
                    c_a_l = new concrete_aggregate_light(4);
                    c_a_w = new concrete_aggregate_water(4);
                }
                c_a_t.append(responseFromAPI_water.getData_time());
                c_a_s.append(responseFromAPI_water.getData_soil_moisture());
                c_a_l.append(responseFromAPI_water.getData_light_lux());
                c_a_w.append(responseFromAPI_water.getData_water_level());

                time = responseFromAPI_water.getData_time();
                soil = responseFromAPI_water.getData_soil_moisture();
                light = responseFromAPI_water.getData_light_lux();
                water = responseFromAPI_water.getData_water_level();
                num++;
                if (num > 3) {
                    //When the data in the concrete aggregate is greater than 4, create an iterator and store the iterator in memento.
                    num = 0;
                    it_t = c_a_t.iterator();
                    it_s = c_a_s.iterator();
                    it_l = c_a_l.iterator();
                    it_w = c_a_w.iterator();
                    or.setState(it_t, it_s,it_l,it_w);
                    index = cr.getIndex();
                    if (index == 30) {
                        history.more_than_30 = true;
                        cr.setIndex(0);
                    }
                    cr.setMemento(or.createMemento());
                    if(cr.getIndex() == 30){
                        cr.reset();
                    }
                }
                history.run_getdata(time, soil,light,water);
            }
        } catch (NullPointerException e) {
            System.out.println("wait");
        }
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof PotSubject) {
            Pot subjectPot = PotSubject.getInstance().getPot();
            toolbar.setTitle(subjectPot.getPot_name());
        }
    }
}