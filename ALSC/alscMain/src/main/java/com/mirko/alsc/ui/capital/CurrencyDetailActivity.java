package com.mirko.alsc.ui.capital;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alsc.net.api.FindPasswordApi;
import com.alsc.net.api.WTransferRecordApi;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.TransferRecordeEntity;
import com.alsc.net.bean.request.FindPasswordRequest;
import com.alsc.net.bean.request.ListPageRequest;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.CurrencyAdapter;
import com.mirko.alsc.adapter.CurrencyDetailAdapter;
import com.mirko.alsc.adapter.listener.RecycleViewItemClickListener;
import com.mirko.alsc.bean.CurrencyData;
import com.mirko.alsc.databinding.ActivityAboutBinding;
import com.mirko.alsc.databinding.ActivityCurrencyDetailBinding;
import com.mirko.alsc.ui.entity.TabWalletEntity;
import com.mirko.alsc.ui.wallet.online.OnlineWalletLoginActivity;
import com.mirko.alsc.ui.wallet.online.OnlineWalletResetPwdActivity;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mirko on 2019/12/23.
 * 币种详细页面
 */
public class CurrencyDetailActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "CurrencyDetailActivity";
    ActivityCurrencyDetailBinding binding;
    private List<CurrencyData> currencyDatas;
    private CurrencyDetailAdapter adapter;
    private ListPageRequest request = new ListPageRequest();
    private int mTitleIds[] = new int[]{
            R.string.capital_transfer,
            R.string.capital_receiveables,
            R.string.capital_all,
            R.string.capital_exchange

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.titleBar.setOnLeftClickListener(this);
        binding.setClickListener(this);

        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        //设置tab的标题、选中图标、未选中图标
        for (int i = 0; i < mTitleIds.length; i++) {
            mTabEntities.add(new TabWalletEntity(getString(mTitleIds[i])));
        }
        binding.ctCapitalTitle.setTabData(mTabEntities);

        currencyDatas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CurrencyData currencyData = new CurrencyData();
            currencyData.setName("BTC");
            currencyDatas.add(currencyData);
        }
        adapter = new CurrencyDetailAdapter(CurrencyDetailActivity.this, currencyDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CurrencyDetailActivity.this);
        binding.rvCurrencyDetail.setLayoutManager(layoutManager);
        binding.rvCurrencyDetail.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setRecycleViewItemClickListener(new RecycleViewItemClickListener() {
            @Override
            public void OnItemOnclick(View view, int position) {
                startActivity(new Intent(CurrencyDetailActivity.this, CurrencyDetailActivity.class));
            }
        });
        binding.ctCapitalTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                LogUtils.d(TAG, "当前选中：" + position);
                if (position == 0) {
                    currencyDatas.clear();
                    for (int i = 0; i < 4; i++) {
                        CurrencyData currencyData = new CurrencyData();
                        currencyData.setName("BTC");
                        currencyDatas.add(currencyData);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    currencyDatas.clear();
                    for (int i = 0; i < 4; i++) {
                        CurrencyData currencyData = new CurrencyData();
                        currencyData.setName("ATO");
                        currencyDatas.add(currencyData);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(CurrencyDetailActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {
        transferRecord();
    }

    /**
     * 转账记录
     */
    private void transferRecord() {

        request.setPage_index(0);
        request.setPage_size(5);
        request.setToken(ComUtils.getTokenCache());
        request.setType(1);
        HttpManager.getInstance().doHttpDeal(new WTransferRecordApi((new HttpOnNextListener<TransferRecordeEntity>() {
            @Override
            public void onNext(TransferRecordeEntity result) {
                LogUtils.d(TAG, "获取成功");
                currencyDatas.clear();
                for (int i = 0; i < result.getList().size(); i++) {
                    CurrencyData currencyData = new CurrencyData();
                    currencyData.setName(result.getList().get(i).getHash() + "");
                    currencyData.setAvailable(result.getList().get(i).getAlsc());
                    currencyData.setTotal(result.getList().get(i).getUsdt());
                    currencyDatas.add(currencyData);
                }
                adapter.notifyDataSetChanged();
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


        }), CurrencyDetailActivity.this, request));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();
                break;
            case R.id.ll_transfer:
                goTo(HotETHTransferActivity.class);
                break;
        }
    }
}
