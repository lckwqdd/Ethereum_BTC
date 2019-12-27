package com.mirko.alsc.ui.wallet.online;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.FindPasswordApi;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.request.FindPasswordRequest;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletResetPwdBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.encryption.MD5Utils;
import com.mirko.androidutil.utils.StringUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/26.
 * 重置密码
 */
public class OnlineWalletResetPwdActivity extends AlscBaseActivity implements View.OnClickListener {

    private static final String TAG = "OnlineWalletForgotPswActivity";
    ActivityOnlineWalletResetPwdBinding binding;
    private FindPasswordRequest request;
    private String loginPsw;
    private String loginPswSure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_reset_pwd);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        request = (FindPasswordRequest)getIntent().getSerializableExtra(Constant.EXTRA_KEY_RESERT_PWD);
        binding.titleBar.setOnLeftClickListener(this);
        binding.setClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(OnlineWalletResetPwdActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {

    }

    /**
     * 找回密码
     */
    private void findPassword(FindPasswordRequest request) {

        HttpManager.getInstance().doHttpDeal(new FindPasswordApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                goTo(OnlineWalletLoginActivity.class);
                finish();
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
                LogUtils.d(TAG, "重置密码失败：" + e.toString());
                super.onError(e);
            }


        }), OnlineWalletResetPwdActivity.this,request));
    }

    /**
     * 开始重置密码
     */
    private void startRestPwd(){

        loginPsw = binding.etLoginPwd.getText().toString();
        loginPswSure = binding.etLoginPwdSure.getText().toString();
        if (!StringUtils.checkNubmerAndLetter(loginPsw)) {
            ToastHelper.alert(OnlineWalletResetPwdActivity.this, getString(R.string.register_error_msg5));
            return;
        }
        if (StringUtils.isEmpty(loginPsw)||StringUtils.isEmpty(loginPsw)) {
            ToastHelper.alert(OnlineWalletResetPwdActivity.this, getString(R.string.register_error_msg1));
            return;
        }
        request.setPwd(MD5Utils.getMD5Code(loginPsw));
        request.setPwd2(MD5Utils.getMD5Code(loginPswSure));

        findPassword(request);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG,"返回点击");
                onBackPressed();
                break;
            case R.id.btn_next:
                startRestPwd();
                break;

        }
    }
}
