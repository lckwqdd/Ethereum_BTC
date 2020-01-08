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
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.ETHWalletUtils;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.alsc.wallet.utils.WalletDaoUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityCodeWalletImportBinding;
import com.mirko.alsc.ui.wallet.BtcTranslateAndCollectActivity;
import com.mirko.alsc.ui.wallet.ETHTranslateAndCollectActivity;
import com.mirko.alsc.utils.ComUtils;

/**
 * 钱包导入页面
 */
public class CodeWalletImportActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityCodeWalletImportBinding binding;
    private CreateWalletInteract createWalletInteract;
    private String ethType = ETHWalletUtils.ETH_JAXX_TYPE;
    private int position;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_import);
        binding.btnNext.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.wallet_import_wallet));
        createWalletInteract = new CreateWalletInteract();

        Intent intent = getIntent();
        position = intent.getIntExtra(Constants.pointPosition, 0);
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
        LogUtils.d("position:" + position);
        if (position == 0 || position == 2) {
            if (checkETHLegal(mnemonic, pwd, surePwd)) {
                createWalletInteract.loadWalletByMnemonic(ethType, mnemonic, surePwd).subscribe(this::loadSuccess, this::onError);
            }
        } else if (position == 1 || position == 3) {
            if (checkBTCLegal(mnemonic, pwd, surePwd)) {
                try {
                    BtcWallet btcWallet = ComUtils.importMnemonic(mnemonic);
                    ToastUtils.showToast(getString(R.string.import_wallet_success));
                    goTo(BtcTranslateAndCollectActivity.class);
                    finish();
                    LogUtils.d("比特币导入:" + GsonUtils.toJson(btcWallet));
                } catch (Exception e) {
                    LogUtils.d("导入比特币钱包异常:" + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }


    private void onSuccess(BtcWallet btcWallet) {
        ToastUtils.showToast(getString(R.string.import_wallet_success));
        goTo(BtcTranslateAndCollectActivity.class);
        finish();
    }

    private void loadSuccess(ETHWallet ethWallet) {
        ToastUtils.showToast(getString(R.string.import_wallet_success));
        goTo(ETHTranslateAndCollectActivity.class);
        finish();
    }

    private void onError(Throwable throwable) {
        ToastUtils.showToast(getString(R.string.import_wallet_fail));
    }

    private boolean checkETHLegal(String mnemonic, String pwd, String surePwd) {
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

    private boolean checkBTCLegal(String mnemonic, String pwd, String surePwd) {
        if (TextUtils.isEmpty(mnemonic)) {
            ToastUtils.showToast(R.string.load_wallet_by_mnemonic_input_tip);
            return false;
        } else if (!WalletDaoUtils.isValid(mnemonic)) {
            ToastUtils.showToast(R.string.load_wallet_by_mnemonic_input_tip);
            return false;
        } else if (BTCWalletDaoUtils.checkRepeatByMenmonic(mnemonic)) {
            ToastUtils.showToast(R.string.load_wallet_already_exist);
            return false;
        } else if (TextUtils.isEmpty(surePwd) || !TextUtils.equals(pwd, surePwd)) {
            ToastUtils.showToast(R.string.create_wallet_pwd_confirm_input_tips);
            return false;
        }
        return true;
    }
}
