package com.example.potapp.fragment_plant;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.potapp.MainActivity;
import com.example.potapp.PotManager;
import com.example.potapp.R;
import com.example.potapp.RetrofitAPI;
import com.example.potapp.RetrofitManger;
import com.example.potapp.Strategy.AutoModeStrategy;
import com.example.potapp.Strategy.CustomModeStrategy;
import com.example.potapp.Strategy.Light;
import com.example.potapp.Strategy.SmartModeStrategy;
import com.example.potapp.Strategy_watering.AutoWateringModeStrategy;
import com.example.potapp.Strategy_watering.CustomWateringModeStrategy;
import com.example.potapp.Strategy_watering.Watering;
import com.example.potapp.Validation;
import com.example.potapp.entity.Pot;
import com.example.potapp.factory.CustomDialog;
import com.example.potapp.factory.CustomDialogFactory;
import com.example.potapp.factory.OneBtnInputDialogFactory;
import com.example.potapp.factory.OneBtnMsgDialogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pot_set#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pot_set extends Fragment {

    Button autoLightBtn;
    Button smartLightBtn;
    private Button customLightBtn;
    private Button autoWaterBtn, customWaterBtn;

    private TextView CurWateringMode;
    private TextView CurLightMode;
    private TextView showWateringTime;
    private TextView showLightTime;
    private Spinner spinner;
    private Light light;
    private Watering watering;
    Calendar calendar,calendar2;
    SimpleDateFormat simpleDateFormat;
    String dataTime,dataTime2,enteredTime,smartMode;
    int selectedTime;

    int hour, minute;
    String choice;



    int position = MainActivity.potPosition;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pot_set() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pot_set.
     */
    // TODO: Rename and change types and number of parameters
    public static pot_set newInstance(String param1, String param2) {
        pot_set fragment = new pot_set();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pot_set, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ---------------- findViewById ----------------
        autoLightBtn = (Button) view.findViewById(R.id.autoLightBtn);
        customLightBtn = (Button) view.findViewById(R.id.customLightBtn);
        smartLightBtn = (Button) view.findViewById(R.id.smartLightBtn);
        CurLightMode = (TextView) view.findViewById(R.id.showLightMode);
        CurWateringMode = (TextView) view.findViewById(R.id.showWaterMode);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        showWateringTime = (TextView) view.findViewById(R.id.showWaterTime);
        showLightTime = (TextView) view.findViewById(R.id.showLightTime);
        autoWaterBtn = (Button) view.findViewById(R.id.autoWaterBtn);
        customWaterBtn = (Button) view.findViewById(R.id.customWaterBtn);

        // ---------------- setListener ----------------
        autoLightBtn.setOnClickListener(autoLightBtnEvent);
        customLightBtn.setOnClickListener(customLightBtnEvent);
        smartLightBtn.setOnClickListener(smartLightBtnEvent);
        CurLightMode.setOnClickListener(autoLightBtnEvent);
        CurLightMode.setOnClickListener(customLightBtnEvent);
        CurLightMode.setOnClickListener(smartLightBtnEvent);
        autoWaterBtn.setOnClickListener(autoWaterBtnEvent);
        customWaterBtn.setOnClickListener(customWaterBtnEvent);

        // ---------------- spinner ----------------

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.spinnerValue, R.layout.spinner_selected_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice = adapterView.getItemAtPosition(i).toString();
                changePlantType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        init();
    }

    private void init() {
        String myString;
        String plantType = PotManager.getPlantType();
        if (plantType == null) {
            myString = "無";
        } else {
            switch (PotManager.getPlantType()) {
                case "chlorophytum":
                    myString = "金邊吊蘭";
                    break;
                case "fern":
                    myString = "蕨類";
                    break;
                case "cactus":
                    myString = "仙人掌";
                    break;
                case "others":
                    myString = "其他";
                    break;
                default:
                    myString = "無";
                    break;
            }
        }

        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); // cast to an ArrayAdapter
        int spinnerPosition = myAdap.getPosition(myString);
        // set the default according to value
        spinner.setSelection(spinnerPosition);
        if (PotManager.getWateringMode().equals("custom")) {
            CurWateringMode.setText("自訂");
        } else {
            CurWateringMode.setText("自動");
        }

        if (PotManager.getWateringStartTime() == null) {
            showWateringTime.setText("全時段");
        } else {
            showWateringTime.setText(PotManager.getWateringStartTime() + "-" + PotManager.getWateringEndTime());
        }

        if (PotManager.getLightMode().equals("custom")) {
            CurLightMode.setText("自訂");
        } else if (PotManager.getLightMode().equals("smart")) {
            CurLightMode.setText("智慧");
        } else {
            CurLightMode.setText("自動");
        }

        if (PotManager.getLightStartTime() == null) {
            showLightTime.setText("無");
        } else {
            showLightTime.setText(PotManager.getLightStartTime() + "-" + PotManager.getLightEndTime());
        }
    }

    private void changePlantType() {
        // 金邊吊蘭: chlorophytum, 蕨類: fern, 仙人掌: cactus, 其他: others
        switch (choice) {
            case "金邊吊蘭":
                PotManager.setPlantType("chlorophytum");
                break;
            case "蕨類":
                PotManager.setPlantType("fern");
                break;
            case "仙人掌":
                PotManager.setPlantType("cactus");
                break;
            case "其他":
                PotManager.setPlantType("others");
                break;
            default:
                PotManager.setPlantType(null);
                break;
        }
        RetrofitAPI retrofitAPI = RetrofitManger.getInstance().getAPI();
        Call<Pot> call = retrofitAPI.changePlantType(PotManager.getPot());
        call.enqueue(new Callback<Pot>() {
            @Override
            public void onResponse(Call<Pot> call, Response<Pot> response) {

            }

            @Override
            public void onFailure(Call<Pot> call, Throwable t) {

            }
        });
    }

    final private View.OnClickListener autoWaterBtnEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            watering= new Watering(CurWateringMode, showWateringTime);
            watering.setWateringModeStrategy(new AutoWateringModeStrategy());
            watering.changeWateringMode();
        }
    };

    final private View.OnClickListener customWaterBtnEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            calendar= Calendar.getInstance();
            calendar2= Calendar.getInstance();

            // ---------------- timepicker ----------------
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    simpleDateFormat = new SimpleDateFormat("HH:mm");
                    calendar.set(Calendar.HOUR_OF_DAY,hour);
                    calendar.set(Calendar.MINUTE,minute);

                    dataTime=simpleDateFormat.format(calendar.getTime());

                    watering= new Watering(CurWateringMode, showWateringTime);
                    watering.setWateringModeStrategy(new CustomWateringModeStrategy(calendar, selectedTime));
                    watering.changeWateringMode();
                }

            };

            int style = AlertDialog.THEME_HOLO_LIGHT;
            TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), style, onTimeSetListener, hour, minute, true);
            timePickerDialog.setTitle("選擇起始時間");

            // factory
            CustomDialogFactory dialogFactory = new OneBtnInputDialogFactory(view.getContext(), "時長設定", "時長(小時): ", "確定");
            CustomDialog timeSelectDialog = dialogFactory.createCustomDialog();

            CustomDialogFactory dialogFactory2 = new OneBtnMsgDialogFactory(view.getContext(), "訊息", "請輸入1-12之間的數字!", "好");
            CustomDialog timeErrorDialog = dialogFactory2.createCustomDialog();

            timeSelectDialog.showDialog();
            EditText editText = timeSelectDialog.findViewById(R.id.editText);
            editText.setHint("1-12");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            timeSelectDialog.findViewById(R.id.positiveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = timeSelectDialog.findViewById(R.id.editText);
                    enteredTime = editText.getText().toString();

                    if (enteredTime.isEmpty()) {
                        Toast.makeText(view.getContext(), "未輸入時長", Toast.LENGTH_SHORT).show();
                    } else {
                        timeSelectDialog.dismiss();

                        selectedTime = Integer.parseInt(enteredTime);

                        if (Validation.checkTime(selectedTime)) {
                            timePickerDialog.show();
                        } else {
                            timeErrorDialog.showDialog();
                        }
                    }
                }
            });
        }
    };

    final private View.OnClickListener autoLightBtnEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            light = new Light(CurLightMode,showLightTime);
            light.setLightModeStrategy(new AutoModeStrategy());
            light.changeLightMode();

        }
    };


    final private View.OnClickListener customLightBtnEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            calendar = Calendar.getInstance();
            calendar2 = Calendar.getInstance();

            // ---------------- timepicker ----------------
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    simpleDateFormat = new SimpleDateFormat("HH:mm");
                    calendar.set(Calendar.HOUR_OF_DAY,hour);
                    calendar.set(Calendar.MINUTE,minute);

                    dataTime = simpleDateFormat.format(calendar.getTime());

                    light = new Light(CurLightMode,showLightTime);
                    light.setLightModeStrategy(new CustomModeStrategy(calendar,selectedTime));
                    light.changeLightMode();
                }

            };

            int style = AlertDialog.THEME_HOLO_LIGHT;
            TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), style, onTimeSetListener, hour, minute, true);
            timePickerDialog.setTitle("選擇起始時間");

            // factory
            CustomDialogFactory dialogFactory = new OneBtnInputDialogFactory(view.getContext(), "時長設定", "時長(小時): ", "確定");
            CustomDialog timeSelectDialog = dialogFactory.createCustomDialog();

            CustomDialogFactory dialogFactory2 = new OneBtnMsgDialogFactory(view.getContext(), "訊息", "請輸入1-12之間的數字!", "好");
            CustomDialog timeErrorDialog = dialogFactory2.createCustomDialog();

            timeSelectDialog.showDialog();
            EditText editText = timeSelectDialog.findViewById(R.id.editText);
            editText.setHint("1-12");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            timeSelectDialog.findViewById(R.id.positiveButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = timeSelectDialog.findViewById(R.id.editText);
                    enteredTime = editText.getText().toString();

                    if (enteredTime.isEmpty()) {
                        Toast.makeText(view.getContext(), "未輸入時長", Toast.LENGTH_SHORT).show();
                    } else {
                        timeSelectDialog.dismiss();

                        selectedTime = Integer.parseInt(enteredTime);

                        if (Validation.checkTime(selectedTime)) {
                            timePickerDialog.show();
                        } else {
                            timeErrorDialog.showDialog();
                        }
                    }
                }
            });
        }
    };

    final private View.OnClickListener smartLightBtnEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // factory
            CustomDialogFactory dialogFactory = new OneBtnMsgDialogFactory(view.getContext(), "訊息", "請先設定植物種類!", "我知道了");
            CustomDialog nullDialog = dialogFactory.createCustomDialog();

            CustomDialogFactory dialogFactory2 = new OneBtnMsgDialogFactory(view.getContext(), "訊息", "無法辨識植物種類!", "確定");
            CustomDialog otherDialog = dialogFactory2.createCustomDialog();

            if( choice.equals("無")){
                nullDialog.showDialog();
            }
            else if(choice.equals("其他")){
                otherDialog.showDialog();
            }
            else {

                light=new Light(CurLightMode,showLightTime);
                light.setLightModeStrategy(new SmartModeStrategy(choice));
                light.changeLightMode();

            }

        }
    };


}