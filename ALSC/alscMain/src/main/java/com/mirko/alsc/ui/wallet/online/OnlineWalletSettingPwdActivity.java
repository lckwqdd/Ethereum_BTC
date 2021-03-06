package com.mirko.alsc.ui.wallet.online;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.RegisterApi;
import com.alsc.net.bean.entity.RegisterResultEntity;
import com.alsc.net.bean.request.RegisterRequest;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.MainActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletSetPwdBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.encryption.MD5Utils;
import com.mirko.androidutil.utils.StringUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/25.
 * 设置密码
 */
public class OnlineWalletSettingPwdActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "OnlineWalletSettingPwdActivity";
    ActivityOnlineWalletSetPwdBinding binding;
    private String loginPsw; //登录密码
    private String loginPswSure; //登录密码
    private String payPsw; //支付密码
    private String payPswSure; //支付密码
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_set_pwd);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
//        binding.titleBar.setOnLeftClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(OnlineWalletSettingPwdActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        registerRequest = (RegisterRequest) getIntent().getSerializableExtra(Constant.EXTRA_REGISTER_REQUST);
        binding.setClickListener(this);
    }

    @Override
    public void loadData() {

    }


    /**
     * 下一步
     */
    private void next(){
        loginPsw = binding.etLoginPwd.getText().toString();
        loginPswSure = binding.etLoginPwdSure.getText().toString();
        payPsw = binding.etPayPwd.getText().toString();
        payPswSure = binding.etPayPwdLogin.getText().toString();
        if (!StringUtils.checkNubmerAndLetter(loginPsw)) {
            ToastHelper.alert(OnlineWalletSettingPwdActivity.this, getString(R.string.register_error_msg5));
            return;
        }else if (!StringUtils.checkNubmerAndLetter(payPsw)) {
            ToastHelper.alert(OnlineWalletSettingPwdActivity.this, getString(R.string.register_error_msg3));
            return;
        }
        if (StringUtils.isEmpty(loginPsw)||StringUtils.isEmpty(loginPsw)||
                StringUtils.isEmpty(payPsw)||StringUtils.isEmpty(payPswSure)) {
            ToastHelper.alert(OnlineWalletSettingPwdActivity.this, getString(R.string.register_error_msg1));
            return;
        } else if(!loginPsw.equals(loginPswSure)){
            ToastHelper.alert(OnlineWalletSettingPwdActivity.this, getString(R.string.register_error_msg2));
            return;
        } else if(!payPsw.equals(payPswSure)){
            ToastHelper.alert(OnlineWalletSettingPwdActivity.this, "不一致");
            return;
        }
        registerRequest.setPwd(MD5Utils.getMD5Code(loginPsw));
        registerRequest.setPwd2(MD5Utils.getMD5Code(loginPswSure));
        registerRequest.setPay_pwd(MD5Utils.getMD5Code(payPsw));
        registerRequest.setPay_pwd2(MD5Utils.getMD5Code(payPswSure));
        LogUtils.d(TAG, "注册信息:" + registerRequest.toString());
        startRegister(registerRequest);

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
                    goTo(OnlineWalletLoginActivity.class);
                    finish();
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


        }), OnlineWalletSettingPwdActivity.this, registerRequest));
    }


    /**
     * 跳转到登录页面
     */
    private void startLoginActivity(){

        Intent intent = new Intent(OnlineWalletSettingPwdActivity.this, OnlineWalletLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                next();
                break;

        }
    }
}
