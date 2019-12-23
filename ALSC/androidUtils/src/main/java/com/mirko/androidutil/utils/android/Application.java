package com.mirko.androidutil.utils.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;

public class Application {
	
	public static final int EXIT_APPLICATION = 0x0001;  
    
    private static Context mContext;
    public static  String currentVersion;
    public static  String androidMarketVersion;
    
    public static Boolean running = false;
    
    public Application(Context context){
        mContext = context;  
    }
    /**
     * 退出应用程序
     */
    public static void exit(Context context){
    	Intent startMain = new Intent(Intent.ACTION_MAIN); 
    	startMain.addCategory(Intent.CATEGORY_HOME); 
    	startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    	context.startActivity(startMain);
    	System.exit(0);
    }
    
    /**
     * 获取当前应用程序的版本号
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context){
    	try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			return 0;
		}
    }
    /**
     * 获取本地app版本
     * @return
     */
    public static String getAppVersion (Context context,Class clazz) {
    	String currentVersion;
		try {
			currentVersion = context.getPackageManager().getPackageInfo(clazz.getPackage().getName(), 0).versionName;
			Application.currentVersion = currentVersion;
			return currentVersion;
		} catch (NameNotFoundException e) {
			return "0.0";
		}
    }
    
    public static String getAppVersion(Context context) {
    	return getAppVersion(context,context.getPackageName());
    }
    
    /**
     * 获取本地app版本
     * @return
     */
    public static String getAppVersion (Context context , String packgeName) {
    	String currentVersion;
		try {
			currentVersion = context.getPackageManager().getPackageInfo(packgeName, 0).versionName;
			Application.currentVersion = currentVersion;
			return currentVersion;
		} catch (NameNotFoundException e) {
			return "0.0";
		}
    }
    
    /**
     * 跳转到android market更新
     * @return
     */
    public static void toAndroidMarket(Context context) {
//    	SkipPageUtil.openUrl(context, "market://details?id=com.honestwalker.kancart.activity");
    }
    
    public static int getAppLevel() {
		int sysVersion = Integer.parseInt(VERSION.SDK); 
		return sysVersion;
	}
    
    public static boolean isAppLevelHigherThan4_0() {
    	return getAppLevel() >= 14;
    }
    
}
