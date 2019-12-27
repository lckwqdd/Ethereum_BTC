package com.mirko.alsc.ui.slide;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.bean.UserInfoResult;
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
    public static final int REQUEST_PHONE = 12;
    ActivitySecurityPhoneBindedBinding binding;
    private UserInfoResult userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_phone_binded);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);
        userInfo = (UserInfoResult) getIntent().getSerializableExtra(Constant.EXTRA_KEY_USER_INFO);
        binding.itPhone.setTvRightText(userInfo.getPhone());
        binding.itPhone.setTvRightColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(SecurityPhoneBindedActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

        //更换手机
        binding.itChangePhone.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =  new Intent(SecurityPhoneBindedActivity.this,SecurityPhoneNoBindActivity.class);
               intent.putExtra(Constant.EXTRA_SECUTITY_PHONE_TYPE,Constant.SECUTITY_PHONE_TYPE_MODITY);
                startActivityForResult(intent, REQUEST_PHONE);
            }
        });
    }

    @Override
    public void loadData() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PHONE) {
                String email = data.getStringExtra("email");
                binding.itPhone.setTvRightText(email);
            }
        }
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
