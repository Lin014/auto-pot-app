package com.example.potapp.factory;

import android.app.Dialog;
import android.content.Context;

public abstract class CustomDialog extends Dialog {

    protected Context context;
    protected String title;

    public CustomDialog(Context context, String title) {
        super(context);
        this.context = context;
        this.title = title;
    }

    public abstract void showDialog();
}
