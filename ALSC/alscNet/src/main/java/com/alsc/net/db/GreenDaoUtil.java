package com.alsc.net.db;

import android.content.Context;
import com.alsc.net.db.bean.DaoMaster;
import com.alsc.net.db.bean.DaoSession;
import com.alsc.net.db.bean.IdCradBeanDao;

/**
 * Created by Administrator on 2019-12-22.
 */

public class GreenDaoUtil {

    private static GreenDaoUtil instance;
    private DaoMaster.DevOpenHelper openHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private String tabName="alsc.db";  //表名

    private GreenDaoUtil() {
    }

    public static GreenDaoUtil getInstance(){
        if (instance==null){
            synchronized (GreenDaoUtil.class){
                if (instance==null){
                    instance = new GreenDaoUtil();
                }
            }
        }
        return instance;
    }

    public void initGreenDao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, tabName);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public IdCradBeanDao getIdCardBeanDao(){
        return mDaoSession.getIdCradBeanDao();
    }
}
