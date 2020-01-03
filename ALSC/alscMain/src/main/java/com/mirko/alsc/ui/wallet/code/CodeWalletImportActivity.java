package com.mirko.alsc.ui.wallet.code;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.utils.ToastUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityCodeWalletImportBinding;
import com.mirko.alsc.ui.wallet.AlscTranslateAndCollectActivity;

/**
 * 钱包导入页面
 */
public class CodeWalletImportActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityCodeWalletImportBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_import);
        binding.btnNext.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.wallet_import_wallet));
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
        if (checkLegal()) {
            goTo(AlscTranslateAndCollectActivity.class);
            finish();
        }
    }

    private boolean checkLegal() {
        if (TextUtils.isEmpty(binding.tvMnemonic.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.mnemonic_not_null));
            return false;
        }
        return true;
    }
}
