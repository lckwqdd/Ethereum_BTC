package com.mirko.alsc.ui.wallet.code;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityCodeWalletBackupBinding;

/**
 * 跨链钱包备份
 */
public class CodeWalletBackUpActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityCodeWalletBackupBinding binding;
    private long walletId;
    private String walletPwd, walletAddress, walletName, walletMnemonic;
    private boolean firstAccount;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_backup);
        binding.btnNext.setOnClickListener(this);
        binding.commonHeader.codeHeaderName.setText(getString(R.string.wc_backup));
        binding.commonHeader.codeHeaderTv1.setText(getString(R.string.wc_backup_single));
        binding.commonHeader.codeHeaderTv2.setText(getString(R.string.wc_backup_double));
    }

    @Override
    public void initAttrs() {
        Intent intent = getIntent();
        walletId = intent.getLongExtra(Constants.walletId, -1);
        walletPwd = intent.getStringExtra(Constants.walletPwd);
        walletAddress = intent.getStringExtra(Constants.walletAddress);
        walletName = intent.getStringExtra(Constants.walletName);
        walletMnemonic = intent.getStringExtra(Constants.walletMnemonic);
        firstAccount = getIntent().getBooleanExtra(Constants.firstAccount, false);
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
        Intent intent = new Intent(CodeWalletBackUpActivity.this, CodeWalletMnemonicBackupActivity.class);
        intent.putExtra(Constants.walletId, walletId);
        intent.putExtra(Constants.walletMnemonic, walletMnemonic);
        startActivity(intent);
        finish();
    }
}
