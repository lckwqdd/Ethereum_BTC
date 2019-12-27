package com.alsc.net.api;

import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.bean.entity.NoticeResultEntity;
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
 * 首页信息
 *
 */

public class HomeMsgApi extends BaseApi {

    private String token;

    public HomeMsgApi(HttpOnNextListener<HomeMsgResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, String token) {
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
        return methods.getHomeMsg(body);

    }
}
