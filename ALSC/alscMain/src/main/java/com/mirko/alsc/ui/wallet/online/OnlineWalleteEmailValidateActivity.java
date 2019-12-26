package com.mirko.alsc.ui.wallet.online;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.EmailCodeApi;
import com.alsc.net.api.MobileCodeApi;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.request.RegisterRequest;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletEmailValidateBinding;
import com.mirko.alsc.databinding.ActivityOnlineWalletPhoneValidateBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.StringUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/24.
 * 邮箱验证
 */
public class OnlineWalleteEmailValidateActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "OnlineWalletMakeActivity";
    ActivityOnlineWalletEmailValidateBinding binding;
    public static final int REQUEST_PHONE_CODE = 10;
    private String email; //邮箱号码
    private String emailCode; //邮箱验证码
    private RegisterRequest registerRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_email_validate);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.setClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(OnlineWalleteEmailValidateActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        registerRequest = (RegisterRequest) getIntent().getSerializableExtra(Constant.EXTRA_REGISTER_REQUST);
    }

    @Override
    public void loadData() {

    }


    /**
     * 下一步
     */
    private void next() {
        email = binding.etEmail.getText().toString();
        emailCode = binding.etEmailCode.getText().toString();
        if (StringUtils.isEmpty(email)) {
            ToastHelper.alert(OnlineWalleteEmailValidateActivity.this, getString(R.string.register_error_msg1));
            return;
        } else if (StringUtils.isEmpty(emailCode)) {
            ToastHelper.alert(OnlineWalleteEmailValidateActivity.this, getString(R.string.register_error_msg1));
            return;
        }
        registerRequest.setEmail(email);
        registerRequest.setCaptcha(emailCode);
        Intent intent = new Intent(OnlineWalleteEmailValidateActivity.this, OnlineWalletSettingPwdActivity.class);
        intent.putExtra(Constant.EXTRA_REGISTER_REQUST, registerRequest);
        startActivity(intent);

    }


    /**
     * 获取邮箱验证码
     */
    private void getEmailCode(String email,String sid) {

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


        }), OnlineWalleteEmailValidateActivity.this, email,sid));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                next();
                break;

            case R.id.tv_get_code:
                String email = binding.etEmail.getText().toString();
                getEmailCode(email,registerRequest.getSid());
                break;

            case R.id.tv_phone:
                Intent intent = new Intent(OnlineWalleteEmailValidateActivity.this, OnlineWalletPhoneValidateActivity.class);
                intent.putExtra(Constant.EXTRA_REGISTER_REQUST, registerRequest);
                startActivity(intent);
                OnlineWalleteEmailValidateActivity.this.finish();
                break;

        }
    }
}
