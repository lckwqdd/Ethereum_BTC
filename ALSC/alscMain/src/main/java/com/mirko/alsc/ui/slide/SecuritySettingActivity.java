package com.mirko.alsc.ui.slide;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.bean.UserInfoResult;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecuritySettingBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * 安全设置页面
 */
public class SecuritySettingActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecuritySettingActivity";
    ActivitySecuritySettingBinding binding;
    private int bindPhone; //绑定手机
    private int bindEmail; //绑定email
    private UserInfoResult userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_setting);
        userInfo = (UserInfoResult) getIntent().getSerializableExtra(Constant.EXTRA_KEY_USER_INFO);
        bindPhone = userInfo.getIs_bang_phone();
        bindEmail = userInfo.getIs_bang_email();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);

        if (bindPhone == 1) {//已经绑定
            binding.securityPhone.setTvRightColor(getResources().getColor(R.color.gray));
            binding.securityPhone.setTvRightText(getResources().getString(R.string.security_opened));
            binding.securityPhone.setRightImageDesVisibility(View.GONE);
        } else { //未绑定
            binding.securityPhone.setTvRightColor(getResources().getColor(R.color.yellow));
            binding.securityPhone.setTvRightText(getResources().getString(R.string.security_no_bind));
            binding.securityPhone.setRightImageDesVisibility(View.VISIBLE);
        }
        if (bindEmail == 1) {//已经绑定
            binding.securityEmail.setTvRightColor(getResources().getColor(R.color.gray));
            binding.securityEmail.setTvRightText(getResources().getString(R.string.security_opened));
            binding.securityEmail.setRightImageDesVisibility(View.GONE);
        } else {//未绑定
            binding.securityEmail.setTvRightColor(getResources().getColor(R.color.yellow));
            binding.securityEmail.setTvRightText(getResources().getString(R.string.security_no_bind));
            binding.securityEmail.setRightImageDesVisibility(View.VISIBLE);

        }

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
                Intent intent = new Intent(SecuritySettingActivity.this, SecurityLoginPasswordActivity.class);
                intent.putExtra(Constant.EXTRA_KEY_USER_INFO, userInfo);
                startActivity(intent);

            }
        });
        //支付密码修改
        binding.securityChangePayPassword.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecuritySettingActivity.this, SecurityPayPasswordActivity.class));
            }
        });
        //谷歌身份验证
        binding.securityGoogle.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecuritySettingActivity.this, SecurityGoogleActivity.class));
            }
        });
        //手机
        binding.securityPhone.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bindPhone == 1) {//已经绑定
                    goTo(SecurityPhoneBindedActivity.class);
                } else {
                    goTo(SecurityPhoneNoBindActivity.class);
                }
            }
        });
        //email
        binding.securityEmail.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bindEmail == 1) {//已经绑定
                    goTo(SecurityEmailBindedActivity.class);
                } else {
                    goTo(SecurityEmailNoBindActivity.class);
                }
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
                LogUtils.d(TAG, "返回点击");
                onBackPressed();

        }
    }
}
