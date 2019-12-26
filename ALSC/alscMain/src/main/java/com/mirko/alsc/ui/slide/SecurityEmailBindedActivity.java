package com.mirko.alsc.ui.slide;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecurityEmailBindedBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * Email设置
 */
public class SecurityEmailBindedActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecurityEmailActivity";
    ActivitySecurityEmailBindedBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_email_binded);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);


    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(SecurityEmailBindedActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

        binding.itEmail.setTvRightText("12356566");
        binding.itEmail.setTvRightColor(getResources().getColor(R.color.white));
        //登录密码修改
        binding.itChangeEmail.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(SecurityEmailBindedActivity.this,SecurityEmailNoBindActivity.class);
                intent.putExtra(Constant.EXTRA_SECUTITY_EMAIL_TYPE,Constant.SECUTITY_EMAIL_TYPE_MODITY);
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
