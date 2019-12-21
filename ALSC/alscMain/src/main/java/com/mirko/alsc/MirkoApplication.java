package com.mirko.alsc;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.mirko.androidutil.BaseApplication;

public class MirkoApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }
}
