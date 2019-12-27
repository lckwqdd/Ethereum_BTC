package com.mirko.alsc.ui.slide;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.BindEmailApi;
import com.alsc.net.api.UpdateNameApi;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.request.BindEmailRequest;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivitySecurityUpdateNameBinding;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.alsc.utils.UrlRequstUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/27.
 * 更改名称
 */
public class SecurityUpdateNameActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "SecurityUpdateNameActivity";
    ActivitySecurityUpdateNameBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_security_update_name);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.setClickListener(this);
        binding.titleBar.setOnLeftClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(SecurityUpdateNameActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {

    }


    private void save() {

        String nickName = binding.etName.getText().toString();
        updateName(ComUtils.getTokenCache(),nickName);
    }

    /**
     * 修改名称
     *
     */
    private void updateName(String token,String name) {

        HttpManager.getInstance().doHttpDeal(new UpdateNameApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                finish();
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

        }), SecurityUpdateNameActivity.this, token,name));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();
                break;
            case R.id.btn_save:
                save();
                break;
        }
    }
}
