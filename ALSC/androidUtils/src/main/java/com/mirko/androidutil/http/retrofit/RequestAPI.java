package com.mirko.androidutil.http.retrofit;

/**
 * @author sam
 * @version 1.0
 * @date 2018/3/20
 */
public class RequestAPI<T> extends BaseAPI {
    private T t;
    private static RequestAPI mRequestAPI;

    /** 
     * 单例模式，得到RequestAPI的实例
     * @date   2019/3/21
     * @version 1.0
     * @param  * @param null
     * @return  
     */ 
    public static synchronized RequestAPI getInstance() {
        if (mRequestAPI == null) {
            mRequestAPI = new RequestAPI();
        }
        return mRequestAPI;
    }

    /** 
     * 得到API请求接口类
     * @date   2019/3/21
     * @version 1.0
     * @param  * @Class 传入工程接口类
     * @return  
     */ 
    public T getApi(Class apiInterfaceClazz) {
        if (t == null) {
            t = (T) RetrofitManager.getInstance().getRetrofit().create(apiInterfaceClazz);
        }
        return t;
    }
}
