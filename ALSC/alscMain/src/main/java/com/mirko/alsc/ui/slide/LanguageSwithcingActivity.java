package com.mirko.alsc.ui.slide;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.bean.entity.NoticeResultEntity;
import com.alsc.net.api.NoticeApi;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityLanguageSwitchingBinding;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/22.
 * 语言切换
 */
public class LanguageSwithcingActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "LanguageSwithcingActivity";
    ActivityLanguageSwitchingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_switching);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(LanguageSwithcingActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {
        getNotice(1,10,1);
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

    private void getNotice(int type, int pageSize, int pageIndex) {

        HttpManager.getInstance().doHttpDeal(new NoticeApi((new HttpOnNextListener<NoticeResultEntity>() {
            @Override
            public void onNext(NoticeResultEntity result) {

                if(result != null){
                    LogUtils.d(TAG, "获取详细成功:"+result.toString());
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
                LogUtils.d(TAG, "获取详细失败：" + e.toString());
                super.onError(e);
            }


        }), LanguageSwithcingActivity.this, type,pageSize,pageIndex));
    }
}
