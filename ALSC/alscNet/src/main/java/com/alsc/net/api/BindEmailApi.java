package com.alsc.net.api;

import com.alsc.net.bean.entity.LoginResultEntity;
import com.alsc.net.bean.request.BindEmailRequest;
import com.alsc.net.bean.request.LoginRequest;
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
 * 绑定邮箱
 *
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 | email | String | 否       | 邮箱 |
 | code | String | 否       | 验证码 |
 *
 */

public class BindEmailApi extends BaseApi {

    private BindEmailRequest request;

    public BindEmailApi(HttpOnNextListener<LoginResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, BindEmailRequest request) {
        super(listener, rxAppCompatActivity);
        this.request = request;
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
            result.put("token", request.getToken());
            result.put("email", request.getEmail());
            result.put("code", request.getCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.bindEmail(body);

    }
}
