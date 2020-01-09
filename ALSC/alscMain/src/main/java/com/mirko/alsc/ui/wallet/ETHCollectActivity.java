package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.alsc.net.db.bean.ETHWallet;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.interact.FetchWalletInteract;
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
    private FetchWalletInteract fetchWalletInteract;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hot_wallet_alsc_collect);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.eth_collect));
        fetchWalletInteract = new FetchWalletInteract();
        fetchWalletInteract.findDefault().subscribe(this::onSuccess, this::onError);//找到当前以太坊钱包
    }
    private void onSuccess(ETHWallet ethWallet) {
        binding.address.setText(ethWallet.getAddress());
        ThreadUtils.executeBySingle(new ThreadUtils.Task<Bitmap>() {
            @Override
            public Bitmap doInBackground() throws Throwable {
                Bitmap bitmap = ComUtils.createQRImage(binding.address.getText().toString().trim());
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
