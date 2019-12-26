package com.mirko.alsc;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alsc.net.NetApplication;
import com.alsc.net.db.GreenDaoUtil;
import com.alsc.wallet.utils.AppFilePath;
import com.mirko.androidutil.BaseApplication;
import com.mirko.androidutil.utils.FileUtils;
import com.mirko.androidutil.utils.MetaDataUtil;
import com.mirko.androidutil.utils.android.Application;

import io.realm.Realm;

public class MirkoApplication extends BaseApplication {

    public  static String HOST;
    private final static String ALSC_API = "ALSC_API";

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initData();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

    private void initData(){
        try {
            //获取API地址
            HOST = MetaDataUtil.getMetaDataValue(mContext, ALSC_API);
        } catch (Exception e) {
            Application.exit(mContext);
        }
        NetApplication.getInstance().init(HOST);
        Realm.init(this);
        AppFilePath.init(this);
        GreenDaoUtil.getInstance().initGreenDao(this);
        FileUtils.getInstance().init(this);
    }
}
