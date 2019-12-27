package com.mirko.alsc.ui.wallet.code;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityCodeWalletTypeBinding;
import com.mirko.alsc.ui.wallet.online.OnlineWalletLoginActivity;
import com.mirko.alsc.ui.wallet.online.OnlineWalletMakeActivity;

/**
 * 创建钱包类型
 */
public class CodeWalletTypeActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityCodeWalletTypeBinding binding;
    private int switchType;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_type);
        binding.setClickListener(this);
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {
        switchBackground(switchType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_code_wallet:
                switchBackground(0);
                break;
            case R.id.ll_online_wallet:
                switchBackground(1);
                break;
            case R.id.btn_next:
                jump2NextActivity();
                break;
            case R.id.btn_login:
                goTo(OnlineWalletLoginActivity.class);
                break;
        }
    }

    private void jump2NextActivity() {
        if (switchType == 0) {
            goTo(CodeWalletCreateActivity.class);
        } else if (switchType == 1) {
            goTo(OnlineWalletMakeActivity.class);
        }
    }

    /**
     * 点击切换不同状态
     *
     * @param type 0  跨链钱包蓝色状态
     *             1  在线钱包黄色状态
     */
    private void switchBackground(int type) {
        switchType = type;
        if (type == 0) {
            binding.llCodeWallet.setBackgroundResource(R.color.blue);
            binding.btnNext.setBackgroundResource(R.color.blue);
            binding.llOnlineWallet.setBackgroundResource(R.color.yahei_middle);
            binding.tvWalletType.setTextColor(getResources().getColor(R.color.blue));
            binding.btnLogin.setTextColor(getResources().getColor(R.color.blue));
        } else if (type == 1) {
            binding.llCodeWallet.setBackgroundResource(R.color.yahei_middle);
            binding.btnNext.setBackgroundResource(R.color.yellow);
            binding.llOnlineWallet.setBackgroundResource(R.color.yellow);
            binding.tvWalletType.setTextColor(getResources().getColor(R.color.yellow));
            binding.btnLogin.setTextColor(getResources().getColor(R.color.yellow));
        }
    }
}
