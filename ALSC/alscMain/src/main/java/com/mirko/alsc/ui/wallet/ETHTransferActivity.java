package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.alsc.net.bean.okgo.AddressEntity;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.net.db.bean.ETHWalletDao;
import com.alsc.net.db.helper.ETHWalletHelper;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.C;
import com.alsc.wallet.entity.Address;
import com.alsc.wallet.entity.nonceBean;
import com.alsc.wallet.service.Web3JService;
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.BalanceUtils;
import com.alsc.wallet.utils.ETHWalletUtils;
import com.alsc.wallet.utils.KeyStoreUtils;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityAddWalletBinding;
import com.mirko.alsc.databinding.ActivityHotWalletAlscTransferBinding;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 以太坊转账
 */
public class ETHTransferActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityHotWalletAlscTransferBinding binding;
    private String ethAddress;
    private static final int QRCODE_SCANNER_REQUEST = 1100;
    public String contractAddress = "0x121212d1f2df1d";
    private String nonce;
    private BigInteger gasPrice = BigInteger.valueOf(1000000000);
    private BigInteger gasLimit = BigInteger.valueOf(144000);
    private BigInteger tokenGasLimit = BigInteger.valueOf(100000);
    private static final double miner_min = 5;
    private static final double miner_max = 55;
    private String netCost;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hot_wallet_alsc_transfer);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.wh_alsc_transfer));
        binding.commonHeader.ivHeaderRight.setVisibility(View.VISIBLE);
        binding.commonHeader.ivHeaderRight.setImageResource(R.mipmap.icon_scan);
        binding.commonHeader.ivHeaderRight.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);

        Intent intent = getIntent();
        ethAddress = intent.getStringExtra(Constants.walletAddress);
        LogUtils.d("以太坊地址:" + ethAddress);
        binding.sendAddress.setText(ethAddress);
    }

    @Override
    public void initAttrs() {

        final DecimalFormat gasformater = new DecimalFormat();
        //保留几位小数
        gasformater.setMaximumFractionDigits(2);
        //模式  四舍五入
        gasformater.setRoundingMode(RoundingMode.CEILING);
        final String etherUnit = getString(R.string.transfer_ether_unit);
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                double p = progress / 100f;
                double d = (miner_max - miner_min) * p + miner_min;
                gasPrice = BalanceUtils.gweiToWei(BigDecimal.valueOf(d));
                try {
                    netCost = BalanceUtils.weiToEth(gasPrice.multiply(gasLimit), 4) + " " + C.ETH_SYMBOL;
                    binding.fee.setText(String.valueOf(netCost) + " == " + gasformater.format(d) + " " + C.GWEI_UNIT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.seekbar.setProgress(10);
    }

    @Override
    public void loadData() {
        getNonceFromSendAddress(ethAddress);
//      getBalance();
    }

    private void getNonceFromSendAddress(String address) {
        ThreadUtils.executeBySingleAtFixRate(new ThreadUtils.SimpleTask<String>() {
            @Override
            public String doInBackground() throws Throwable {
                try {
                    String url = "http://45.77.37.117:8001/getNonce/" + address;
                    Call<String> call = OkGo.<String>get(url)
                            .tag(this)
                            .cacheKey("cacheKey")
                            .cacheMode(CacheMode.NO_CACHE).converter(new StringConvert()).adapt();
                    Response<String> response = call.execute();
                    String s = response.body().toString();
                    nonceBean jsonBean = new Gson().fromJson(s, nonceBean.class);
                    return jsonBean.getData();
                } catch (Exception e) {
                    LogUtils.d("nonceStr异常:" + e.toString());
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onSuccess(String result) {
                if(!TextUtils.isEmpty(result)){
                    nonce = result;
                }
            }
        },5, TimeUnit.SECONDS);
    }

    private void getBalance() {
        Observable.create(new ObservableOnSubscribe<EthGetBalance>() {
            @Override
            public void subscribe(ObservableEmitter<EthGetBalance> e) throws Exception {
                Web3j web3j = Web3JService.getInstance();
                EthGetBalance ethGetBalance = web3j.ethGetBalance(binding.sendAddress.getText().toString().trim(), DefaultBlockParameterName.LATEST).send();
                e.onNext(ethGetBalance);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EthGetBalance>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(EthGetBalance ethGetBalance) {
                        if (ethGetBalance != null) {
                            if (ethGetBalance.getError() == null) {
                                BigDecimal balanceDecimal = Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER);
                                binding.totalAmount.setText(balanceDecimal.toPlainString());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 以太坊非合约交易
     */
    private void sendTranstion() {
        if (checkInput()) {
            LogUtils.d("填写地址合法");
            Observable.create(new ObservableOnSubscribe<EthSendTransaction>() {
                @Override
                public void subscribe(ObservableEmitter<EthSendTransaction> e) throws Exception {
                    Web3j web3j = Web3JService.getInstance();
                    //签名交易信息
                    List<ETHWallet> ethWalletList = ETHWalletHelper.getInstance().QueryObject(ETHWallet.class, ETHWalletDao.Properties.Address.columnName + "=?", new String[]{ethAddress});
//                  LogUtils.d("当前钱包:"+ GsonUtils.toJson(ethWalletList.get(0)));
                    LogUtils.d("receiveAddress.toString():" + binding.receiveAddress.getText().toString().trim());
                    LogUtils.d("ethAddress.toString():" + ethAddress);
                    BigInteger gas = gasPrice.multiply(gasLimit);
                    BigInteger total = gas.add(Convert.toWei(binding.account.getText().toString().trim(), Convert.Unit.ETHER).toBigInteger());
                    LogUtils.d("gasPrice.toString():" + gasPrice.toString());
                    LogUtils.d("gasLimit.toString():" + gasLimit.toString());
                    LogUtils.d("nonce.toString():" + nonce);
                    LogUtils.d("total.toString():" + total.toString());
                    LogUtils.d("account:" + Convert.toWei(binding.account.getText().toString().trim(), Convert.Unit.ETHER).toBigInteger().toString());
                    String hexValue = KeyStoreUtils.signedTransactionData(ethWalletList.get(0),
                            ethAddress,
                            binding.receiveAddress.getText().toString().trim(),//发送地址
                            nonce, gasPrice.toString(), gasLimit.toString(),
                            Convert.toWei(binding.account.getText().toString().trim(), Convert.Unit.ETHER).toBigInteger().toString());
                    LogUtils.d("hexValue:" + hexValue);
                    //RPC 处理发送交易
//                    EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).send();
//                    if (send.getError() == null) {
//                        e.onNext(send);
//                        e.onComplete();
//                    } else {
//                        e.onError(new Throwable(send.getError().getMessage()));
//                    }
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<EthSendTransaction>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(EthSendTransaction ethSendTransaction) {
                            String result = ethSendTransaction.getResult();
                            if (ethSendTransaction.getError() == null) {
                                ToastUtils.showToast(getString(R.string.send_success) + result);
                            } else {
                                LogUtils.d("translation1:" + ethSendTransaction.getJsonrpc());
                                LogUtils.d("translation2:" + ethSendTransaction.getRawResponse());
                                LogUtils.d("translation3:" + ethSendTransaction.getResult());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            LogUtils.d("合约交易发送失败:" + e.getMessage());
                            ToastUtils.showToast(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        }
    }

    /**
     * 以太坊合约交易
     */
    private void sendContractTransaction() {
        if (checkInput()) {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    LogUtils.d("gasPrice.toString():" + gasPrice.toString());
                    LogUtils.d("gasLimit.toString():" + gasLimit.toString());
                    LogUtils.d("account:" + BalanceUtils.tokenToWei(new BigDecimal(binding.account.getText().toString().trim()), 18));
                    String txHash = sendTokenTransaction(binding.sendAddress.getText().toString().trim(),
                            binding.receiveAddress.getText().toString().trim(),
                            contractAddress,
                            BalanceUtils.tokenToWei(new BigDecimal(binding.account.getText().toString().trim()), 18).toBigInteger());
                    if (txHash != null) {
                        e.onNext(txHash);
                    }
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast("发送成功");
                            LogUtils.d("以太坊合约交易hex:" + s);
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtils.showToast("发送失败");
                            LogUtils.d("发送失败:" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    /**
     * 合约代币转账
     */
    public String sendTokenTransaction(String fromAddress, String toAddress, String contractAddress, BigInteger amount) throws Exception {
        String txHash = null;

        String methodName = "transfer";
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        org.web3j.abi.datatypes.Address tAddress = new org.web3j.abi.datatypes.Address(toAddress);
        Uint256 value = new Uint256(amount);
        inputParameters.add(tAddress);
        inputParameters.add(value);
        TypeReference<Bool> typeReference = new TypeReference<Bool>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Web3j web3j = Web3JService.getInstance();

        List<ETHWallet> ethWalletList = ETHWalletHelper.getInstance().QueryObject(ETHWallet.class, ETHWalletDao.Properties.Address.columnName + "=?", new String[]{ethAddress});
        LogUtils.d("当前钱包:" + GsonUtils.toJson(ethWalletList.get(0)));
        String hexValue = KeyStoreUtils.signedTransactionContractData(ethWalletList.get(0), fromAddress, contractAddress, nonce, gasPrice.toString(),
                tokenGasLimit.toString(), value.toString(), data);
        EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).send();
        txHash = send.getResult();
        return txHash;

    }


    private boolean checkInput() {
        if (TextUtils.isEmpty(binding.account.getText().toString())) {
            ToastUtils.showToast(getString(R.string.tranfer_not_null));
            return false;
        }
        if (TextUtils.isEmpty(nonce)) {
            ToastUtils.showToast(getString(R.string.nonce_not_null));
            return false;
        }
        if (!ETHWalletUtils.isETHValidAddress(binding.receiveAddress.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.btc_receive_not_right));
            return false;
        }
        if (!ETHWalletUtils.isETHValidAddress(binding.sendAddress.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.btc_send_not_right));
            return false;
        }
        try {
            String wei = BalanceUtils.EthToWei(binding.account.getText().toString());
            return wei != null;
        } catch (Exception e) {
            ToastUtils.showToast(R.string.amount_error_tips);
            return false;
        }
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
