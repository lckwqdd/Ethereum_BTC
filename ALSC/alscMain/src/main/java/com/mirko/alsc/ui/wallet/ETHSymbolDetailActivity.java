package com.mirko.alsc.ui.wallet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityAddWalletBinding;
import com.mirko.alsc.databinding.ActivitySymbolDetailBinding;

/**
 * 充币成功界面
 */
public class ETHSymbolDetailActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivitySymbolDetailBinding binding;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_symbol_detail);
        binding.commonHeader.tvHeaderMiddle.setVisibility(View.GONE);
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
