package com.mirko.alsc.ui.wallet.code;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityCodeWalletBackupWordBinding;


/**
 * 跨链钱包助记池备份
 */
public class CodeWalletMnemonicBackupActivity extends AlscBaseActivity implements View.OnClickListener {
    private static final int VERIFY_MNEMONIC_BACKUP_REQUEST = 1101;
    private ActivityCodeWalletBackupWordBinding binding;
    private long walletId;
    private String walletMnemonic;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_backup_word);
        binding.btnNext.setOnClickListener(this);
        binding.commonHeader.codeHeaderName.setText(getString(R.string.wc_backup));
        binding.commonHeader.codeHeaderTv1.setText(getString(R.string.wc_backup_single));
        binding.commonHeader.codeHeaderTv2.setText(getString(R.string.wc_backup_double));
    }

    @Override
    public void initAttrs() {
        Intent intent = getIntent();
        walletId = intent.getLongExtra(Constants.walletId, -1);
        walletMnemonic = intent.getStringExtra(Constants.walletMnemonic);
        binding.tvMnemonic.setText(walletMnemonic);
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
        Intent intent = new Intent(this, CodeWalletValidateActivity.class);
        intent.putExtra(Constants.walletMnemonic, walletId);
        intent.putExtra(Constants.walletMnemonic, walletMnemonic);
        startActivityForResult(intent, VERIFY_MNEMONIC_BACKUP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VERIFY_MNEMONIC_BACKUP_REQUEST) {
            if (data != null) {
                finish();
            }
        }
    }
}
