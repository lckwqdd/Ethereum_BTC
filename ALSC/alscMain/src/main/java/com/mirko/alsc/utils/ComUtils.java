package com.mirko.alsc.utils;

import com.alsc.net.cache.CacheManager;

/**
 * Created by Mirko on 2019/12/26 20:49.
 */
public class ComUtils {

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

}
