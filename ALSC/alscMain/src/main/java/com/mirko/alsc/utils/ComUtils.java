package com.mirko.alsc.utils;

import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.cache.CacheManager;

/**
 * Created by Mirko on 2019/12/26 20:49.
 */
public class ComUtils {

    private static final String TAG = "ComUtils";

    /**
     * 获取token缓存
     * @return
     */
    public static String getTokenCache(){
        if(CacheManager.LoginToken.get()!=null){
            return CacheManager.LoginToken.get();
        }
        return null;
    }


    /**
     * 获取sid
     *
     * @return
     */
    public static String getSid() {
        if (CacheManager.PicSid.get() != null) {
            return CacheManager.PicSid.get();
        }
        return null;
    }

    /**
     * 获取用户缓存
     *
     * @return
     */
    public static UserInfoResult getUserInfo() {
        if (CacheManager.UserInfoResult.get() != null) {
            return CacheManager.UserInfoResult.get();
        }
        return null;
    }


}
