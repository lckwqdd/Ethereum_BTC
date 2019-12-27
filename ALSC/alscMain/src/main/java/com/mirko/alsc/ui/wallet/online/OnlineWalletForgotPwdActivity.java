package com.mirko.alsc.ui.wallet.online;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alsc.net.bean.request.FindPasswordRequest;
import com.alsc.net.cache.CacheManager;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletForgetPwdBinding;
import com.mirko.alsc.utils.Constant;
import com.mirko.alsc.utils.UrlRequstUtils;
import com.mirko.alsc.views.MyVerificationCode;
import com.mirko.androidutil.utils.StringUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.CustomeDialog;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/26.
 * 找回密码
 */
public class OnlineWalletForgotPwdActivity extends AlscBaseActivity implements View.OnClickListener {

    private static final String TAG = "OnlineWalletForgotPswActivity";
    ActivityOnlineWalletForgetPwdBinding binding;
    public static final int REQUEST_PHONE_CODE = 10;
    private FindPasswordRequest request = new FindPasswordRequest();
    private String areaCode = "86";
    private String areaName;
    private String numberPhone;
    private String numberEmail;

    private CustomeDialog getCodeDialog;
    private int type = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_forget_pwd);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(OnlineWalletForgotPwdActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        binding.setClickListener(this);
    }

    @Override
    public void loadData() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PHONE_CODE) {
                areaCode = data.getStringExtra("phone_code");
                areaName = data.getStringExtra("area_name");
                binding.tvPhoneArea.setText(areaName);
                binding.tvAreaCode.setText("+" + areaCode);
            }
        }
    }


    /**
     * 选择手机号地区
     */
    private void selectPhoneArea() {
        Intent intent = new Intent(OnlineWalletForgotPwdActivity.this, OnlineWalletPhoneAreaActivity.class);
        startActivityForResult(intent, REQUEST_PHONE_CODE);

    }

    /**
     * 获取输入的数据
     */
    private void getInputData() {
        numberPhone = binding.etPhone.getText().toString();
        numberEmail = binding.etEmail.getText().toString();

        if (type == Constant.TYPE_EMAIL) {
            if (StringUtils.isEmpty(numberEmail)) {
                ToastHelper.alert(OnlineWalletForgotPwdActivity.this, getString(R.string.register_error_msg1));
                return;
            }
            request.setEmail(numberEmail);
        } else if (type == Constant.TYPE_PHONE) {
            if (StringUtils.isEmpty(numberPhone) || !StringUtils.checkPhoneNumber(numberPhone, areaCode)) {
                ToastHelper.alert(OnlineWalletForgotPwdActivity.this, getString(R.string.register_error_msg1));
                return;
            }
            request.setMobile(numberPhone);
            request.setPhone_code(areaCode);
        }
        showGetCodeView();
    }


    /**
     * 跳转到修改密码
     */
    private void goResertPswActivity() {

        Intent intent = new Intent(OnlineWalletForgotPwdActivity.this, OnlineWalletResetPwdActivity.class);
        intent.putExtra(Constant.EXTRA_KEY_RESERT_PWD, request);
        startActivity(intent);
    }


    /**
     * 显示获取验证码页面
     */
    private void showGetCodeView() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_get_code, null);
        getCodeDialog = new CustomeDialog(this, view);
        getCodeDialog.setCanceledOnTouchOutside(false);
        getCodeDialog.setGravity(Gravity.BOTTOM);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getCodeDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        getCodeDialog.getWindow().setAttributes(lp);

        TextView tvTitle = view.findViewById(R.id.tv_title); //手机号或者邮箱号码
        TextView tvNumber = view.findViewById(R.id.tv_number); //手机号或者邮箱号码
        EditText etCode = view.findViewById(R.id.et_code); //验证码
        RadioGroup radioGroup = view.findViewById(R.id.rg_email_phone);
        RadioButton rbEmail = view.findViewById(R.id.rb_email);
        RadioButton rbPhone = view.findViewById(R.id.rb_phone);
        MyVerificationCode vfGetCode = view.findViewById(R.id.vf_get_code);

        if (type == Constant.TYPE_EMAIL) {
            rbEmail.setChecked(true);
            rbPhone.setVisibility(View.GONE);
            rbEmail.setVisibility(View.VISIBLE);
            tvNumber.setText(numberEmail);
            etCode.setHint(R.string.sc_dialog_email_code_des);
            tvTitle.setText(R.string.sc_dialog_title_email);

        } else if (type == Constant.TYPE_PHONE) {
            rbPhone.setChecked(true);
            rbPhone.setVisibility(View.VISIBLE);
            rbEmail.setVisibility(View.GONE);
            tvNumber.setText(numberPhone);
            etCode.setHint(R.string.sc_dialog_phone_code_des);
            tvTitle.setText(R.string.sc_dialog_title_phone);
        }

        vfGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid = StringUtils.getRandomString(26);
                CacheManager.PicSid.set(sid);
                request.setSid(sid);
                if (type == Constant.TYPE_EMAIL) {
                    if (StringUtils.isEmpty(numberEmail)) {
                        ToastHelper.alert(OnlineWalletForgotPwdActivity.this, getString(R.string.register_error_msg1));
                        return;
                    }
                    UrlRequstUtils.getEmailCode(OnlineWalletForgotPwdActivity.this, numberEmail);
                } else if (type == Constant.TYPE_PHONE) {
                    if (StringUtils.isEmpty(numberPhone)) {
                        ToastHelper.alert(OnlineWalletForgotPwdActivity.this, getString(R.string.register_error_msg1));
                        return;
                    }
                    UrlRequstUtils.getMobileCode(OnlineWalletForgotPwdActivity.this, numberPhone, areaCode);
                }
                vfGetCode.startCountDown();
            }
        });

        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCodeDialog != null) {
                    getCodeDialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCodeDialog != null && getCodeDialog.isShowing()) {
                    String code = etCode.getText().toString();
                    if (StringUtils.isEmpty(code)) {
                        ToastHelper.alert(OnlineWalletForgotPwdActivity.this, getString(R.string.register_error_msg1));
                        return;
                    }
                    request.setCode(code);
                    goResertPswActivity();
                }
                if (getCodeDialog != null) {
                    getCodeDialog.dismiss();
                }
            }
        });
        getCodeDialog.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();
                break;
            case R.id.btn_next:

                getInputData();



                break;
            case R.id.lv_phone_area_name:
            case R.id.lv_phone_area_code:
                selectPhoneArea();
                break;

            case R.id.tv_email:
                type = Constant.TYPE_EMAIL;
                binding.llPhone.setVisibility(View.GONE);
                binding.llEmail.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_phone:
                type = Constant.TYPE_PHONE;
                binding.llPhone.setVisibility(View.VISIBLE);
                binding.llEmail.setVisibility(View.GONE);
                break;
        }
    }
}
