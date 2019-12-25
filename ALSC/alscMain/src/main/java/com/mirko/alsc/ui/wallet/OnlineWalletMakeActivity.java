package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.alsc.net.api.LoginApi;
import com.alsc.net.api.NoticeApi;
import com.alsc.net.api.PicCodeApi;
import com.alsc.net.api.RegisterApi;
import com.alsc.net.bean.entity.NoticeResultEntity;
import com.alsc.net.bean.entity.PicCodeResultEntity;
import com.alsc.net.bean.entity.RegisterResultEntity;
import com.alsc.net.bean.request.RegisterRequest;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityAboutBinding;
import com.mirko.alsc.databinding.ActivityOnlineWalletMakeBinding;
import com.mirko.alsc.ui.slide.LanguageSwithcingActivity;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.StringUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.utils.android.image.ImageUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/24.
 * 创建在线钱包
 */
public class OnlineWalletMakeActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "OnlineWalletMakeActivity";
    ActivityOnlineWalletMakeBinding binding;
    private String userName; //用户名
    private String inviteCode; //邀请码
    private String picCaptcha; //图片验证码
    private String sid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_make);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
//        binding.titleBar.setOnLeftClickListener(this);
        binding.setClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(OnlineWalletMakeActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {
        getPicCaptcha(); //获取图片验证码
    }


    /**
     * 下一步
     */
    private void next() {
        userName = binding.etUserName.getText().toString();
        inviteCode = binding.etInviteCode.getText().toString();
        picCaptcha = binding.etPicCaptcha.getText().toString();
        if (StringUtils.isEmpty(userName)) {
            ToastHelper.alert(OnlineWalletMakeActivity.this, "不能为空");
            return;
        } else if (StringUtils.isEmpty(inviteCode)) {
            ToastHelper.alert(OnlineWalletMakeActivity.this, "不能为空");
            return;
        } else if (StringUtils.isEmpty(picCaptcha)) {
            ToastHelper.alert(OnlineWalletMakeActivity.this, "不能为空");
            return;
        }
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(userName);
        registerRequest.setInvite_code(inviteCode);
        registerRequest.setPic_code(picCaptcha);
        registerRequest.setSid(sid);

//        startRegister(registerRequest);

        Intent intent = new Intent(OnlineWalletMakeActivity.this, OnlineWalletPhoneValidateActivity.class);
        intent.putExtra(Constant.EXTRA_REGISTER_REQUST, registerRequest);
        startActivity(intent);

    }


    /**
     * 获取图片验证码
     */
    private void getPicCaptcha() {

//        OkGo.<String>get("http://www.alsc.space/api/captcha")                            // 请求方式和请求url
//                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
//                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
//                //  .cacheTime(3000)//缓存时间
//                .execute(new StringCallback() {
//
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        Log.i("get",response.body());
////                        tvText.setText(response.body());
//
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                        super.onError(response);
//                    }
//                });

        HttpManager.getInstance().doHttpDeal(new PicCodeApi((new HttpOnNextListener<PicCodeResultEntity>() {
            @Override
            public void onNext(PicCodeResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取图片验证码成功:" + result.getCode().toString());
                    Bitmap bitmap = ImageUtils.base64ToBitmap(result.getCode());
//                    Bitmap bitmap = ImageUtils.base64ToBitmap("data:image/png;base64,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");
                    binding.ivPicCaptcha.setImageBitmap(bitmap);
                    sid = result.getSid();
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
                LogUtils.d(TAG, "注册失败：" + e.toString());
                super.onError(e);
            }


        }), OnlineWalletMakeActivity.this));
    }


    /**
     * 开始注册
     *
     * @param registerRequest
     */
    private void startRegister(RegisterRequest registerRequest) {

        HttpManager.getInstance().doHttpDeal(new RegisterApi((new HttpOnNextListener<RegisterResultEntity>() {
            @Override
            public void onNext(RegisterResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "注册成功:" + result.toString());
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
                LogUtils.d(TAG, "注册失败：" + e.toString());
                super.onError(e);
            }


        }), OnlineWalletMakeActivity.this, registerRequest));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                next();
                break;
            case R.id.tv_change_pic:
                getPicCaptcha();
                break;
            case R.id.lv_phone_area_name:
                getPicCaptcha();
                break;
        }
    }



}
