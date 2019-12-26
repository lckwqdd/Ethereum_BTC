package com.mirko.alsc.ui.slide;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alsc.net.api.EmailCodeApi;
import com.alsc.net.api.MobileCodeApi;
import com.alsc.net.api.ModifyLoginPswApi;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.request.ModifyPswRequest;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecurityPayPasswordBinding;
import com.mirko.alsc.databinding.ActivitySecuritySettingBinding;
import com.mirko.alsc.ui.wallet.online.OnlineWalletLoginActivity;
import com.mirko.alsc.views.MyVerificationCode;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.CustomeDialog;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * 修改支付密码
 */
public class SecurityPayPasswordActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecurityPayPasswordActivity";
    ActivitySecurityPayPasswordBinding binding;

    private ModifyPswRequest request;
    private String newPwd; //新密码
    private String oldPwd; //旧密码
    private int type = 1; //类型，手机或者邮箱
    private String numberPhone ="133****1658"; //号码，手机
    private String numberEmail = "105****ddd@qq.com"; //号码，邮箱
    private String captcha; //验证码，手机或者邮箱
    //    private PopWindowGetCode popWindowGetCode;
    private CustomeDialog getCodeDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_pay_password);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);
        binding.setClickListener(this);
//        popWindowGetCode = new PopWindowGetCode(R.layout.pop_home_get_code, SecurityPayPasswordActivity.this);

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(SecurityPayPasswordActivity.this, getResources().getColor(R.color.color_slide_bg));
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
                LogUtils.d(TAG, "获取手机验证码失败：" + e.toString());
                super.onError(e);
            }


        }), SecurityPayPasswordActivity.this, phone, areaCode));
    }


    /**
     * 获取邮箱验证码
     */
    private void getEmailCode(String email,String sid) {

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


        }), SecurityPayPasswordActivity.this, email,sid));
    }



    /**
     * 修改登录密码
     *
     * @param request
     */
    private void modifyLoginPsw(ModifyPswRequest request) {

        HttpManager.getInstance().doHttpDeal(new ModifyLoginPswApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "修改成功:" + result.toString());
                    goTo(OnlineWalletLoginActivity.class);
                    finish();
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
                LogUtils.d(TAG, "修改失败：" + e.toString());
                super.onError(e);
            }


        }), SecurityPayPasswordActivity.this, request));
    }

    /**
     * 开始修改
     */
    private void startModify() {

        newPwd = binding.etPwdNew.getText().toString();
        oldPwd = binding.etPwdOld.getText().toString();
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
        MyVerificationCode vfGetCode = view.findViewById(R.id.vf_get_code);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_email:
                        type = 1;
                        tvNumber.setText(numberEmail);
                        etCode.setHint(R.string.sc_dialog_email_code_des);
                        tvTitle.setText(R.string.sc_dialog_title_email);
                        break;
                    case R.id.rb_phone:
                        type = 2;
                        tvNumber.setText(numberPhone);
                        etCode.setHint(R.string.sc_dialog_phone_code_des);
                        tvTitle.setText(R.string.sc_dialog_title_phone);
                        break;
                }
            }
        });

        vfGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( type == 1){

                }else if(type ==2){

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
                    captcha = etCode.getText().toString();
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

            case R.id.btn_sure:
                showGetCodeView();
                break;
        }
    }
    
    
    
}
