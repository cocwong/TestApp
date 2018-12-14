package com.test.testapp;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.squareup.leakcanary.LeakCanary;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        if(!LeakCanary.isInAnalyzerProcess(this)){
//            LeakCanary.install(this);
//        }
        getBaseContext();
        getApplicationContext();
    }
}
