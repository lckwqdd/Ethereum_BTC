package com.mirko.alsc.ui.wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityBtcTransferBinding;
import com.mirko.alsc.databinding.ActivityHotWalletAlscTransferBinding;

/**
 * 比特币转账
 */
public class BtcTransferActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityBtcTransferBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_btc_transfer);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.wh_btc_transfer));
        binding.commonHeader.ivHeaderRight.setVisibility(View.VISIBLE);
        binding.commonHeader.ivHeaderRight.setImageResource(R.mipmap.icon_scan);
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        sendTranstion();
    }

    /**
     * 发送比特币交易
     */
    private void sendTranstion() {

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
