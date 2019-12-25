package com.mirko.alsc.ui.wallet.online;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityCodeWalletMakeBinding;
import com.mirko.alsc.databinding.ActivityOnlineWalletMakeBinding;

/**
 * 创建在线钱包
 */
public class OnlineWalletMakeActivity extends AlscBaseActivity {
    private ActivityOnlineWalletMakeBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_make);
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {

    }
}
