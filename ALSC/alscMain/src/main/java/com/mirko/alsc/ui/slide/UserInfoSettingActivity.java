package com.mirko.alsc.ui.slide;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.HomeMsgApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityUserInfoSettingBinding;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.alsc.utils.UrlRequstUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


/**
 * Created by Mirko on 2019/12/22.
 * 个人信息设置页面
 */
public class UserInfoSettingActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "UserInfoSettingActivity";
    ActivityUserInfoSettingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info_setting);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHomeData(UserInfoSettingActivity.this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(UserInfoSettingActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        //头像
        binding.userInfoHeadImg.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this,SecurityLoginPasswordActivity.class));
            }
        });
        //昵称
        binding.userInfoNick.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this,SecurityUpdateNameActivity.class));
            }
        });
        //id号
        binding.userInfoId.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this,SecurityGoogleActivity.class));
            }
        });
        //性别
        binding.userInfoSex.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this,SecurityGoogleActivity.class));
            }
        });
        //二维码
        binding.userInfoQrcode.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this, SecurityEmailNoBindActivity.class));
            }
        });
        //地区
        binding.userInfoArea.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoSettingActivity.this, SecurityEmailNoBindActivity.class));
            }
        });
    }

    @Override
    public void loadData() {

    }

    /**
     * 加载首页数据
     */
    public  void loadHomeData(RxAppCompatActivity activity) {

        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new HomeMsgApi((new HttpOnNextListener<HomeMsgResultEntity>() {
            @Override
            public void onNext(HomeMsgResultEntity result) {
                if (result != null) {
                    if (result.getUser_info() != null){
                        UserInfoResult userInfo = result.getUser_info();
                        CacheManager.UserInfoResult.set(result.getUser_info());
                        updateView(userInfo);
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
                super.onError(e);
            }


        }), activity, token));
    }

    /**
     * 更新View
     */
    private void updateView(UserInfoResult userInfo){
        binding.userInfoNick.setTvRightText(userInfo.getUname());
        binding.userInfoId.setTvRightText(userInfo.getUid()+"");
        binding.userInfoId.setTvRightColor(getResources().getColor(R.color.white));
        binding.userInfoNick.setTvRightColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG,"返回点击");
                onBackPressed();

        }
    }
}
