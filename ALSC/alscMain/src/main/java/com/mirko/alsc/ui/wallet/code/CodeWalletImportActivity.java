package com.mirko.alsc.ui.wallet.code;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alsc.net.db.bean.ETHWallet;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.interact.CreateWalletInteract;
import com.alsc.wallet.utils.ETHWalletUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.alsc.wallet.utils.WalletDaoUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityCodeWalletImportBinding;
import com.mirko.alsc.ui.wallet.AlscTranslateAndCollectActivity;

/**
 * 钱包导入页面
 */
public class CodeWalletImportActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityCodeWalletImportBinding binding;
    private CreateWalletInteract createWalletInteract;
    private String ethType = ETHWalletUtils.ETH_JAXX_TYPE;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_import);
        binding.btnNext.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.wallet_import_wallet));
        createWalletInteract = new CreateWalletInteract();
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
            case R.id.btn_next:
                jump2NextActivity();
                break;
        }
    }

    private void jump2NextActivity() {
        String mnemonic = binding.tvMnemonic.getText().toString().trim();
        String pwd = binding.pwd.getText().toString().trim();
        String surePwd = binding.pwdSure.getText().toString().trim();
        if (checkLegal(mnemonic, pwd, surePwd)) {
            createWalletInteract.loadWalletByMnemonic(ethType, mnemonic, surePwd).subscribe(this::loadSuccess, this::onError);
        }
    }

    private void loadSuccess(ETHWallet ethWallet) {
        goTo(AlscTranslateAndCollectActivity.class);
        finish();
    }

    private void onError(Throwable throwable) {
        ToastUtils.showToast(getString(R.string.import_wallet_fail));
    }

    private boolean checkLegal(String mnemonic, String pwd, String surePwd) {
        if (TextUtils.isEmpty(mnemonic)) {
            ToastUtils.showToast(R.string.load_wallet_by_mnemonic_input_tip);
            return false;
        } else if (!WalletDaoUtils.isValid(mnemonic)) {
            ToastUtils.showToast(R.string.load_wallet_by_mnemonic_input_tip);
            return false;
        } else if (WalletDaoUtils.checkRepeatByMenmonic(mnemonic)) {
            ToastUtils.showToast(R.string.load_wallet_already_exist);
            return false;
        } else if (TextUtils.isEmpty(surePwd) || !TextUtils.equals(pwd, surePwd)) {
            ToastUtils.showToast(R.string.create_wallet_pwd_confirm_input_tips);
            return false;
        }
        return true;
    }
}
