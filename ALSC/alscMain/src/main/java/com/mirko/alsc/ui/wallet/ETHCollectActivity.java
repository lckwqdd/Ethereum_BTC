package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityAddWalletBinding;
import com.mirko.alsc.databinding.ActivityHotWalletAlscCollectBinding;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.androidutil.utils.ThreadUtils;

import static com.mirko.alsc.constant.Constants.btcAddress;

/**
 * 以太坊收款
 */
public class ETHCollectActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityHotWalletAlscCollectBinding binding;
    private String ethAddress;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hot_wallet_alsc_collect);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        Intent intent = getIntent();
        ethAddress=intent.getStringExtra(Constants.walletAddress);
        binding.address.setText(ethAddress);
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        ThreadUtils.executeBySingle(new ThreadUtils.Task<Bitmap>() {
            @Override
            public Bitmap doInBackground() throws Throwable {
                Bitmap bitmap = ComUtils.createQRImage(ethAddress);
                return bitmap;
            }

            @Override
            public void onSuccess(Bitmap result) {
                if(result!=null){
                    binding.ivQrcode.setImageBitmap(result);
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFail(Throwable t) {

            }
        });
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
