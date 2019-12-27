package com.alsc.net.api;

import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.request.BindEmailRequest;
import com.alsc.net.bean.request.FindPasswordRequest;
import com.alsc.net.retrofit.api.BaseApi;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.net.util.HttpService;
import com.mirko.androidutil.utils.StringUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * 找回密码
 *
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | email | String | 是       | 邮箱 |
 | mobile | String | 是       | 手机号 |
 | phone_code | int | 是       | 手机区号 |
 | code | String | 否       | 验证码 |
 | pwd | String | 否       | 密码 |
 | pwd2 | String | 否       | 密码2 |
 | sid | String | 否       | sessionid |
 *
 */

public class FindPasswordApi extends BaseApi {


    private FindPasswordRequest request;

    public FindPasswordApi(HttpOnNextListener<EmptyResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, FindPasswordRequest request) {
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
            result.put("email", request.getEmail());
            result.put("mobile", request.getMobile());
            result.put("phone_code", request.getPhone_code());
            result.put("code", request.getCode());
            result.put("pwd", request.getPwd());
            result.put("pwd2", request.getPwd2());
            result.put("sid", request.getSid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.findPassword(body);

    }


}
