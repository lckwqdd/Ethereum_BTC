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
 * 更新logo
 *
 */

public class UpdateLogoApi extends BaseApi {

    private String token;
    private String logo;

    public UpdateLogoApi(HttpOnNextListener<EmptyResultEntity> listener, RxAppCompatActivity rxAppCompatActivity,String token, String logo) {
        super(listener, rxAppCompatActivity);
        this.token = token;
        this.logo = logo;
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
            result.put("logo", logo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.updateLogo(body);

    }
}
