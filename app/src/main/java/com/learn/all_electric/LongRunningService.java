package com.learn.all_electric;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class LongRunningService extends Service {
    private Binder mBinder = new BackBinder();
    private int time = 1000 * 3;
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class BackBinder extends Binder {

    }

    //登录成功后开启定时线程刷新更新token避免token过期


}
