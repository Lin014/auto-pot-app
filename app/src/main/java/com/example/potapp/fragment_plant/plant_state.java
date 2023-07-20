package com.example.potapp.fragment_plant;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.potapp.ImageConvert;
import com.example.potapp.MainActivity;
import com.example.potapp.PotManager;
import com.example.potapp.R;
import com.example.potapp.command.ChangePlantImgCommand;
import com.example.potapp.command.ChangePlantNameCommand;
import com.example.potapp.command.ChangePotNameCommand;
import com.example.potapp.command.Command;
import com.example.potapp.command.Constant;
import com.example.potapp.command.PotInvoker;
import com.example.potapp.command.PotReceiver;
import com.example.potapp.entity.Data;
import com.example.potapp.entity.Pot;
import com.example.potapp.observer.DataSubject;
import com.example.potapp.observer.Observer;
import com.example.potapp.observer.PotSubject;
import com.example.potapp.observer.Subject;
import com.example.potapp.state.WaterLevelContext;
import com.example.potapp.state_light.LightContext;
import com.example.potapp.state_water_motor.WaterMotorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link plant_state#newInstance} factory method to
 * create an instance of this fragment.
 */
public class plant_state extends Fragment implements Observer {

    private TextView daysText, plantNameText;
    private ImageView plantImage;
    private TextView soilMoistureText, waterLevelStateText, waterMotorStateText, lightLuxText, lightStateText;
    private ImageButton editBtn;
    private TextView dataTimeText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public plant_state() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment plant_state.
     */
    // TODO: Rename and change types and number of parameters
    public static plant_state newInstance(String param1, String param2) {
        plant_state fragment = new plant_state();
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
        return inflater.inflate(R.layout.fragment_plant_state, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DataSubject.getInstance().addObserver(this);
        PotSubject.getInstance().addObserver(this);

        // ---------------- findViewById ----------------
        daysText = view.findViewById(R.id.txt_days);
        plantNameText = view.findViewById(R.id.plantName);
        plantImage = view.findViewById(R.id.potImage);
        soilMoistureText = view.findViewById(R.id.soilMoisture);
        waterLevelStateText = view.findViewById(R.id.waterLevelState);
        waterMotorStateText = view.findViewById(R.id.waterMotorState);
        lightLuxText = view.findViewById(R.id.lightLux);
        lightStateText = view.findViewById(R.id.lightState);
        editBtn = view.findViewById(R.id.editBtn);
        dataTimeText = view.findViewById(R.id.dataTimeTextView);
        // ---------------- setListener ----------------
        editBtn.setOnClickListener(editBtnEvent);

        plantNameText.setText(PotManager.getPlantName());
        plantImage.setImageBitmap(ImageConvert.ByteAryStringToBitmap(PotManager.getPlantImg()));
        daysText.setText(Integer.toString(daysBetween(PotManager.getPotCreateTime())));
    }

    private int daysBetween(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format.parse(date);
            Date nowDate = new Date();

            return (int) (( nowDate.getTime() - date1.getTime()) / (1000*3600*24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    final View.OnClickListener editBtnEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // command
            PotReceiver potReceiver = new PotReceiver();
            PotInvoker potInvoker = new PotInvoker();
            Command changePotNameCommand = new ChangePotNameCommand(potReceiver);
            Command changePlantNameCommand = new ChangePlantNameCommand(potReceiver);
            Command changePlantImgCommand = new ChangePlantImgCommand(potReceiver);
            potInvoker.addCommand(changePotNameCommand);
            potInvoker.addCommand(changePlantNameCommand);
            potInvoker.addCommand(changePlantImgCommand);

            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_edit_pot);

            ImageButton editPotNameBtn = dialog.findViewById(R.id.editPotNameBtn);
            ImageButton editPlantNameBtn = dialog.findViewById(R.id.editPlantNameBtn);
            ImageButton editPlantImgBtn = dialog.findViewById(R.id.editPlantImgBtn);
            Button closeBtn = dialog.findViewById(R.id.positiveButton);

            dialog.show();

            DisplayMetrics dm = new DisplayMetrics();//取得螢幕解析度
            ((AppCompatActivity) view.getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);//取得螢幕寬度值
            dialog.getWindow().setLayout(dm.widthPixels-230, ViewGroup.LayoutParams.WRAP_CONTENT);//設置螢幕寬度值
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//將原生AlertDialog的背景設為透明

            editPotNameBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    potInvoker.run(Constant.ChangePotName, MainActivity.potPosition, getContext());
                }
            });

            editPlantNameBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    potInvoker.run(Constant.ChangePlantName, MainActivity.potPosition, getContext());
                }
            });

            editPlantImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    potInvoker.run(Constant.ChangePlantImg, MainActivity.potPosition, getContext());
                }
            });

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        PotSubject.getInstance().removeObserver(this);
        DataSubject.getInstance().removeObserver(this);
    }

    public void updateWaterLevelState(String waterLevelState) {
        WaterLevelContext waterLevelContext = new WaterLevelContext();
        waterLevelStateText.setText(waterLevelContext.Handle(waterLevelState));
    }

    public void updateWaterMotorState(String waterMotorState) {
        WaterMotorContext waterMotorContext = new WaterMotorContext();
        waterMotorStateText.setText(waterMotorContext.Handle(waterMotorState));
    }

    public void updateLightState(String lightState) {
        LightContext lightContext = new LightContext();
        lightStateText.setText(lightContext.Handle(lightState));
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof DataSubject) {
            Data sujectData = DataSubject.getInstance().getData();
            System.out.println("getData_water_level_state: " + sujectData.getData_water_level_state());
            System.out.println("getData_water_motor_state: " + sujectData.getData_water_motor_state());
            System.out.println("getData_light_state: " + sujectData.getData_light_state());
            updateWaterLevelState(sujectData.getData_water_level_state());
            updateWaterMotorState(sujectData.getData_water_motor_state());
            updateLightState(sujectData.getData_light_state());
            soilMoistureText.setText(Integer.toString(sujectData.getData_soil_moisture()));
            lightLuxText.setText(Integer.toString(sujectData.getData_light_lux()));
            dataTimeText.setText(sujectData.getData_time());
            System.out.println("plant state: 更新Data成功");
        } else if (subject instanceof PotSubject) {
            Pot subjectPot = PotSubject.getInstance().getPot();
            plantImage.setImageBitmap(ImageConvert.ByteAryStringToBitmap(subjectPot.getPlant_img()));
            plantNameText.setText(subjectPot.getPlant_name());
        }

    }
}