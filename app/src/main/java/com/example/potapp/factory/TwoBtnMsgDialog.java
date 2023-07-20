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

public class TwoBtnMsgDialog extends CustomDialog {

    private String message;
    private String positiveBtnText;
    private String negativeBtnText;

    public TwoBtnMsgDialog(Context context, String title, String message, String positiveBtnText, String negativeBtnText) {
        super(context, title);
        this.message = message;
        this.positiveBtnText = positiveBtnText;
        this.negativeBtnText = negativeBtnText;
    }

    @Override
    public void showDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_active, null);
        setContentView(view);

        TextView titleView = view.findViewById(R.id.title);
        TextView messageView = view.findViewById(R.id.message);
        Button positiveButton = view.findViewById(R.id.positiveButton);
        Button negativeButton = view.findViewById(R.id.negativeButton);

        titleView.setText(title);
        messageView.setText(message);
        positiveButton.setText(positiveBtnText);
        negativeButton.setText(negativeBtnText);


        show();

        DisplayMetrics dm = new DisplayMetrics();//取得螢幕解析度
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);//取得螢幕寬度值
        getWindow().setLayout(dm.widthPixels-230, ViewGroup.LayoutParams.WRAP_CONTENT);//設置螢幕寬度值
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//將原生AlertDialog的背景設為透明

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
