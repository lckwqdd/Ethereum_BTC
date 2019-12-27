package com.mirko.alsc.ui.slide;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.BindEmailApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.request.BindEmailRequest;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecurityEmailNoBindBinding;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.alsc.utils.Constant;
import com.mirko.alsc.utils.UrlRequstUtils;
import com.mirko.androidutil.utils.StringUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * Email设置
 */
public class SecurityEmailNoBindActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecurityEmailNoBindActivity";
    ActivitySecurityEmailNoBindBinding binding;
    private int emailType;
    private BindEmailRequest request = new BindEmailRequest();
    private String email; //邮箱
    private String emailCode; //邮箱验证码


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
        binding.setClickListener(this);

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


    /**
     * 获取邮箱验证码
     */
    private void getEmailCode() {
        email = binding.etEmail.getText().toString();
        if (StringUtils.isEmpty(email)) {
            ToastHelper.alert(this, getString(R.string.register_error_msg1));
            return;
        }
        UrlRequstUtils.getEmailCode(SecurityEmailNoBindActivity.this, email);
        binding.vfGetCode.startCountDown();
    }

    private void next() {
        email = binding.etEmail.getText().toString();
        emailCode = binding.etEmailCode.getText().toString();
        if (StringUtils.isEmpty(emailCode)) {
            ToastHelper.alert(this, getString(R.string.register_error_msg1));
            return;
        }
        request.setEmail(email);
        request.setToken(ComUtils.getTokenCache());
        request.setSid(ComUtils.getSid());
        request.setCode(emailCode);
        bindEmail(request);
    }

    /**
     * 绑定邮箱
     *
     * @param request
     */
    private void bindEmail(BindEmailRequest request) {

        HttpManager.getInstance().doHttpDeal(new BindEmailApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                if (result != null) {
                    UserInfoResult userInfoResult = ComUtils.getUserInfo();
                    if (userInfoResult != null) {
                        userInfoResult.setEmail(email);
                        userInfoResult.setIs_bang_email(1);
                        CacheManager.UserInfoResult.set(userInfoResult);
                    }
                    if (emailType == Constant.SECUTITY_EMAIL_TYPE_MODITY) {
                        LogUtils.d(TAG, "绑定成功:" + result.toString());
                        Intent intent = new Intent();
                        intent.putExtra("email", email);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }else{
                        Intent intent = new Intent(SecurityEmailNoBindActivity.this, SecurityEmailBindedActivity.class);
                        intent.putExtra(Constant.EXTRA_KEY_USER_INFO, userInfoResult);
                        startActivity(intent);
                        finish();
                    }
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
                LogUtils.d(TAG, "绑定失败：" + e.toString());
                super.onError(e);
            }

        }), SecurityEmailNoBindActivity.this, request));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();
                break;
            case R.id.vf_get_code:
                getEmailCode();
                break;
            case R.id.btn_next:
                next();
                break;
        }
    }
}
