package com.mirko.alsc.ui.wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityBtcCollectBinding;
import com.mirko.alsc.databinding.ActivityHotWalletAlscCollectBinding;

/**
 * 比特币收款
 */
public class BtcCollectActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityBtcCollectBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_btc_collect);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
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
            case R.id.iv_header_left:
                onBackPressed();
                break;
        }
    }
}
