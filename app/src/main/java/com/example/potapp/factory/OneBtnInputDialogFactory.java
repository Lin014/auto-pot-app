package com.example.potapp.factory;

import android.content.Context;

public class OneBtnInputDialogFactory implements CustomDialogFactory {

    private Context context;
    private String title;
    private String editTextViewText;
    private String positiveBtnText;

    public OneBtnInputDialogFactory(Context context, String title, String editTextViewText, String positiveBtnText) {
        this.context = context;
        this.title = title;
        this.editTextViewText = editTextViewText;
        this.positiveBtnText = positiveBtnText;
    }

    @Override
    public CustomDialog createCustomDialog() {
        return new OneBtnInputDialog(context, title, editTextViewText, positiveBtnText);
    }
}
