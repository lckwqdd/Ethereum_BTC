package com.alsc.net.cache;

import com.mirko.androidutil.utils.android.SharedPreferencesUtils;

import java.io.NotSerializableException;
import java.util.HashMap;


/**
 * App 缓存管理器
 */
public class CacheManager<T> {

    private String name;

    private CacheType cacheType;
    private String cachePath = "/PurePrac/";


    public static HashMap<String, Object> applicationContextParams = new HashMap<String, Object>();

    /**
     * <b>缓存类型有三种</b>                  <br />
     * ApplicaiontContext   内存         <br />
     * DataBase             数据库缓存   <br />
     * ObjectStream         对象流缓存   <br />
     */
    private static enum CacheType {
        ApplicaiontContext, DataBase, ObjectStream, SharedPreferences
    }

    /**
     * @param cacheType 缓存类型
     * @param name      缓存名字 对象流缓存中是文件名
     */
    private CacheManager(CacheType cacheType, String name) {
        this.cacheType = cacheType;
        this.name = name;
    }

    /**
     * 登录的TOOKEN
     */
    public static CacheManager<String> LoginToken = new CacheManager<String>(CacheType.ObjectStream, "LoginToken");




    /**
     * 获取缓存对象
     *
     * @return
     */
    public T get() {
        if (CacheType.ApplicaiontContext.equals(cacheType)) {
            return (T) applicationContextParams.get(name);
        } else if (CacheType.SharedPreferences.equals(cacheType)) {
            T t = (T) SharedPreferencesUtils.getInstance().getString(name);
            if (t != null) {
                return t;
            }
        } else if (CacheType.DataBase.equals(cacheType)) {
        } else if (CacheType.ObjectStream.equals(cacheType)) {
            try {
                if(name != null && name.equals("CacheHistoryCount")) {
                    if(ObjectStreamIO.input(cachePath, name) != null && ObjectStreamIO.input(cachePath, name) instanceof Integer) {
                        long val = (long)((Integer)(ObjectStreamIO.input(cachePath, name))).intValue();
                        return (T)Long.valueOf(val);
                    }
                }
                return (T) ObjectStreamIO.input(cachePath, name);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 获取缓存对象，为空或出错时返回默认值
     *
     * @param defaultValue 默认值
     * @return
     */
    public T get(T defaultValue) {
        if (CacheType.ApplicaiontContext.equals(cacheType)) {

            T t = (T) applicationContextParams.get(name);

            if (t != null) {
                return t;
            }

        } else if (CacheType.SharedPreferences.equals(cacheType)) {

            T t = (T) SharedPreferencesUtils.getInstance().getString(name);

            if (t != null) {
                return t;
            }

        } else if (CacheType.DataBase.equals(cacheType)) {

        } else if (CacheType.ObjectStream.equals(cacheType)) {

            try {
                if(name != null && name.equals("CacheHistoryCount")) {
                    if(ObjectStreamIO.input(cachePath, name) != null && ObjectStreamIO.input(cachePath, name) instanceof Integer) {
                        long val = (long)((Integer)(ObjectStreamIO.input(cachePath, name))).intValue();
                        return (T)Long.valueOf(val);
                    }
                }
                T t = (T) ObjectStreamIO.input(cachePath, name);

                if (t != null) {
                    return t;
                }

            } catch (Exception e) {
            }

        }
        return defaultValue;
    }

    /**
     * 存储缓存，如果为空则删除已有缓存
     *
     * @param cacheObj
     * @return
     */
    public boolean set(T cacheObj) {
        if (CacheType.ApplicaiontContext.equals(cacheType)) {
            applicationContextParams.put(name, cacheObj);
        } else if (CacheType.SharedPreferences.equals(cacheType)) {
            SharedPreferencesUtils.getInstance().putString(name, cacheObj + "");
        } else if (CacheType.DataBase.equals(cacheType)) {

        } else if (CacheType.ObjectStream.equals(cacheType)) {
            try {
                if (cacheObj == null) {
                    ObjectStreamIO.remove(cachePath, name);
                } else {
                    ObjectStreamIO.output(cachePath, cacheObj, name);
                }
            } catch (NotSerializableException e) {
//				LogUtils.d("test","ObjectStream错误1:"+e.toString());
            } catch (Exception e) {
//				LogUtils.d("test","ObjectStream错误2:"+e.toString());
            }
        }
        return true;
    }

}
