package com.mirko.alsc.ui.wallet.online;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletLoginAlscBinding;
import com.mirko.alsc.databinding.ActivityOnlineWalletSetPwdBinding;

/**
 * 在线钱包登录ALSC
 */
public class OnlineWalletLoginAlscActivity extends AlscBaseActivity {
    private ActivityOnlineWalletLoginAlscBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_login_alsc);
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {

    }
}
