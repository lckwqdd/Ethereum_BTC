package com.mirko.androidutil;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * 常用工具类+ retrofit + Rxjava + Glide + GreenDao + MVP +
 *
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication application = null;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        application = this;
        init();
    }

    public static BaseApplication getBaseApplication(){
        return application;
    }
    
    /**
     * 得到Application环境变量
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化话app信息
     */
    private void init() {
    }

    /**
     * 退出app
     */
    public void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    
    /**
     * 退出app
     */
    public void exitAppAll() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
    
}


