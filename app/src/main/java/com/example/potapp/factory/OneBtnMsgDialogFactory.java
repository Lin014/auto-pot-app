package com.example.potapp.factory;

import android.content.Context;

public class OneBtnMsgDialogFactory implements CustomDialogFactory {

    private Context context;
    private String title;
    private String message;
    private String positiveBtnText;

    public OneBtnMsgDialogFactory(Context context, String title, String message, String positiveBtnText) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.positiveBtnText = positiveBtnText;
    }

    @Override
    public CustomDialog createCustomDialog() {
        return new OneBtnMsgDialog(context, title, message, positiveBtnText);
    }
}
