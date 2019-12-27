package com.mirko.alsc.ui.wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.alsc.net.db.bean.ETHWallet;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.C;
import com.alsc.wallet.entity.ErrorEnvelope;
import com.alsc.wallet.interact.FetchWalletInteract;
import com.alsc.wallet.utils.LogUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.TokensAdapter;
import com.mirko.alsc.databinding.ActivityTotalAssetsBinding;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_total_assets);
        binding.ivAddConcact.setOnClickListener(this);
        binding.ivSee.setOnClickListener(this);
        binding.ivAddWallet.setOnClickListener(this);
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.totalAssetsRv.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new TokensAdapter(R.layout.list_item_total_assets, new ArrayList<>());  //
        binding.totalAssetsRv.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(((adapter, view, position) -> {
//            Intent intent = new Intent(this, PropertyDetailActivity.class);
//            Token token = tokenItems.get(position);
//            intent.putExtra(C.EXTRA_BALANCE, token.balance);
//            intent.putExtra(C.EXTRA_ADDRESS, currEthWallet.getAddress());
//            intent.putExtra(C.EXTRA_CONTRACT_ADDRESS, token.tokenInfo.address);
//            intent.putExtra(C.EXTRA_SYMBOL, token.tokenInfo.symbol);
//            intent.putExtra(C.EXTRA_DECIMALS, token.tokenInfo.decimals);
//            startActivity(intent);
        }));

        fetchWalletInteract = new FetchWalletInteract();
//      fetchWalletInteract.fetch().subscribe(this::onSuccess, this::onError);//找到所有以太坊钱包
        fetchWalletInteract.findDefault().subscribe(this::onSuccess, this::onError);//找到当前以太坊钱包
        fetchWalletInteract.findDefaultBtc().subscribe();//找到当前以太坊钱包
    }

    private void onSuccess(ETHWallet ethWallet) {
        currentEthWallet = ethWallet;
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
