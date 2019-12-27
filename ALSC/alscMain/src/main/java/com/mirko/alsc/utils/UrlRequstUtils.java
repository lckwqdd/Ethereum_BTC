package com.mirko.alsc.utils;

import com.alsc.net.api.EmailCodeApi;
import com.alsc.net.api.HomeMsgApi;
import com.alsc.net.api.MobileCodeApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
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
    public static void getMobileCode(RxAppCompatActivity activity , String phone, String areaCode) {

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


        }), activity, phone, areaCode,ComUtils.getSid()));
    }


    /**
     * 获取邮箱验证码
     */
    public static void getEmailCode(RxAppCompatActivity activity ,String email) {

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
                    if (result.getUser_info() != null){
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

}
