package com.mirko.alsc.ui.devote;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.DevoteCheckPayPwdApi;
import com.alsc.net.api.DevoteCreatApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityDevoteCygxBinding;
import com.mirko.alsc.databinding.ActivityDevoteHomeBinding;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/28.
 * 参与贡献
 */
public class DevoteCygxActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "DevoteHomeActivity";
    ActivityDevoteCygxBinding binding;

    private UserInfoResult userInfo = new UserInfoResult();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_devote_cygx);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.setClickListener(this);
        binding.titleBar.setOnLeftClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(DevoteCygxActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        loadFragmentData();
    }

    /**
     * 加载fragment数据
     */
    private void loadFragmentData(){

    }

    /**
     * 参与贡献
     */

    private void devoteCreatApi(float usdt) {
        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new DevoteCreatApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                ToastHelper.alert(DevoteCygxActivity.this, "参与贡献成功");
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


        }), DevoteCygxActivity.this, token, usdt));
    }


    /**
     * 验证密码
     */
    private void devoteCheckPayPwd(String pwd) {
        String token = ComUtils.getTokenCache();
        float usdt = Float.valueOf(binding.etGxeCount.getText().toString());
        HttpManager.getInstance().doHttpDeal(new DevoteCheckPayPwdApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                devoteCreatApi(usdt);
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


        }), DevoteCygxActivity.this, token, pwd));
    }




    private void showDialog() {
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();

                break;

            case R.id.btn_cygx:
                showDialog();
                break;

        }
    }
}
