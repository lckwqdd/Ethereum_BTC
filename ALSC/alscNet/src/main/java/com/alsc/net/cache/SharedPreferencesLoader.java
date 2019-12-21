package com.alsc.net.cache;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesLoader {
	
	private static SharedPreferencesLoader instance;
	private static SharedPreferences sp;
	private static Context context;
	
	public static SharedPreferencesLoader getInstance(Context context) {
		SharedPreferencesLoader.context = context;
		instance = new SharedPreferencesLoader();
		sp = context.getSharedPreferences("KanCart", Context.MODE_MULTI_PROCESS);
		return instance;
	}
	
	/**
	 * 重载配置
	 */
	public static void reload() {
		sp = context.getSharedPreferences("KanCart", Context.MODE_MULTI_PROCESS);
	}
	
	public static void putString(String key , String value) {
		getInstance(context);    // 更新数据重新初始化是为了让多进程SharedPreferences更改数据后即时生效
		if(sp == null) {
			return;
		}
		sp.edit().putString(key, value).commit();
	}
	public static String getString(String key) {
		getInstance(context);
		if(sp == null){
			return "";
		}
		return sp.getString(key, "");
	}
	
	public static void putInt(String key , int value) {
		getInstance(context);
		if(sp == null) {
			return;
		}
		sp.edit().putInt(key, value).commit();
	}
	public static int getInt(String key) {
		getInstance(context);
		if(sp == null){
			return 0;
		}
		return sp.getInt(key, 0);
	}
	
	public static void putBoolean(String key , boolean value) {
		getInstance(context);
		if(sp == null) {
			return;
		}
		sp.edit().putBoolean(key, value).commit();
	}
	public static boolean getBoolean(String key) {
		getInstance(context);
		if(sp == null){
			return false;
		}
		return sp.getBoolean(key, false);
	}
	
}
