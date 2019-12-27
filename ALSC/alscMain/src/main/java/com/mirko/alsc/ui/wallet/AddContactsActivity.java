package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.entity.Address;
import com.alsc.wallet.utils.ToastUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityAddContactsBinding;
import com.mirko.alsc.databinding.ActivityAddWalletBinding;

/**
 * 添加联系人
 */
public class AddContactsActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityAddContactsBinding binding;
    private static final int QRCODE_SCANNER_REQUEST = 1100;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_contacts);
        binding.btnScan.setOnClickListener(this);
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
            case R.id.btn_scan:
                Intent intent = new Intent(this, ScanQrCodeActivity.class);
                startActivityForResult(intent, QRCODE_SCANNER_REQUEST);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCODE_SCANNER_REQUEST) {
            if (data != null) {
                String scanResult = data.getStringExtra("scan_result");
                // 对扫描结果进行处理
                parseScanResult(scanResult);
            }
        }
    }

    private void parseScanResult(String result) {
        if (result.contains(":") && result.contains("?")) {  // 符合协议格式
            String[] urlParts = result.split(":");
            if (urlParts[0].equals("ethereum")) {
                urlParts = urlParts[1].split("\\?");
                fillAddress(urlParts[0]);
                // ?contractAddress=0xdxx & decimal=1 & value=100000
//                 String[] params = urlParts[1].split("&");
//                for (String param : params) {
//                    String[] keyValue = param.split("=");
//                }

            }


        } else {  // 无格式， 只有一个地址
            fillAddress(result);
        }


    }

    private void fillAddress(String addr) {
        try {
            new Address(addr);
            binding.tvAddress.setText(addr);
        } catch (Exception e) {
            ToastUtils.showToast(R.string.addr_error_tips);
        }
    }
}
