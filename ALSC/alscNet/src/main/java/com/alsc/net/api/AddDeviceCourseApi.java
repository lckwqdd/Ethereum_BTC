package com.alsc.net.api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.alsc.net.bean.entity.EmptyCommonEntity;
import com.alsc.net.retrofit.api.BaseApi;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.net.util.HttpService;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/29.
 */

public class AddDeviceCourseApi extends BaseApi {
    private String deviceId;
    private String courseIds;
    public AddDeviceCourseApi(HttpOnNextListener<EmptyCommonEntity> listener, RxAppCompatActivity rxAppCompatActivity, String deviceId,String courseIds) {
        super(listener, rxAppCompatActivity);
        this.deviceId = deviceId;
        this.courseIds = courseIds;
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
            result.put("deviceId", deviceId);
            result.put("courseIds", courseIds);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
//        return methods.addDeviceCourse(body);
        return null;

    }
}
