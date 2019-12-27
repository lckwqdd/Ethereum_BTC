package com.mirko.alsc.ui.wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityAddWalletBinding;

/**
 * alsc转账等界面
 */
public class AlscTransferActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityAddWalletBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_wallet);
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
        }
    }
}
