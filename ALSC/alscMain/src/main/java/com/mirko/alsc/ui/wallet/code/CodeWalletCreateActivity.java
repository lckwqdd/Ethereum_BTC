package com.mirko.alsc.ui.wallet.code;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.interact.CreateWalletInteract;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.alsc.wallet.utils.WalletDaoUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityCodeWalletMakeBinding;

/**
 * 创建跨链钱包
 */
public class CodeWalletCreateActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityCodeWalletMakeBinding binding;
    private CreateWalletInteract createWalletInteract;
    private static final int CREATE_WALLET_RESULT = 2202;
    private static final int LOAD_WALLET_REQUEST = 1101;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_make);
        binding.btnNext.setOnClickListener(this);
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
                createWallet();
                break;
        }
    }


    /**
     * 创建生成钱包
     */
    private void createWallet() {
        String walletName = binding.account.getText().toString().trim();
        String walletPwd = binding.password.getText().toString().trim();
        String confirmPwd = binding.confirmPassword.getText().toString().trim();
        boolean verifyWalletInfo = verifyInfo(walletName, walletPwd, confirmPwd);
        if (verifyWalletInfo) {
            createWalletInteract.create(walletName, walletPwd, confirmPwd).subscribe(this::jumpToWalletBackUp, this::showError);
        }
    }

    private boolean verifyInfo(String walletName, String walletPwd, String confirmPwd) {
        if (WalletDaoUtils.walletNameChecking(walletName)) {
            ToastUtils.showToast(R.string.create_wallet_name_repeat_tips);
            return false;
        } else if (TextUtils.isEmpty(walletName)) {
            ToastUtils.showToast(R.string.create_wallet_name_input_tips);
            return false;
        } else if (TextUtils.isEmpty(walletPwd)) {
            ToastUtils.showToast(R.string.create_wallet_pwd_input_tips);
            return false;
        } else if (TextUtils.isEmpty(confirmPwd) || !TextUtils.equals(confirmPwd, walletPwd)) {
            ToastUtils.showToast(R.string.create_wallet_pwd_confirm_input_tips);
            return false;
        }
        return true;
    }

    public void jumpToWalletBackUp(ETHWallet wallet) {
        String walletName = binding.account.getText().toString().trim();
        String walletPwd = binding.password.getText().toString().trim();
        String confirmPwd = binding.confirmPassword.getText().toString().trim();
        createWalletInteract.createBtc(walletName, walletPwd, confirmPwd).subscribe(this::jumpToWalletBackUp, this::showError);
    }

    public void jumpToWalletBackUp(BtcWallet wallet) {

        setResult(CREATE_WALLET_RESULT, new Intent());
        Intent intent = new Intent(this, CodeWalletBackUpActivity.class);
        intent.putExtra(Constants.walletId, wallet.getId());
        intent.putExtra(Constants.walletPwd, wallet.getPassword());
        intent.putExtra(Constants.walletAddress, wallet.getAddress());
        intent.putExtra(Constants.walletName, wallet.getName());
        intent.putExtra(Constants.walletMnemonic, wallet.getMnemonic());
        intent.putExtra(Constants.firstAccount, true);

        startActivity(intent);
        finish();
    }


    public void showError(Throwable errorInfo) {
        LogUtils.d("创建异常:" + errorInfo.toString());
        ToastUtils.showToast(errorInfo.toString());
    }
}
