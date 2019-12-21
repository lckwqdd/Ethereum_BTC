package com.mirko.androidutil.http.retrofit.subscriber;

/**
 * Created by sam on 16/3/10.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
