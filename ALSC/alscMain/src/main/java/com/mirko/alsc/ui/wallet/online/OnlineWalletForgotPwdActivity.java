package com.mirko.alsc.ui.wallet.online;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.PicCodeApi;
import com.alsc.net.bean.entity.PicCodeResultEntity;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletForgetPwdBinding;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.utils.android.image.ImageUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/26.
 * 找回密码
 */
public class OnlineWalletForgotPwdActivity extends AlscBaseActivity implements View.OnClickListener {

    private static final String TAG = "OnlineWalletForgotPswActivity";
    ActivityOnlineWalletForgetPwdBinding binding;

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
        getPicCaptcha();
    }



    /**
     * 获取图片验证码
     */
    private void getPicCaptcha() {

        HttpManager.getInstance().doHttpDeal(new PicCodeApi((new HttpOnNextListener<PicCodeResultEntity>() {
            @Override
            public void onNext(PicCodeResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取图片验证码成功:" + result.getCode().toString());
                    Bitmap bitmap = ImageUtils.base64ToBitmap(result.getCode());
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
                LogUtils.d(TAG, "获取图片验证码失败：" + e.toString());
                super.onError(e);
            }


        }), OnlineWalletForgotPwdActivity.this));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG,"返回点击");
                onBackPressed();
                break;
            case R.id.btn_next:
                goTo(OnlineWalletResetPwdActivity.class);
                break;

        }
    }
}
