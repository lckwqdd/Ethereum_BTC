package com.mirko.alsc.ui.wallet.online;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityOnlineWalletPhoneValidateBinding;
import com.mirko.alsc.databinding.ActivityOnlineWalletSetPwdBinding;

/**
 * 在线钱包设置密码
 */
public class OnlineWalletSetPwdActivity extends AlscBaseActivity {
    private ActivityOnlineWalletSetPwdBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_online_wallet_set_pwd);
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {

    }
}
