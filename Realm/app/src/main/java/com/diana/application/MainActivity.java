package com.diana.application;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.diana.application.data.di.component.DaggerIApplicationComponent;
import com.diana.application.data.di.component.IApplicationComponent;
import com.diana.application.data.di.module.NetworkModule;
import com.diana.application.data.di.module.RealmModule;
import com.diana.application.data.utils.Constant;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;

public class MainActivity extends Application {

    private IApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        Realm.init(this);
        initApplicationComponent();
    }


    private void initApplicationComponent() {

        applicationComponent = DaggerIApplicationComponent
                .builder()
                .realmModule(new RealmModule(false))
                .networkModule(new NetworkModule(Constant.BASE_URL))
                .build();
    }

    public IApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
