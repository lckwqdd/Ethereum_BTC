package com.alsc.net.api;

import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.retrofit.api.BaseApi;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.net.util.HttpService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 获取手机验证码
 *
 */

public class MobileCodeApi extends BaseApi {

    private String phone;
    private String phoneCode;
    private String sid;

    public MobileCodeApi(HttpOnNextListener<EmptyResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, String phone,String phoneCode,String sid) {
        super(listener, rxAppCompatActivity);
        this.phone = phone;
        this.phoneCode = phoneCode;
        this.sid = sid;
        setShowProgress(true);
        setCancel(true);
        setCache(false);
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24*60*60);
    }

    @Override
    public Observable getObservable(HttpService methods) {
        JSONObject result = new JSONObject();
        try {
            result.put("phone", phone);
            result.put("phone_code", phoneCode);
            result.put("sid", sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.getMobileCode(body);

    }
}
