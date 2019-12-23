package com.alsc.net.api;

import com.alsc.net.bean.entity.NoticeResultEntity;
import com.alsc.net.bean.request.LoginRequest;
import com.alsc.net.bean.request.RegisterRequest;
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
 * 登录
 *
 * | 字段  | 类型   | 是否可选 | 描述  |
 * | ----- | ------ | -------- | ----- |
 * | name | String | 否       | 账号 |
 * | password | String | 否       | 密码 |
 * | code | String | 否       | 验证码 |
 *
 */

public class LoginApi extends BaseApi {

    private LoginRequest loginRequest;

    public LoginApi(HttpOnNextListener<NoticeResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, LoginRequest loginRequest) {
        super(listener, rxAppCompatActivity);
        this.loginRequest = loginRequest;
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
            result.put("name", loginRequest.getName());
            result.put("password", loginRequest.getPassword());
            result.put("code", loginRequest.getCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.login(body);

    }
}
