package com.mirko.alsc.ui.slide;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecurityPhoneNoBindBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * 手机设置
 */
public class SecurityPhoneNoBindActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecurityPhoneNoBindActivity";
    ActivitySecurityPhoneNoBindBinding binding;
    private int phoneType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_phone_no_bind);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        phoneType = getIntent().getIntExtra(Constant.EXTRA_SECUTITY_PHONE_TYPE,0);
        binding.titleBar.setOnLeftClickListener(this);
    if(phoneType == Constant.SECUTITY_PHONE_TYPE_MODITY){
            binding.titleBar.setLeftText(getString(R.string.security_phone_modify));
        }else{
            binding.titleBar.setLeftText(getString(R.string.security_phone_bind));
        }

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(SecurityPhoneNoBindActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

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
