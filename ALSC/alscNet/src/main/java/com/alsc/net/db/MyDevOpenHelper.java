package com.alsc.net.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.alsc.net.db.bean.DaoMaster;


/**
 * Created by Mirko on 2018/5/14.
 *
 * 修改数据库升级模块
 * 先将数据复制保存
 */

public class MyDevOpenHelper extends DaoMaster.DevOpenHelper{

    public MyDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

//    @SuppressWarnings("unchecked")
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据迁移模块
//        MigrationHelper.migrate(db,null);
    }

}
