package com.mirko.androidutil.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Created by tim on 4/17/15.
 */
public class MetaDataUtil {

    public static String getMetaDataValue(Context context, String name){
        String configValue = null;
        Bundle metaData = null;
        ApplicationInfo ai = null;

        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (null != ai) {
            metaData = ai.metaData;
        }
        if (null != metaData) {
            configValue = metaData.getString(name);
        }

        return configValue;
    }
}
