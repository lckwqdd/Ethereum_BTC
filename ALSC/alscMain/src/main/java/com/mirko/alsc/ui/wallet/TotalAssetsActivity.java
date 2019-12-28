package com.mirko.alsc.ui.wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.alsc.net.api.AddressRegisterApi;
import com.alsc.net.bean.entity.AddressRegisterResultEntity;
import com.alsc.net.bean.request.AddressRegisterRequest;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.interact.FetchWalletInteract;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.ExpandableItemAdapter;
import com.mirko.alsc.adapter.TokensAdapter;
import com.mirko.alsc.databinding.ActivityTotalAssetsBinding;
import com.mirko.alsc.entity.Level0Item;
import com.mirko.alsc.entity.Level1Item;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 总资产
 */
public class TotalAssetsActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityTotalAssetsBinding binding;
    private LinearLayoutManager linearLayoutManager;
    private TokensAdapter recyclerAdapter;
    private FetchWalletInteract fetchWalletInteract;
    private ETHWallet currentEthWallet;
    private boolean isCanSee;
    private AddressRegisterResultEntity tempAddressRegisterResult;
    private ExpandableItemAdapter adapter;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_total_assets);
        binding.ivAddConcact.setOnClickListener(this);
        binding.ivSee.setOnClickListener(this);
        binding.ivAddWallet.setOnClickListener(this);
        fetchWalletInteract = new FetchWalletInteract();
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        initRecycleDatas();
        fetchWalletInteract.findDefault().subscribe(this::onSuccess, this::onError);//找到当前以太坊钱包
        getCurrentUserInfo(new AddressRegisterRequest(UUID.randomUUID().toString().replace("-", ""), "alsc"));
    }

    private void initRecycleDatas() {
        ArrayList<MultiItemEntity> list = generateData();
        adapter = new ExpandableItemAdapter(list);
        binding.totalAssetsRv.setAdapter(recyclerAdapter);
        adapter.setSubOnClickListener(((v, position) -> {
            ToastUtils.showToast("点击的position:"+position);
        }));
    }

    /**
     * 通过注册地址和对应币种获取对应金额
     *
     * @param addressRegisterRequest
     */
    private void getCurrentUserInfo(AddressRegisterRequest addressRegisterRequest) {
        HttpManager.getInstance().doHttpDeal(new AddressRegisterApi((new HttpOnNextListener<AddressRegisterResultEntity>() {
            @Override
            public void onNext(AddressRegisterResultEntity result) {
                tempAddressRegisterResult = result;
                binding.tvAccount.setText(result.getAssets());
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("通过注册地址异常:" + e.toString());
                super.onError(e);
            }

        }), TotalAssetsActivity.this, addressRegisterRequest));
    }

    private void onSuccess(ETHWallet ethWallet) {
        LogUtils.d("获取数据ETHWallet:" + GsonUtils.toJson(ethWallet));
        currentEthWallet = ethWallet;
    }

    private   ArrayList<MultiItemEntity>  generateData(){
        int lv0Count = 4;
        int lv1Count = 2;
        String[] nameLists={"ALSC","ETH","BTC","USDT"};
        String[] balanceLists={"0","0","0","0"};
        String[] valueLists={"=¥1772.00","=¥0.00","=¥0.00","=¥0.00"};
        int[]    imgsLists={R.drawable.arrow,R.drawable.arrow,R.drawable.arrow,R.drawable.arrow};

        //子类
        String[] subNameLists={"已有的ALSC地址","创建的AlSC地址"};
        String[] subAddressLists={"0x164f65d4fd","0x432423"};
        String[] subBalanceLists={"0","0"};
        String[] subValueLists={"=¥1772.00","=¥0.00"};

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item(imgsLists[i],nameLists[i],balanceLists[i],valueLists[i]);
            for (int j = 0; j < lv1Count; j++) {
                Level1Item lv1 = new Level1Item(subNameLists[j],subAddressLists[j],subBalanceLists[j],subValueLists[j]);
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return  res;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_concact:
                goTo(AddContactsActivity.class);
                break;
            case R.id.iv_add_wallet:
                goTo(AddWalletActivity.class);
                break;
            case R.id.iv_see:
                switchStyle(isCanSee);
                break;
        }
    }

    private void switchStyle(boolean isCanSee) {
    }

    private void onError(Throwable throwable) {
        LogUtils.d("取数据异常:" + throwable.toString());
    }
}
