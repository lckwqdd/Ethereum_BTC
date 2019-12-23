package com.alsc.net;

import android.content.Context;

import com.mirko.androidutil.BaseApplication;

import java.io.File;

/**
 * Created by Mirko on 2018/3/15.
 *
 * 网络相关初始化
 *
 */

public class NetApplication extends BaseApplication {

    public static NetApplication application;
    private String  url;

    public static NetApplication getInstance(){

        if (application == null){
            synchronized (NetApplication.class){
                if (application == null){
                    application = new NetApplication();
                }
            }
        }
        return application;
    }


    public  void init(String url){
        this.url = url;
    }

    public static Context getContext() {
        return mContext;
    }

    public String getUrl() {
        return url;
    }

    /**
     * 获取缓存路径
     * @return
     */
    public File getCacheDir(){
        return mContext.getCacheDir();
    }
}
