package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.alsc.net.db.bean.BtcWallet;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.interact.FetchWalletInteract;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityBtcCollectBinding;
import com.mirko.alsc.databinding.ActivityHotWalletAlscCollectBinding;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.androidutil.utils.ThreadUtils;

/**
 * 比特币收款
 */
public class BtcCollectActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityBtcCollectBinding binding;
    private FetchWalletInteract fetchWalletInteract;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_btc_collect);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.wh_btc_collect));
        fetchWalletInteract = new FetchWalletInteract();
        fetchWalletInteract.findDefaultBTCByAddress("").subscribe(this::onSuccess, this::onError);
    }

    private void onSuccess(BtcWallet btcWallet) {
        binding.address.setText(btcWallet.getAddress());
        ThreadUtils.executeBySingle(new ThreadUtils.Task<Bitmap>() {
            @Override
            public Bitmap doInBackground() throws Throwable {
                Bitmap bitmap = ComUtils.createQRImage(btcWallet.getAddress());
                return bitmap;
            }

            @Override
            public void onSuccess(Bitmap result) {
                if (result != null) {
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

    private void onError(Throwable e) {
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
