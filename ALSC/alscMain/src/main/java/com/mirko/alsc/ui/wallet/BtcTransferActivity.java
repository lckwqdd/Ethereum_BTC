package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.alsc.net.bean.okgo.AddressEntity;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.btcCoin.btc.KeyPair;
import com.alsc.wallet.btcCoin.btc.UnspentOutputInfo;
import com.alsc.wallet.entity.Address;
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.ETHWalletUtils;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.google.common.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.mirko.alsc.R;
import com.alsc.wallet.bean.GenerateTransactionResult;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityBtcTransferBinding;
import com.mirko.alsc.utils.UrlRequstUtils;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 比特币转账界面
 */
public class BtcTransferActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityBtcTransferBinding binding;
    private String btcAddress, btcPrivateKey, address;
    private static final int QRCODE_SCANNER_REQUEST = 1100;
    private AsyncTask<Void, Void, KeyPair> decodePrivateKeyTask;
    private KeyPair verifiedKeyPairForTx;
    private AsyncTask<Void, Void, ArrayList<UnspentOutputInfo>> decodeUnspentOutputsInfoTask;
    private ArrayList<UnspentOutputInfo> verifiedUnspentOutputsForTx;
    private static final long SEND_MAX = -1;
    private static final long AMOUNT_ERR = -2;
    private long verifiedAmountToSendForTx = -1;
    private AsyncTask<Void, Void, GenerateTransactionResult> generateTransactionTask;
    private String singatureInfo;
    private String uxto;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_btc_transfer);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        binding.commonHeader.ivHeaderRight.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.wh_btc_transfer));
        binding.commonHeader.ivHeaderRight.setVisibility(View.VISIBLE);
        binding.commonHeader.ivHeaderRight.setImageResource(R.mipmap.icon_scan);
        Intent intent = getIntent();
        btcAddress = intent.getStringExtra(Constants.btcAddress);
        btcPrivateKey = intent.getStringExtra(Constants.btcPrivateKey);
        address = intent.getStringExtra(Constants.walletAddress);
        LogUtils.d("以太坊地址:" + address);
        LogUtils.d("比特币地址:" + btcAddress);
        binding.sendAddress.setText(btcAddress);
        LogUtils.d("是否是比特币地址:" + ETHWalletUtils.isBTCValidAddress(btcAddress));
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        getAnotherUxto(btcAddress);
    }

    /**
     * 发送比特币交易
     * 1.用接口获取uxto - 构建交易  -签名 -调用接口广播交易
     */
    private void sendTranstion(String btcAddress) {
        OkGo.<String>get("https://chain.api.btc.com/v3/address/" + btcAddress + "/unspent")                            // 请求方式和请求url
                .tag(this)                   // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body().toString();
                        LogUtils.d("构造的数据uxto: " + response.body());
                        AddressEntity jsonBean = GsonUtils.fromJson(s, new TypeToken<AddressEntity>() {
                        }.getType());
                        LogUtils.d("构造后的数据: " + jsonBean.toString());
                        String uxtx0 = "123456";
                    }

                    @Override
                    public void onError(Response<String> response) {
                        getAnotherUxto(btcAddress);
                        super.onError(response);
                    }
                });
    }

    /**
     * 备选uxto
     */
    private void getAnotherUxto(String btcAddress) {
        String url = "https://blockchain.info/unspent?active=" + btcAddress;
        OkGo.<String>get(url)
                .tag(this)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("get", response.body());
                        uxto = response.body().toString();
                        LogUtils.d("备选uxto: " + response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
            case R.id.iv_header_right:
                Intent intent = new Intent(this, ScanQrCodeActivity.class);
                startActivityForResult(intent, QRCODE_SCANNER_REQUEST);
                break;
            case R.id.btn_next:
                sendTranstion();
                break;
        }
    }


    private void sendTranstion() {
        checkInput();
        Single.fromCallable(() -> {
            LogUtils.d("私钥:" + btcPrivateKey);
            String strs = BTCWalletDaoUtils.signatureBtcTranstion(uxto, btcPrivateKey, binding.sendAddress.getText().toString().trim(),
                    binding.receiveAddress.getText().toString().trim(), Long.parseLong(binding.account.getText().toString().trim()));
            return strs;
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::onSuccess, this::onErrow);
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(binding.account.getText().toString())) {
            ToastUtils.showToast(getString(R.string.tranfer_not_null));
            return false;
        }
        if (!ETHWalletUtils.isBTCValidAddress(binding.receiveAddress.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.btc_receive_not_right));
            return false;
        }
        if (!ETHWalletUtils.isBTCValidAddress(binding.sendAddress.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.btc_send_not_right));
            return false;
        }
        if (TextUtils.isEmpty(uxto)) {
            ToastUtils.showToast(getString(R.string.utxo_not_null));
            return false;
        }
        return true;
    }

    private void onSuccess(String s) {
        LogUtils.d("生成的hex:" + s);
//      UrlRequstUtils.btcBrocast(this, s);
    }

    public void onErrow(Throwable throwable) {
        LogUtils.d("生成的hex过程中异常:" + throwable.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCODE_SCANNER_REQUEST) {
            if (data != null) {
                String scanResult = data.getStringExtra(Constants.scanResult);
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
            }
        } else {  // 无格式， 只有一个地址
            fillAddress(result);
        }
    }

    private void fillAddress(String addr) {
        try {
            new Address(addr);
            binding.receiveAddress.setText(addr);
        } catch (Exception e) {
            ToastUtils.showToast(R.string.addr_error_tips);
        }
    }
}
