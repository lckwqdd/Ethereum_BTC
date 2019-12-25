package com.mirko.alsc.ui.wallet.online;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletMakeBinding;
import com.mirko.alsc.databinding.ActivityOnlineWalletPhoneValidateBinding;

/**
 * 在线钱包手机验证
 */
public class OnlineWalletPhoneValidateActivity extends AlscBaseActivity {
    private ActivityOnlineWalletPhoneValidateBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_phone_validate);
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {

    }
}
