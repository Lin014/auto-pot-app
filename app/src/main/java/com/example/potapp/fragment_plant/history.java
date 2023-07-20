package com.example.potapp.fragment_plant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.potapp.R;
import com.example.potapp.historyActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class history extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static TextView[] time_list = new TextView[4];
    public static TextView[] soil_list = new TextView[4];
    public static TextView[] light_list = new TextView[4];
    public static TextView[] water_list = new TextView[4];

    public static boolean instant_data = true, more_than_30 = false;

    private static int next = 0;

    public history() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment history.
     */
    // TODO: Rename and change types and number of parameters
    public static history newInstance(String param1, String param2) {
        history fragment = new history();
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setItem(view);
    }

    private void setItem(View view) {
        //Set the component that in the page.
        time_list[0] = view.findViewById(R.id.data_1_time);
        time_list[1] = view.findViewById(R.id.data_2_time);
        time_list[2] = view.findViewById(R.id.data_3_time);
        time_list[3] = view.findViewById(R.id.data_4_time);
        soil_list[0] = view.findViewById(R.id.data_1_soil);
        soil_list[1] = view.findViewById(R.id.data_2_soil);
        soil_list[2] = view.findViewById(R.id.data_3_soil);
        soil_list[3] = view.findViewById(R.id.data_4_soil);
        light_list[0] = view.findViewById(R.id.data_1_light);
        light_list[1] = view.findViewById(R.id.data_2_light);
        light_list[2] = view.findViewById(R.id.data_3_light);
        light_list[3] = view.findViewById(R.id.data_4_light);
        water_list[0] = view.findViewById(R.id.data_1_water);
        water_list[1] = view.findViewById(R.id.data_2_water);
        water_list[2] = view.findViewById(R.id.data_3_water);
        water_list[3] = view.findViewById(R.id.data_4_water);


        Button button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(btn1);
    }

    public static void run_getdata(String time,int soil,int light,int water) {
        //Show the latest data.
        time_list[next].setText(time);
        soil_list[next].setText("土壤濕度：" + soil);
        light_list[next].setText("光照度：" + light);
        water_list[next].setText("水量：" + water);
        next++;
        if(next >3){
            next = 0;
        }


    }

    private Button.OnClickListener btn1 = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            setButtonCustomDialog();
        }

    };
    private void setButtonCustomDialog() {
        historyActivity his = new historyActivity();
        his.setButtonCustomDialog(getActivity());
    }

}
