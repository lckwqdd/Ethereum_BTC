package com.mirko.alsc.ui.devote;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alsc.net.api.DevoteCheckPayPwdApi;
import com.alsc.net.api.DevoteCreatApi;
import com.alsc.net.api.DevoteRealPriceApi;
import com.alsc.net.api.DevoteSuperApi;
import com.alsc.net.bean.CapitalData;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.RealPriceResultEntity;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.C;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.HomePagerAdapter;
import com.mirko.alsc.databinding.ActivityDevoteCjjdBinding;
import com.mirko.alsc.databinding.ActivityDevoteHomeBinding;
import com.mirko.alsc.ui.devote.fragment.DevoteHomeFragment;
import com.mirko.alsc.ui.devote.fragment.DevoteShanDuiFragment;
import com.mirko.alsc.ui.entity.TabEntity;
import com.mirko.alsc.ui.fragment.HomeFragment;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.alsc.utils.Constant;
import com.mirko.alsc.views.MyVerificationCode;
import com.mirko.alsc.views.ViewPagerScroller;
import com.mirko.androidutil.encryption.MD5Utils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.CustomeDialog;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mirko on 2019/12/28.
 * 超级节点
 */
public class DevoteCjjdActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "DevoteHomeActivity";
    ActivityDevoteCjjdBinding binding;

    private float realPrice;
    private CapitalData capitalData = new CapitalData();
    private CustomeDialog walletPwdDialog;
    private EditText etPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_devote_cjjd);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.setClickListener(this);
        binding.titleBar.setOnLeftClickListener(this);
        capitalData = ComUtils.getCapitalData();
        if(capitalData != null){
            binding.tvTotalPrice.setText(capitalData.getMax_sinvestment() +"");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(DevoteCjjdActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {
        devoteRealPriceApi();
    }

    /**
     * 超级节点
     */
    private void devoteSuperApi() {

        HttpManager.getInstance().doHttpDeal(new DevoteSuperApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                ToastHelper.alert(DevoteCjjdActivity.this, "参与节点成功");
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
                super.onError(e);
            }


        }), DevoteCjjdActivity.this, ComUtils.getTokenCache(),realPrice));
    }


    /**
     * 获取实时价格
     */

    private void devoteRealPriceApi() {
        HttpManager.getInstance().doHttpDeal(new DevoteRealPriceApi((new HttpOnNextListener<RealPriceResultEntity>() {
            @Override
            public void onNext(RealPriceResultEntity result) {
                binding.tvRealPrice.setText(result.getResult().getSymbol().substring(0, 4) +
                        " ≈ " + result.getResult().getPrice() + result.getResult().getSymbol().substring(4, 8));
                realPrice = Float.parseFloat(result.getResult().getPrice());
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
                super.onError(e);
            }


        }), DevoteCjjdActivity.this));
    }



    /**
     * 验证密码
     */
    private void devoteCheckPayPwd(String pwd) {
        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new DevoteCheckPayPwdApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                    devoteSuperApi();
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
                super.onError(e);
            }


        }), DevoteCjjdActivity.this, token, pwd));
    }


    /**
     * 显示获取验证码页面
     */
    private void showDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_wallet_pwd, null);
        walletPwdDialog = new CustomeDialog(this, view);
        walletPwdDialog.setCanceledOnTouchOutside(false);
        walletPwdDialog.setGravity(Gravity.BOTTOM);
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = walletPwdDialog.getWindow().getAttributes();
//        lp.width = (int) (display.getWidth()); //设置宽度
//        walletPwdDialog.getWindow().setAttributes(lp);

        etPwd = view.findViewById(R.id.et_pwd); //验证码
        String pwd = etPwd.getText().toString();
        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                devoteCheckPayPwd(MD5Utils.getMD5Code(pwd));
                if (walletPwdDialog != null) {
                    walletPwdDialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (walletPwdDialog != null) {
                    walletPwdDialog.dismiss();
                }
            }
        });

        walletPwdDialog.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();

                break;

            case R.id.btn_cjjd:
                showDialog();
                break;

        }
    }
}
