package com.diana.application.data.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * Created by Diana on 26.06.2019.
 */

public class ToastImpl implements IToast {

    private final Context context;

    public ToastImpl(Context context) {
        this.context = context;
    }

    @Override
    public void showLongToast(String toast) {

        Toast toast1 = Toast.makeText(context, toast, Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();

    }

    @Override
    public void showShortToast(String toast) {

        Toast toast1 = Toast.makeText(context, toast, Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }
}
