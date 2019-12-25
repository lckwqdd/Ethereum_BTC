package com.mirko.alsc.ui.wallet;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.EmailCodeApi;
import com.alsc.net.api.MobileCodeApi;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.request.RegisterRequest;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletPhoneValidateBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.StringUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/24.
 * 验证手机
 */
public class OnlineWalletPhoneValidateActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "OnlineWalletMakeActivity";
    ActivityOnlineWalletPhoneValidateBinding binding;
    public static final int REQUEST_PHONE_CODE = 10;
    private String phone; //手机号
    private String phoneCode; //手机验证码
    private RegisterRequest registerRequest;
    private String areaCode; //区号

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_phone_validate);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.setClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(OnlineWalletPhoneValidateActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        registerRequest = (RegisterRequest) getIntent().getSerializableExtra(Constant.EXTRA_REGISTER_REQUST);
    }

    @Override
    public void loadData() {

    }


    /**
     * 下一步
     */
    private void next() {
        phone = binding.etPhone.getText().toString();
        phoneCode = binding.etPhone.getText().toString();
        if (!StringUtils.checkPhoneNumber(phone, "+86")) {
            ToastHelper.alert(OnlineWalletPhoneValidateActivity.this, "不能为空");
            return;
        } else if (StringUtils.isEmpty(phoneCode)) {
            ToastHelper.alert(OnlineWalletPhoneValidateActivity.this, "不能为空");
            return;
        } else if (StringUtils.isEmpty(phone)) {
            ToastHelper.alert(OnlineWalletPhoneValidateActivity.this, "不能为空");
            return;
        }
        registerRequest.setPhone(phone);
        registerRequest.setCaptcha(phoneCode);
        registerRequest.setPhone_area(areaCode);
        Intent intent = new Intent(OnlineWalletPhoneValidateActivity.this, OnlineWalletSettingPwdActivity.class);
        intent.putExtra(Constant.EXTRA_REGISTER_REQUST, registerRequest);
        startActivity(intent);

    }

    /**
     * 选择手机号地区
     */
    private void selectPhoneArea() {
        Intent intent = new Intent(OnlineWalletPhoneValidateActivity.this, OnlineWalletPhoneAreaActivity.class);
        startActivityForResult(intent, REQUEST_PHONE_CODE);

    }

    /**
     * 获取手机验证码
     */
    private void getMobileCode(String phone, String areaCode) {

        HttpManager.getInstance().doHttpDeal(new MobileCodeApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取手机验证码成功:");
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
                LogUtils.d(TAG, "注册失败：" + e.toString());
                super.onError(e);
            }


        }), OnlineWalletPhoneValidateActivity.this, phone, areaCode));
    }


    /**
     * 获取邮箱验证码
     */
    private void getEmailCode(String phone) {

        HttpManager.getInstance().doHttpDeal(new EmailCodeApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取邮箱验证码成功:");
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
                LogUtils.d(TAG, "获取邮箱验证码失败：" + e.toString());
                super.onError(e);
            }


        }), OnlineWalletPhoneValidateActivity.this, phone));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PHONE_CODE) {
                areaCode = data.getStringExtra("phone_code");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                next();
                break;
            case R.id.lv_phone_area_name:
                selectPhoneArea();
                break;
            case R.id.tv_get_code:

                phone = binding.etPhone.getText().toString();

                getMobileCode(phone, areaCode);

                break;

        }
    }
}
