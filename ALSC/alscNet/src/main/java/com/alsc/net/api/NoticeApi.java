package com.alsc.net.api;

import com.alsc.net.bean.entity.NoticeResultEntity;
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
 * 获取通知 类型 1公告 2资讯.
 *
 * | 字段  | 类型   | 是否可选 | 描述  |
 * | ----- | ------ | -------- | ----- |
 * | type | int | 否       | 类型 1公告 2资讯 |
 * | page_size | int | 是       | 每页显示条数 |
 * | page_index | int | 是       | 当前第几页 |
 */

public class NoticeApi extends BaseApi {

    private int type;
    private int pageSize;
    private int pageIndex;

    public NoticeApi(HttpOnNextListener<NoticeResultEntity> listener, RxAppCompatActivity rxAppCompatActivity, int type, int pageSize, int pageIndex) {
        super(listener, rxAppCompatActivity);
        this.type = type;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
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
            result.put("type", type);
            result.put("page_size", pageSize);
            result.put("page_index", pageIndex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), result.toString());
        return methods.getNotice(body);

    }
}
