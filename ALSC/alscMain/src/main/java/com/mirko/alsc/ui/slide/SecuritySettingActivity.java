package com.mirko.alsc.ui.slide;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecuritySettingBinding;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * 安全设置页面
 */
public class SecuritySettingActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecuritySettingActivity";
    ActivitySecuritySettingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_setting);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);


        binding.securityGoogle.setTvRightColor(getResources().getColor(R.color.yellow));
        binding.securityGoogle.setTvRightText(getResources().getString(R.string.security_no_bind));

        binding.securityPhone.setTvRightColor(getResources().getColor(R.color.gray));
        binding.securityPhone.setTvRightText(getResources().getString(R.string.security_opened));

        binding.securityEmail.setTvRightColor(getResources().getColor(R.color.yellow));
        binding.securityEmail.setTvRightText(getResources().getString(R.string.security_no_bind));

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(SecuritySettingActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        //登录密码修改
        binding.securityChangeLoginPassword.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecuritySettingActivity.this,SecurityLoginPasswordActivity.class));

            }
        });
        //支付密码修改
        binding.securityChangePayPassword.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecuritySettingActivity.this,SecurityPayPasswordActivity.class));
            }
        });
        //谷歌身份验证
        binding.securityGoogle.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecuritySettingActivity.this,SecurityGoogleActivity.class));
            }
        });
        //手机
        binding.securityPhone.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecuritySettingActivity.this,SecurityPhoneActivity.class));
            }
        });
        //email
        binding.securityEmail.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecuritySettingActivity.this,SecurityEmailActivity.class));
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG,"返回点击");
                onBackPressed();

        }
    }
}
