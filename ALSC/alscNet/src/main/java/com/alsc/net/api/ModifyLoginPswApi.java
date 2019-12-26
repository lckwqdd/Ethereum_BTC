package com.alsc.net.api;

import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.LoginResultEntity;
import com.alsc.net.bean.request.LoginRequest;
import com.alsc.net.bean.request.ModifyPswRequest;
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
 * 修改登录密码
 *
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 | old_pwd | String | 否       | 旧密码 |
 | new_pwd | String | 否       | 新密码 |
 | code | String | 否       | 验证码 |
 | type | int | 否       | 类型 1手机验证  2邮箱验证 |
 *
 */

public class ModifyLoginPswApi extends BaseApi {

    private ModifyPswRequest request;

    public ModifyLoginPswApi(HttpOnNextListener<EmptyResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, ModifyPswRequest request) {
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
            result.put("old_pwd", request.getOld_pwd());
            result.put("new_pwd", request.getNew_pwd());
            result.put("code", request.getCode());
            result.put("type", request.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.modifyLoginPsw(body);

    }
}
