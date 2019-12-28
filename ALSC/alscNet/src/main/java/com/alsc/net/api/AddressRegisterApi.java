package com.alsc.net.api;

import com.alsc.net.bean.entity.AddressRegisterResultEntity;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.request.AddressRegisterRequest;
import com.alsc.net.bean.request.BindEmailRequest;
import com.alsc.net.retrofit.api.BaseApi;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.net.util.HttpService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;


public class AddressRegisterApi extends BaseApi {

    private AddressRegisterRequest request;

    public AddressRegisterApi(HttpOnNextListener<AddressRegisterResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, AddressRegisterRequest request) {
        super(listener, rxAppCompatActivity);
        this.request = request;
        setShowProgress(true);
        setCancel(true);
        setCache(false);
        setCookieNetWorkTime(60);
        setCookieNoNetWorkTime(24 * 60 * 60);
    }

    @Override
    public Observable getObservable(HttpService methods) {
        JSONObject result = new JSONObject();
        try {
            result.put("address", request.getAddress());
            result.put("symbol", request.getSymbol());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.registerAddress(body);
    }
}
