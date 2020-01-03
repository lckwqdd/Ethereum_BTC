package com.mirko.alsc.ui.wallet.address;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alsc.net.db.bean.ContactBean;
import com.alsc.net.db.helper.ContactBeanHelper;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.entity.Address;
import com.alsc.wallet.utils.ToastUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityAddContactsBinding;
import com.mirko.alsc.databinding.ActivityAddWalletBinding;
import com.mirko.alsc.ui.wallet.ScanQrCodeActivity;

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
        binding.btnNext.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.add_contacts));
//        binding.commonHeader.tvHeaderRight.setText(getString(R.string.save));
//        binding.commonHeader.tvHeaderRight.setVisibility(View.VISIBLE);
//        binding.commonHeader.tvHeaderRight.setOnClickListener(this);
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
            case R.id.btn_next:
                saveContacts();
                break;
        }
    }

    /**
     * 保存联系人信息
     */
    private void saveContacts() {
        String name = binding.name.getText().toString().trim();
        String note = binding.note.getText().toString().trim();
        String address = binding.tvAddress.getText().toString().trim();
        if (detection(name, note, address)) {
            ContactBeanHelper.getInstance().insertObject(new ContactBean(null, name, note, address));
            goTo(AddAddressActivity.class);
            finish();
        }
    }

    private boolean detection(String name, String note, String address) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast(getString(R.string.name_not_null));
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showToast(getString(R.string.address_not_null));
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCODE_SCANNER_REQUEST) {
            if (data != null) {
                String scanResult = data.getStringExtra(Constants.scanResult);
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
