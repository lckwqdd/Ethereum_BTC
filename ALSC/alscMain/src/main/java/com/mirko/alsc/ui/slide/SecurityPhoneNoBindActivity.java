package com.mirko.alsc.ui.slide;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.BindPhoneApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.request.BindPhoneRequest;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecurityPhoneNoBindBinding;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.alsc.utils.Constant;
import com.mirko.alsc.utils.UrlRequstUtils;
import com.mirko.androidutil.utils.StringUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * 手机设置
 */
public class SecurityPhoneNoBindActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecurityPhoneNoBindActivity";
    ActivitySecurityPhoneNoBindBinding binding;
    private int phoneType;
    private String phone; //手机号
    private String phoneCode; //验证码
    private String areaCode = "86";
    private BindPhoneRequest request = new BindPhoneRequest();
    private UserInfoResult userInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_phone_no_bind);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        phoneType = getIntent().getIntExtra(Constant.EXTRA_SECUTITY_PHONE_TYPE, 0);
        userInfo = (UserInfoResult) getIntent().getSerializableExtra(Constant.EXTRA_KEY_USER_INFO);

        binding.titleBar.setOnLeftClickListener(this);
        if (phoneType == Constant.SECUTITY_PHONE_TYPE_MODITY) {
            binding.titleBar.setLeftText(getString(R.string.security_phone_modify));
        } else {
            binding.titleBar.setLeftText(getString(R.string.security_phone_bind));
        }
        binding.setClickListener(this);

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

    /**
     * 获取手机验证码
     */
    private void getPhoneCode() {
        String area = "86";
        phone = binding.etPhone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            ToastHelper.alert(this, getString(R.string.register_error_msg1));
            return;
        } else if (!StringUtils.checkPhoneNumber(phone, area)) {
            ToastHelper.alert(this, getString(R.string.register_error_msg1));
            return;
        }
        UrlRequstUtils.getMobileCode(SecurityPhoneNoBindActivity.this, phone, area);
        binding.vfGetCode.startCountDown();
    }

    private void next() {
        phone = binding.etPhone.getText().toString();
        phoneCode = binding.etPhoneCode.getText().toString();
        if (StringUtils.isEmpty(phoneCode)) {
            ToastHelper.alert(this, getString(R.string.register_error_msg1));
            return;
        }
        request.setPhone(phone);
        request.setToken(ComUtils.getTokenCache());
        request.setSid(ComUtils.getSid());
        request.setCode(phoneCode);
        request.setPhone_code(areaCode); //区号
        bindPhone(request);
    }

    /**
     * 绑定手机
     *
     * @param request
     */
    private void bindPhone(BindPhoneRequest request) {

        HttpManager.getInstance().doHttpDeal(new BindPhoneApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "绑定成功:" + result.toString());
                    UserInfoResult userInfoResult = ComUtils.getUserInfo();
                    if (userInfoResult != null) {
                        userInfoResult.setPhone(phone);
                        userInfoResult.setIs_bang_phone(1);
                        CacheManager.UserInfoResult.set(userInfoResult);
                    }
                    if (phoneType == Constant.SECUTITY_PHONE_TYPE_MODITY) {
                        Intent intent = new Intent();
                        intent.putExtra("phone", phone);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }else{
                        Intent intent = new Intent(SecurityPhoneNoBindActivity.this, SecurityPhoneBindedActivity.class);
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

        }), SecurityPhoneNoBindActivity.this, request));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();
                break;
            case R.id.vf_get_code:
                getPhoneCode();
                break;
            case R.id.btn_next:
                next();
                break;
        }
    }
}
