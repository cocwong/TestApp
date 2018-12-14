package com.test.testapp;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class WatchDogService extends Service {
    private boolean enableWatch;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        watch();
        return super.onStartCommand(intent, flags, startId);
    }

    private void watch() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        while (enableWatch) {

        }
    }
}
