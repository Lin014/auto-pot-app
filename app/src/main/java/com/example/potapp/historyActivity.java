package com.example.potapp;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.example.potapp.Iterator.Iterator;
import com.example.potapp.Memento.Caretaker;
import com.example.potapp.Memento.Originator;
import com.example.potapp.fragment_plant.history;

public class historyActivity extends AddPotActivity {


    private TextView[] time_list = new TextView[4];
    private TextView[] soil_list = new TextView[4];
    private TextView[] light_list = new TextView[4];
    private TextView[] water_list = new TextView[4];
    private Caretaker cr = new Caretaker();
    private Originator or = new Originator();
    private Iterator it_t,it_s,it_l,it_w;

    int index = 0, max;

    private Button exit, page_up, page_down, search;
    private TextView textView;
    private EditText et;
    private FragmentActivity activity;


    public void setButtonCustomDialog(FragmentActivity activity) {
        //Through the dialog box, after setting the component, display the previous data.
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View v = layoutInflater.inflate(R.layout.history_layout, null, false);
        alertDialog.setView(v);
        exit = v.findViewById(R.id.button);
        page_up = v.findViewById(R.id.button2);
        page_down = v.findViewById(R.id.button3);
        search = v.findViewById(R.id.button4);

        this.activity = activity;

        page_up.setOnClickListener(up);
        page_down.setOnClickListener(down);
        search.setOnClickListener(search_click);

        et = v.findViewById(R.id.editText);

        this.cr = MainActivity.cr;
        this.or = MainActivity.or;
        this.it_t = MainActivity.it_t;
        this.it_s = MainActivity.it_s;
        this.it_l = MainActivity.it_l;
        this.it_w = MainActivity.it_w;

        max = cr.getIndex()-1;
        if(max < 0){
            max = 0;
        }

        textView = v.findViewById(R.id.textView2);
        textView.setText(index + 1 + "/" + (max + 1) + "頁");

        time_list[0] = v.findViewById(R.id.data_1_time);
        time_list[1] = v.findViewById(R.id.data_2_time);
        time_list[2] = v.findViewById(R.id.data_3_time);
        time_list[3] = v.findViewById(R.id.data_4_time);
        soil_list[0] = v.findViewById(R.id.data_1_soil);
        soil_list[1] = v.findViewById(R.id.data_2_soil);
        soil_list[2] = v.findViewById(R.id.data_3_soil);
        soil_list[3] = v.findViewById(R.id.data_4_soil);
        light_list[0] = v.findViewById(R.id.data_1_light);
        light_list[1] = v.findViewById(R.id.data_2_light);
        light_list[2] = v.findViewById(R.id.data_3_light);
        light_list[3] = v.findViewById(R.id.data_4_light);
        water_list[0] = v.findViewById(R.id.data_1_water);
        water_list[1] = v.findViewById(R.id.data_2_water);
        water_list[2] = v.findViewById(R.id.data_3_water);
        water_list[3] = v.findViewById(R.id.data_4_water);
        try {
            or.restoreMemento(cr.getMemento(index));
            it_t = or.getState_time();
            it_s = or.getState_soil();
            it_l = or.getState_light();
            it_w = or.getState_water();
            int next = 0;
            it_t.reset();
            it_s.reset();
            it_l.reset();
            it_w.reset();
            while (it_t.hasNext()) {
                time_list[next].setText(it_t.next().toString());
                soil_list[next].setText("土壤濕度：" + it_s.next());
                light_list[next].setText("光照度：" + it_l.next());
                water_list[next].setText("水量：" + it_w.next());
                next++;
            }
        }
        catch (NullPointerException e){
            Toast.makeText(activity, "先前紀錄尚未更新", Toast.LENGTH_SHORT).show();
        }

        AlertDialog dialog = alertDialog.create();
        dialog.show();
        exit.setOnClickListener((v1 -> {
            history.instant_data = true;
            dialog.hide();
        }));


    }
    private Button.OnClickListener up = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Show last previous record.
            try {
                index -= 1;
                if (index < 0) {
                    index = max;
                }
                or.restoreMemento(cr.getMemento(index));
                it_t = or.getState_time();
                it_s = or.getState_soil();
                it_l = or.getState_light();
                it_w = or.getState_water();
                int next = 0;
                it_t.reset();
                it_s.reset();
                it_l.reset();
                it_w.reset();
                while (it_t.hasNext()) {
                    time_list[next].setText(it_t.next().toString());
                    soil_list[next].setText("土壤濕度：" + it_s.next());
                    light_list[next].setText("光照度：" + it_l.next());
                    water_list[next].setText("水量：" + it_w.next());
                    next++;
                }
                textView.setText(index + 1 + "/" + (max + 1) + "頁");
            }
            catch (NullPointerException e){
                Toast.makeText(activity, "關閉後再試一次", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Button.OnClickListener down = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Show next previous record.
            try {
                index += 1;
                if (index > max) {
                    index = 0;
                }
                or.restoreMemento(cr.getMemento(index));
                it_t = or.getState_time();
                it_s = or.getState_soil();
                it_l = or.getState_light();
                it_w = or.getState_water();
                int next = 0;
                it_t.reset();
                it_s.reset();
                it_l.reset();
                it_w.reset();
                while (it_t.hasNext()) {
                    time_list[next].setText(it_t.next().toString());
                    soil_list[next].setText("土壤濕度：" + it_s.next());
                    light_list[next].setText("光照度：" + it_l.next());
                    water_list[next].setText("水量：" + it_w.next());
                    next++;
                }
                textView.setText(index + 1 + "/" + (max+1) + "頁");
            }
            catch (NullPointerException e){
                Toast.makeText(activity, "關閉後再試一次", Toast.LENGTH_SHORT).show();
            }
        }

    };
    private Button.OnClickListener search_click = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            //By entering a specific location, display previous records for that location.
            try {
                index = Integer.parseInt(et.getText().toString());
                if(index > max+1 || index <= 0){
                    Toast.makeText(historyActivity.this, "超出範圍", Toast.LENGTH_LONG).show();
                }
                else{
                    or.restoreMemento(cr.getMemento(index - 1));
                    it_t = or.getState_time();
                    it_s = or.getState_soil();
                    it_l = or.getState_light();
                    it_w = or.getState_water();
                    int next = 0;
                    it_t.reset();
                    it_s.reset();
                    it_l.reset();
                    it_w.reset();
                    while (it_t.hasNext()) {
                        time_list[next].setText(it_t.next().toString());
                        soil_list[next].setText("土壤濕度：" + it_s.next());
                        light_list[next].setText("光照度：" + it_l.next());
                        water_list[next].setText("水量：" + it_w.next());
                        next++;
                    }
                    textView.setText(index + "/" + (max+1) + "頁");
                    index--;
                }
            } catch (NullPointerException e) {
                Toast.makeText(activity, "超出範圍", Toast.LENGTH_SHORT).show();
            }
            catch (ArrayIndexOutOfBoundsException e){
                Toast.makeText(activity, "超出範圍", Toast.LENGTH_SHORT).show();
            }
            catch (NumberFormatException e){
                Toast.makeText(activity, "請輸入正確值", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
