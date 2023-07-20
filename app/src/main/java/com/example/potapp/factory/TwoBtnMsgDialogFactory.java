package com.example.potapp.factory;

import android.content.Context;

public class TwoBtnMsgDialogFactory implements CustomDialogFactory {

    private Context context;
    private String title;
    private String message;
    private String positiveBtnText;
    private String negativeBtnText;

    public TwoBtnMsgDialogFactory(Context context, String title, String message, String positiveBtnText, String negativeBtnText) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.positiveBtnText = positiveBtnText;
        this.negativeBtnText = negativeBtnText;
    }

    @Override
    public CustomDialog createCustomDialog() {
        return new TwoBtnMsgDialog(context, title, message, positiveBtnText, negativeBtnText);
    }
}
