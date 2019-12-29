package com.mirko.alsc.ui.wallet.online;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.LoginApi;
import com.alsc.net.api.PicCodeApi;
import com.alsc.net.bean.entity.LoginResultEntity;
import com.alsc.net.bean.entity.PicCodeResultEntity;
import com.alsc.net.bean.request.LoginRequest;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.utils.Md5Utils;
import com.mirko.alsc.MainActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletLoginAlscBinding;
import com.mirko.androidutil.encryption.MD5Utils;
import com.mirko.androidutil.utils.DateUtils;
import com.mirko.androidutil.utils.StringUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.utils.android.image.ImageUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/25.
 * 登录页面
 */
public class OnlineWalletLoginActivity extends AlscBaseActivity implements View.OnClickListener {

    private static final String TAG = "OnlineWalletLoginActivity";
    ActivityOnlineWalletLoginAlscBinding binding;
    private String name; //账户名
    private String loginPsw; //登录密码
    private String code; //图片验证码
    private String sid;
    private LoginRequest loginRequest = new LoginRequest();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_login_alsc);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
//        binding.titleBar.setOnLeftClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(OnlineWalletLoginActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        binding.setClickListener(this);
    }

    @Override
    public void loadData() {
        getPicCaptcha();
    }


    /**
     * 下一步
     */
    private void next(){

        name = binding.etLoginName.getText().toString();
        loginPsw =  binding.etLoginPsw.getText().toString();
        code = binding.etLoginCode.getText().toString();

        if (StringUtils.isEmpty(name)||StringUtils.isEmpty(loginPsw)) {
            ToastHelper.alert(OnlineWalletLoginActivity.this, "不能为空");
            return;
        } else if(StringUtils.isEmpty(code)){
            ToastHelper.alert(OnlineWalletLoginActivity.this, "不能为空");
            return;
        }
        loginRequest.setName(name);
        loginRequest.setPassword(MD5Utils.getMD5Code(loginPsw));
        loginRequest.setCode(code);
        loginRequest.setSid(sid);
        LogUtils.d(TAG, "登录信息:" + loginRequest.toString());

        startLogin(loginRequest);

    }
    /**
     * 开始登录
     *
     * @param loginRequest
     */
    private void startLogin(LoginRequest loginRequest) {

        HttpManager.getInstance().doHttpDeal(new LoginApi((new HttpOnNextListener<LoginResultEntity>() {
            @Override
            public void onNext(LoginResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "登录成功:" + result.toString());
                    CacheManager.LoginToken.set(result.getToken());
                    long expiresTime = DateUtils.dateToUnixTimestamp(result.getExpires_time(),"yyyy-MM-dd HH:mm:ss");
                    CacheManager.ExpiresTime.set(expiresTime);
                    startActivity(new Intent(OnlineWalletLoginActivity.this, MainActivity.class));
                    tack.popUntilActivity(MainActivity.class);
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
                LogUtils.d(TAG, "登录失败：" + e.toString());
                super.onError(e);
            }

        }), OnlineWalletLoginActivity.this, loginRequest));
    }

    /**
     * 获取图片验证码
     */
    private void getPicCaptcha() {

        HttpManager.getInstance().doHttpDeal(new PicCodeApi((new HttpOnNextListener<PicCodeResultEntity>() {
            @Override
            public void onNext(PicCodeResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取图片验证码成功:" + result.getCode().toString());
                    Bitmap bitmap = ImageUtils.base64ToBitmap(result.getCode());
                    binding.ivPicCaptcha.setImageBitmap(bitmap);
                    sid = result.getSid();
                    CacheManager.PicSid.set(sid); //缓存sid
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


        }), OnlineWalletLoginActivity.this));
    }


    /**
     * 跳转到登录页面
     */
    private void startLoginActivity(){

        Intent intent = new Intent(OnlineWalletLoginActivity.this, OnlineWalletLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                next();
                break;
            case R.id.tv_change_pic:
                getPicCaptcha();
                break;
            case R.id.tv_forget_psw:
                goTo(OnlineWalletForgotPwdActivity.class);
                break;

        }
    }
}
