package com.alsc.net.retrofit.subscribers;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.alsc.net.NetApplication;
import com.alsc.net.R;
import com.alsc.net.db.CookieDbUtil;
import com.alsc.net.retrofit.api.BaseApi;
import com.alsc.net.retrofit.http.cookie.CookieResulte;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.net.util.AppUtil;
import com.alsc.net.util.CommUtils;
import com.alsc.net.util.Constant;
import com.mirko.androidutil.view.CustomeDialog;
import com.mirko.androidutil.view.ToastHelper;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Observable;
import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by WZG on 2016/7/16.
 */
public class ProgressSubscriber<T> extends Subscriber<T> {


    private final static int NONE_FRIEND = 3143;
    //重复添加
    private final static int REDO_APPLY = 3128;
    /*是否弹框*/
    private boolean showPorgress = true;
    //    回调接口
    private HttpOnNextListener mSubscriberOnNextListener;
    //    弱引用反正内存泄露
    private SoftReference<RxAppCompatActivity> mActivity;
    //    加载框可自己定义
    private CustomeDialog pd;
    /*请求数据*/
    private BaseApi api;


    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api) {
        this.api = api;
        this.mSubscriberOnNextListener = api.getListener();
        this.mActivity = new SoftReference<>(api.getRxAppCompatActivity());
        setShowPorgress(api.isShowProgress());
        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel());
        }
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
            ProgressBar mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            CommUtils.setProgress(context, mProgressBar);
            pd = new CustomeDialog(context, view, R.style.LoadingDialog);
            pd.setCanceledOnTouchOutside(false);
            pd.setGravity(Gravity.CENTER);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        onCancelProgress();
                    }
                });
            }
        }
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowPorgress()) return;
        try {
            Context context = mActivity.get();
            if (pd == null || context == null) return;
            if (!pd.isShowing()) {
                pd.show();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (!isShowPorgress()) return;
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
        /*缓存并且有网*/
        if (api.isCache() && AppUtil.isNetworkAvailable(NetApplication.getInstance().getContext())) {
            /*获取缓存数据*/
            CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(api.getUrl());
            if (cookieResulte != null) {
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < api.getCookieNetWorkTime()) {
                    if (mSubscriberOnNextListener != null) {
                        mSubscriberOnNextListener.onCacheNext(cookieResulte.getResulte());
                    }
                    onCompleted();
                    unsubscribe();
                }
            }
        } else {
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        /*需要緩存并且本地有缓存才返回*/
        if (api.isCache()) {
            Observable.just(api.getUrl()).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    errorDo(e);
                }

                @Override
                public void onNext(String s) {
                    /*获取缓存数据*/
                    CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(s);
                    if (cookieResulte == null) {
                        return;
//                        throw new HttpTimeException("请求出错,请稍后再试");
                    }
                    long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                    if (time < api.getCookieNoNetWorkTime()) {
                        if (mSubscriberOnNextListener != null) {
                            mSubscriberOnNextListener.onCacheNext(cookieResulte.getResulte());
                        }
                    } else {
                        CookieDbUtil.getInstance().deleteCookie(cookieResulte);
                        return;
//                        throw new HttpTimeException("请求出错,请稍后再试");
                    }
                }
            });
        } else {
            errorDo(e);
        }
    }

    /*错误统一处理*/
    private void errorDo(Throwable e) {
        Context context = mActivity.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            ToastHelper.alert(context, "网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            ToastHelper.alert(context, "网络中断，请检查您的网络状态");
        } else if (e instanceof UnknownHostException) {
        } else {
            if (!(api.getStatus() == Constant.RETURN_CODE_ERROE)) {
                ToastHelper.alert(context, e.getMessage());
            }
        }
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onError(e);
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    public boolean isShowPorgress() {
        return showPorgress;
    }

    /**
     * 是否需要弹框设置
     *
     * @param showPorgress
     */
    public void setShowPorgress(boolean showPorgress) {
        this.showPorgress = showPorgress;
    }
}