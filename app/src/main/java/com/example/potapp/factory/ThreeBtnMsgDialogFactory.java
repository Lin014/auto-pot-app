package com.example.potapp.factory;

import android.content.Context;

public class ThreeBtnMsgDialogFactory implements CustomDialogFactory {

    private Context context;
    private String title;
    private String message;
    private String positiveBtnText;
    private String negativeBtnText;
    private String neutralBtnText;

    public ThreeBtnMsgDialogFactory(Context context, String title, String message, String positiveBtnText, String negativeBtnText, String neutralBtnText) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.positiveBtnText = positiveBtnText;
        this.negativeBtnText = negativeBtnText;
        this.neutralBtnText = neutralBtnText;
    }

    @Override
    public CustomDialog createCustomDialog() {
        return new ThreeBtnMsgDialog(context, title, message, positiveBtnText, negativeBtnText, neutralBtnText);
    }
}
