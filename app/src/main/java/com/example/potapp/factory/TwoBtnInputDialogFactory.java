package com.example.potapp.factory;

import android.content.Context;

public class TwoBtnInputDialogFactory implements CustomDialogFactory {

    private Context context;
    private String title;
    private String editTextViewText;
    private String positiveBtnText;
    private String negativeBtnText;

    public TwoBtnInputDialogFactory(Context context, String title, String editTextViewText, String positiveBtnText, String negativeBtnText) {
        this.context = context;
        this.title = title;
        this.editTextViewText = editTextViewText;
        this.positiveBtnText = positiveBtnText;
        this.negativeBtnText = negativeBtnText;
    }

    @Override
    public CustomDialog createCustomDialog() {
        return new TwoBtnInputDialog(context, title, editTextViewText, positiveBtnText, negativeBtnText);
    }
}
