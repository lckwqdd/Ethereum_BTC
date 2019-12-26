package com.mirko.alsc.ui.slide;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecurityEmailNoBindBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * Email设置
 */
public class SecurityEmailNoBindActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecurityEmailActivity";
    ActivitySecurityEmailNoBindBinding binding;
    private int emailType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_email_no_bind);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        emailType = getIntent().getIntExtra(Constant.EXTRA_SECUTITY_EMAIL_TYPE, 0);
        binding.titleBar.setOnLeftClickListener(this);
        if (emailType == Constant.SECUTITY_EMAIL_TYPE_MODITY) {
            binding.titleBar.setLeftText(getString(R.string.security_email_modify));
        } else {
            binding.titleBar.setLeftText(getString(R.string.security_email_bind));
        }

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(SecurityEmailNoBindActivity.this, getResources().getColor(R.color.color_slide_bg));
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
                LogUtils.d(TAG, "返回点击");
                onBackPressed();
                break;
        }
    }
}
