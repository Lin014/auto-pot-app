package com.example.potapp.factory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.potapp.R;

public class OneBtnInputDialog extends CustomDialog {
    private String editTextViewText;
    private String positiveBtnText;

    public OneBtnInputDialog(Context context, String title, String editTextViewText, String positiveBtnText) {
        super(context, title);
        this.editTextViewText = editTextViewText;
        this.positiveBtnText = positiveBtnText;
    }

    @Override
    public void showDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        setContentView(view);

        TextView titleView = view.findViewById(R.id.title);
        TextView editTextView = view.findViewById(R.id.editTextView);
        Button positiveButton = view.findViewById(R.id.positiveButton);

        titleView.setText(title);
        editTextView.setText(editTextViewText);
        positiveButton.setText(positiveBtnText);

        show();

        DisplayMetrics dm = new DisplayMetrics();//取得螢幕解析度
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);//取得螢幕寬度值
        getWindow().setLayout(dm.widthPixels-230, ViewGroup.LayoutParams.WRAP_CONTENT);//設置螢幕寬度值
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//將原生AlertDialog的背景設為透明

    }
}
