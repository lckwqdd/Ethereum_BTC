package com.mirko.alsc.ui.slide;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecurityPhoneBindedBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/26.
 * 手机设置
 */
public class SecurityPhoneBindedActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecurityPhoneBindedActivity";
    ActivitySecurityPhoneBindedBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_phone_binded);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(SecurityPhoneBindedActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

        binding.itPhone.setTvRightText("12356566");
        binding.itPhone.setTvRightColor(getResources().getColor(R.color.white));
        //登录密码修改
        binding.itChangePhone.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =  new Intent(SecurityPhoneBindedActivity.this,SecurityPhoneNoBindActivity.class);
               intent.putExtra(Constant.EXTRA_SECUTITY_PHONE_TYPE,Constant.SECUTITY_PHONE_TYPE_MODITY);
               startActivity(intent);
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
                break;
        }
    }
}
