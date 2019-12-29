package com.mirko.alsc.ui.slide;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.LoginApi;
import com.alsc.net.api.RecommendUserApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.LoginResultEntity;
import com.alsc.net.bean.entity.RecommendUserEntity;
import com.alsc.net.bean.request.ListPageRequest;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.net.util.ConstantUrl;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.MainActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityInviteFriendsBinding;
import com.mirko.alsc.databinding.ActivitySecuritySettingBinding;
import com.mirko.alsc.ui.wallet.online.OnlineWalletLoginActivity;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.utils.DateUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * 邀请好友
 */
public class InviteFriendsActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "AboutActivity";
    ActivityInviteFriendsBinding binding;
    private UserInfoResult userInfo;
    private ListPageRequest request = new ListPageRequest();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invite_friends);
        userInfo = (UserInfoResult) getIntent().getSerializableExtra(Constant.EXTRA_KEY_USER_INFO);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);
        binding.tvInvitedUrl.setText(ConstantUrl.INVITE_URL.replace
                ("{1}",userInfo.getUid()+"")
                .replace("{2}","en"));
        binding.tvInvitedCode.setText(userInfo.getUid()+"");

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(InviteFriendsActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {
        getInvitedCounts();
    }

    /**
     * 获取邀请数量
     */
    private void getInvitedCounts(){

        request.setToken(ComUtils.getTokenCache());
        request.setPage_size(5);
        request.setPage_index(1);
        HttpManager.getInstance().doHttpDeal(new RecommendUserApi((new HttpOnNextListener<RecommendUserEntity>() {
            @Override
            public void onNext(RecommendUserEntity result) {

                if (result != null) {
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
                LogUtils.d(TAG, "登录失败：" + e.toString());
                super.onError(e);
            }

        }), InviteFriendsActivity.this, request));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG,"返回点击");
                onBackPressed();
                break;
        }
    }
}
