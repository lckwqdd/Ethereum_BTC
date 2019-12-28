package com.alsc.net.retrofit.listener;

import com.mirko.androidutil.utils.android.LogUtils;

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public abstract class HttpCommonListener<T> extends HttpOnNextListener {
    @Override
    public void onCacheNext(String string) {
    }

    @Override
    public void onCancel() {
    }
}
