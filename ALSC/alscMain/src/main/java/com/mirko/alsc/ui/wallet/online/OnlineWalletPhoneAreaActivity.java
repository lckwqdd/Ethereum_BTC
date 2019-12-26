package com.mirko.alsc.ui.wallet.online;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alsc.net.api.PhoneAreaApi;
import com.alsc.net.bean.entity.PhoneCodeResultEntity;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.PhoneCodeAdapter;
import com.mirko.alsc.adapter.listener.RecycleViewItemClickListener;
import com.alsc.net.bean.PhoneCodeInfo;
import com.mirko.alsc.databinding.ActivityOnlineWalletPhoneAreaBinding;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mirko on 2019/12/25.
 * 手机地区选择
 */
public class OnlineWalletPhoneAreaActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "OnlineWalletPhoneAreaActivity";
    ActivityOnlineWalletPhoneAreaBinding binding;
    private PhoneCodeAdapter adapter;
    private List<PhoneCodeInfo> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_phone_area);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(OnlineWalletPhoneAreaActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
        binding.setClickListener(this);

        adapter = new PhoneCodeAdapter(OnlineWalletPhoneAreaActivity.this, datas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OnlineWalletPhoneAreaActivity.this);
        binding.rvPhoneArea.setLayoutManager(layoutManager);
        binding.rvPhoneArea.setAdapter(adapter);
        adapter.setRecycleViewItemClickListener(new RecycleViewItemClickListener() {
            @Override
            public void OnItemOnclick(View view, int position) {

                Intent intent = new Intent();
                intent.putExtra("phone_code", datas.get(position).getCode());
                intent.putExtra("area_name", datas.get(position).getCountry());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void loadData() {
        getPhoneCodeData();
    }


    private void getPhoneCodeData(){

        HttpManager.getInstance().doHttpDeal(new PhoneAreaApi((new HttpOnNextListener<PhoneCodeResultEntity>() {
            @Override
            public void onNext(PhoneCodeResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取图片验证码成功:" + result.getPhone_code().size());
                    if (result.getPhone_code() != null){
                        datas.clear();
                        datas.addAll(result.getPhone_code());
                        adapter.notifyDataSetChanged();
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
                LogUtils.d(TAG, "注册失败：" + e.toString());
                super.onError(e);
            }


        }), OnlineWalletPhoneAreaActivity.this));
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
