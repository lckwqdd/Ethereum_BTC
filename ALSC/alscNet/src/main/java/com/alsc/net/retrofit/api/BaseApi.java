package com.alsc.net.retrofit.api;

import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.utils.android.SharedPreferencesUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.alsc.net.NetApplication;
import com.alsc.net.retrofit.entity.BaseResultEntity;
import com.alsc.net.retrofit.exception.HttpTimeException;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.net.util.HttpService;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.SoftReference;

import rx.Observable;
import rx.exceptions.CompositeException;
import rx.functions.Func1;

/**
 * Created by Mirko on 2016/11/30.
 *
 */

public abstract class BaseApi<T> implements Func1<BaseResultEntity<T>, T> {

    public static final String TYPE_PUTUAN = "putuan/"; ////访问蒲团
    public static final String TYPE_DEVICES = "devices/";////访问设备

    //rx生命周期管理
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*回调*/
    private HttpOnNextListener listener;
    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理*/
    private boolean cache;
    /*基础url*/
    private String baseUrl;
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String mothed;
    /*超时时间-默认6秒*/
    private int connectionTime = 120;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;

    private int status;

    public BaseApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        setListener(listener);
        setRxAppCompatActivity(rxAppCompatActivity);
        setShowProgress(true);
        setCache(true);
        if (NetApplication.getInstance().getUrl() != null) {
            setBaseUrl(NetApplication.getInstance().getUrl());
            baseUrl += TYPE_PUTUAN;
        } else {
            setBaseUrl("https://futon.pureprac.com/");
        }
    }

    /**
     *
     * @param listener
     * @param rxAppCompatActivity
     * @param serviceType 访问的服务类型， 默认是蒲团服务
     */
    public BaseApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, String serviceType) {
        setListener(listener);
        setRxAppCompatActivity(rxAppCompatActivity);
        setShowProgress(true);
        setCache(true);
        if (NetApplication.getInstance().getUrl() != null) {
            setBaseUrl(NetApplication.getInstance().getUrl());
            baseUrl += serviceType;
        } else {
            setBaseUrl("https://futon.pureprac.com/");
        }
    }

    /**
     * 设置参数
     *
     * @param methods
     * @return
     */
    public abstract Observable getObservable(HttpService methods);

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public int getStatus() {
        return status;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public String getMothed() {
        return mothed;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public void setMothed(String mothed) {
        this.mothed = mothed;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUrl() {
        return baseUrl + mothed;
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference(rxAppCompatActivity);
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public HttpOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpOnNextListener listener) {
        this.listener = listener;
    }

    /*
     * 获取当前rx生命周期F
     * @return
     */
    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity.get();
    }

    @Override
    public T call(BaseResultEntity<T> httpResult) {

//        LogUtils.d("SyncData", "API返回数据：" + httpResult.getStatusCode());
//        LogUtils.d("SyncData", "isLoginToLogout：" + SharedPreferencesUtils.getInstance().getBoolean("isLoginToLogout", true));
//        if (httpResult.getStatusCode() == 3007 && SharedPreferencesUtils.getInstance().getBoolean("isLoginToLogout", true)
//                || httpResult.getStatusCode() == 3005) { //跳转到登录页面
//            SharedPreferencesUtils.getInstance().putBoolean("isLoginToLogout", false);
//            EventBus.getDefault().post(new LoginOutEvent());
//        }
        status = httpResult.getStatusCode();
        Object object = null;
        try {
            object = httpResult.getData();
        } catch (CompositeException e) {
            object = null;
        }

        if (httpResult.getStatusCode() != 3000) {
            if(object != null) {
                throw new HttpTimeException(httpResult.getMsg(), httpResult.getData());
            } else {
                throw new HttpTimeException(httpResult.getMsg());
            }

        }
        return httpResult.getData();
    }
}