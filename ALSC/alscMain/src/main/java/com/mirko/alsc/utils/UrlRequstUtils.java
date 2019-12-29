package com.mirko.alsc.utils;

import android.app.Activity;
import android.util.Log;

import com.alsc.net.api.EmailCodeApi;
import com.alsc.net.api.HomeMsgApi;
import com.alsc.net.api.MobileCodeApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.bean.okgo.AddressEntity;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.mirko.alsc.MainActivity;
import com.mirko.androidutil.utils.android.LogUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by Mirko on 2019/12/27 08:09.
 */
public class UrlRequstUtils {

    private static final String TAG = "ComUtils";


    /**
     * 获取手机验证码
     */
    public static void getMobileCode(RxAppCompatActivity activity, String phone, String areaCode) {

        HttpManager.getInstance().doHttpDeal(new MobileCodeApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取手机验证码成功:");
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d(TAG, "获取手机验证码失败：" + e.toString());
                super.onError(e);
            }


        }), activity, phone, areaCode, ComUtils.getSid()));
    }


    /**
     * 获取邮箱验证码
     */
    public static void getEmailCode(RxAppCompatActivity activity, String email) {

        HttpManager.getInstance().doHttpDeal(new EmailCodeApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取邮箱验证码成功:");
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d(TAG, "获取邮箱验证码失败：" + e.toString());
                super.onError(e);
            }


        }), activity, email, ComUtils.getSid()));
    }

    /**
     * 加载首页数据
     */
    public static void loadHomeData(RxAppCompatActivity activity) {

        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new HomeMsgApi((new HttpOnNextListener<HomeMsgResultEntity>() {
            @Override
            public void onNext(HomeMsgResultEntity result) {
                if (result != null) {
                    if (result.getUser_info() != null) {
                        UserInfoResult userInfo = result.getUser_info();
                        CacheManager.UserInfoResult.set(result.getUser_info());
                    }
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }


        }), activity, token));
    }


    public static void getUnspent(Activity activity, String params) {

        String url = "https://blockchain.info/unspent?active=" + params;
        OkGo.<String>get(url)                            // 请求方式和请求url
                .tag(activity)                   // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                //  .cacheTime(3000)//缓存时间
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("get", response.body());

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


    public static void getAddress(Activity activity, String params) {

        String url = "https://chain.api.btc.com/v3/address/" + params +"/unspent";
        OkGo.<String>get(url)                            // 请求方式和请求url
                .tag(activity)                   // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                //  .cacheTime(3000)//缓存时间
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("get", response.body());
                        String s = response.body().toString();
                        //解析
                        Gson gson = new Gson();
                        AddressEntity jsonBean = gson.fromJson(s, AddressEntity.class);
                        Log.i("解析后", jsonBean.toString());

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


    public static void getTxDecode(Activity activity, String params) {

        String url = " https://chain.api.btc.com/tools/tx-decode";
        OkGo.<String>post(url)                            // 请求方式和请求url
                .tag(activity)                   // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                .params("rawhex", params)
                //  .cacheTime(3000)//缓存时间
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("get", response.body());

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

}
