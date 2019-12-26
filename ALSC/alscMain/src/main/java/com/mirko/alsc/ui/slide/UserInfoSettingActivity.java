package com.mirko.alsc.ui.slide;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityUserInfoSettingBinding;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * 个人信息设置页面
 */
public class UserInfoSettingActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "UserInfoSettingActivity";
    ActivityUserInfoSettingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info_setting);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);


    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(UserInfoSettingActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        //头像
        binding.userInfoHeadImg.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this,SecurityLoginPasswordActivity.class));

            }
        });
        //昵称
        binding.userInfoNick.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this,SecurityPayPasswordActivity.class));
            }
        });
        //id号
        binding.userInfoId.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this,SecurityGoogleActivity.class));
            }
        });
        //性别
        binding.userInfoSex.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this,SecurityGoogleActivity.class));
            }
        });
        //二维码
        binding.userInfoQrcode.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this, SecurityEmailNoBindActivity.class));
            }
        });
        //地区
        binding.userInfoArea.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this, SecurityEmailNoBindActivity.class));
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
