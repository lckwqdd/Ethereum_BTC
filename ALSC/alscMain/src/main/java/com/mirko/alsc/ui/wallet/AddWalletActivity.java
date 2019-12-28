package com.mirko.alsc.ui.wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alsc.net.db.bean.ETHWallet;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.interact.FetchWalletInteract;
import com.alsc.wallet.utils.LogUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.TokensAdapter;
import com.mirko.alsc.databinding.ActivityAddWalletBinding;
import com.mirko.alsc.databinding.ActivityTotalAssetsBinding;

import java.util.ArrayList;

/**
 * 添加钱包界面
 */
public class AddWalletActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityAddWalletBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_wallet);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.total_assets_add));
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
