package com.alsc.net.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.alsc.net.NetApplication;
import com.alsc.net.db.bean.DaoMaster;
import com.alsc.net.db.bean.DaoSession;
import com.alsc.net.retrofit.download.DownInfo;

/**
 * 数据库管理
 * Created by Mirko on 2016/12/7.
 */

public class DaoUtils {

    private static final String TAG = "DaoUtils";
    public static final String EXTRA_ACTION = "action";
    public static final String EXTRA_DATE = "date";
    public static final int ACTION_INSERT = 0;
    public static final int ACTION_UPDATE = 1;
    public static final int ACTION_DELETE = 2;

    private static volatile DaoUtils db = null;
    private final static String dbName = "alsc";  //数据库名称
    private MyDevOpenHelper openHelper;
    private Context context;

    private static DaoSession daoSession;

    private DaoUtils() {
        context= NetApplication.getInstance().getContext();
        openHelper = new MyDevOpenHelper(context, dbName, null);
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
//      deviceInfoDao = daoSession.getDeviceInfoDao();
    }

    /**
     * 获取单例
     * @return
     */
    public static DaoUtils getInstance() {
        if (db == null) {
            synchronized (DaoUtils.class) {
                if (db == null) {
                    db = new DaoUtils();
                }
            }
        }
        return db;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new MyDevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new MyDevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    public void update(DownInfo info){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
//        DownInfoDao downInfoDao = daoSession.getDownInfoDao();
//        downInfoDao.update(info);
    }



}
