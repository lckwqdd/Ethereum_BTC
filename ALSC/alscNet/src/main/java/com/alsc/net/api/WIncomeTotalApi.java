package com.alsc.net.api;

import com.alsc.net.bean.entity.IncomeTotalResultEntity;
import com.alsc.net.bean.request.ListPageRequest;
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
 * 收益总额
 **请求参数**

 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 | amount | float | 否       | 金额 |
 | receive_url | String | 否       | 接收地址 |
 | send_url | String | 否       | 发送地址 |
 | price | float | 否       | 实时价格 |
 *
 */

public class WIncomeTotalApi extends BaseApi {

    private ListPageRequest request;

    public WIncomeTotalApi(HttpOnNextListener<IncomeTotalResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, ListPageRequest request) {
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
            result.put("page_size", request.getPage_size());
            result.put("page_index", request.getPage_index());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.wIncomeTotal(body);

    }
}
