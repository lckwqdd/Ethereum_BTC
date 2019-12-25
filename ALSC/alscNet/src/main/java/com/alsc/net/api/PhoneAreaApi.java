package com.alsc.net.api;

import com.alsc.net.bean.entity.PhoneCodeResultEntity;
import com.alsc.net.bean.entity.PicCodeResultEntity;
import com.alsc.net.retrofit.api.BaseApi;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.net.util.HttpService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 获取手机区号列表
 */

public class PhoneAreaApi extends BaseApi {

    public PhoneAreaApi(HttpOnNextListener<PhoneCodeResultEntity> listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setShowProgress(true);
        setCancel(true);
        setCache(false);
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.getPhoneCode();

    }
}
