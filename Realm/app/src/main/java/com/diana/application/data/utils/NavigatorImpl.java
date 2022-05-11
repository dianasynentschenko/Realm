package com.diana.application.data.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.diana.application.R;

/**
 * Created by Diana on 26.06.2019.
 */

public class NavigatorImpl implements INavigator {

    private final Context context;
    private final FragmentManager fragmentManager;

    public NavigatorImpl(Context context) {
        this.context = context;
        this.fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
    }


    private void addToBackStack(Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commitAllowingStateLoss();
    }


    @Override
    public void openCategoryList() {


    }

    @Override
    public void openProductList(String category) {

    }

    @Override
    public void openProductDetails(long productId) {

    }
}
