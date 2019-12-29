package com.alsc.net.api;

import com.alsc.net.bean.entity.CapitalResultEntity;
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
 *
 * 资产信息

 **请求参数**

 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 *
 */

public class WCapitalInfoApi extends BaseApi {

    private String token;

    public WCapitalInfoApi(HttpOnNextListener<CapitalResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, String token) {
        super(listener, rxAppCompatActivity);
        this.token = token;
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
            result.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.wCapitalInfo(body);

    }
}
