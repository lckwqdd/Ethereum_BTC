package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.C;
import com.alsc.wallet.entity.Address;
import com.alsc.wallet.service.Web3JService;
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.BalanceUtils;
import com.alsc.wallet.utils.ETHWalletUtils;
import com.alsc.wallet.utils.KeyStoreUtils;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
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
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
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
import java.util.ArrayList;
import java.util.List;

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
    private String receiveAddress;
    private String sendAddress;
    private String account;
    private String tokenGasLimit = "100000";
    private String gasLimit = "21000";
    private String gasPrice = "1500000000";
    private String nonce;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hot_wallet_alsc_transfer);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.wh_alsc_transfer));
        binding.commonHeader.ivHeaderRight.setVisibility(View.VISIBLE);
        binding.commonHeader.ivHeaderRight.setImageResource(R.mipmap.icon_scan);

        Intent intent = getIntent();
        ethAddress = intent.getStringExtra(Constants.walletAddress);
        LogUtils.d("以太坊地址:" + ethAddress);
        binding.sendAddress.setText(ethAddress);
        receiveAddress = binding.receiveAddress.getText().toString().trim();
        sendAddress = binding.sendAddress.getText().toString().trim();
        account = binding.account.getText().toString().trim();
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        getNonceFromSendAddress();
        getBalance();
    }

    private void getNonceFromSendAddress() {
        Observable.create(new ObservableOnSubscribe<EthGetTransactionCount>() {
            @Override
            public void subscribe(ObservableEmitter<EthGetTransactionCount> e) throws Exception {
                EthGetTransactionCount count = Web3JService.getInstance().ethGetTransactionCount(sendAddress, DefaultBlockParameterName.LATEST).send();
                if (count.getError() == null) {
                    e.onNext(count);
                    e.onComplete();
                } else {
                    e.onError(new Throwable(count.getError().getMessage()));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EthGetTransactionCount>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(EthGetTransactionCount ethGetTransactionCount) {
                        BigInteger bigInteger = Numeric.decodeQuantity(ethGetTransactionCount.getResult());
                        nonce = bigInteger.toString();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getBalance() {
        Observable.create(new ObservableOnSubscribe<EthGetBalance>() {
            @Override
            public void subscribe(ObservableEmitter<EthGetBalance> e) throws Exception {
                Web3j web3j = Web3JService.getInstance();
                EthGetBalance ethGetBalance = web3j.ethGetBalance(sendAddress, DefaultBlockParameterName.LATEST).send();
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
     * 以太坊合约交易
     */
    private void sendContractTransaction() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String txHash = sendTokenTransaction(sendAddress, receiveAddress, contractAddress, BalanceUtils.tokenToWei(new BigDecimal(account), 18).toBigInteger());
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


    /**
     * 以太坊交易
     */
    private void sendTranstion() {
        checkInput();

        Double aDouble = Double.valueOf(account);
        //toWei转换为18位的wei
        final BigInteger bigAccount = Convert.toWei(BigDecimal.valueOf(aDouble), Convert.Unit.ETHER).toBigInteger();
        Double gasPriceDouble = Double.valueOf(gasPrice);
        final BigInteger bigGasPrice = Convert.toWei(BigDecimal.valueOf(gasPriceDouble), Convert.Unit.GWEI).toBigInteger();
        Observable.create(new ObservableOnSubscribe<EthSendTransaction>() {
            @Override
            public void subscribe(ObservableEmitter<EthSendTransaction> e) throws Exception {
                Web3j web3j = Web3JService.getInstance();
                //签名交易信息
                String hexValue = KeyStoreUtils.signedTransactionData(sendAddress, receiveAddress, nonce, bigGasPrice.toString(), gasLimit, bigAccount.toString());
                //RPC 处理发送交易
                EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).send();

                if (send.getError() == null) {
                    e.onNext(send);
                    e.onComplete();
                } else {
                    e.onError(new Throwable(send.getError().getMessage()));
                }
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
                            LogUtils.d("发送成功: " + result);
                            ToastUtils.showToast("发送成功");
                        } else {
                            LogUtils.d("translation1:" + ethSendTransaction.getJsonrpc());
                            LogUtils.d("translation2:" + ethSendTransaction.getRawResponse());
                            LogUtils.d("translation3:" + ethSendTransaction.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.showToast("发送失败");
                        LogUtils.d("发送失败:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    /**
     * 代币转账
     */
    public String sendTokenTransaction(String fromAddress, String toAddress, String contractAddress, BigInteger amount) throws IOException {
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

        String hexValue = signedTransactionContractData(fromAddress, contractAddress, nonce, gasPrice, tokenGasLimit, BigInteger.ZERO.toString(), data);
        EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).send();
        txHash = send.getResult();
        return txHash;

    }

    public static String signedTransactionContractData(String from, String contractAddress, String nonce, String gasPrice, String gasLimit, String value, String data) throws FileNotFoundException {
        //发送正常交易
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                new BigInteger(nonce),
                new BigInteger(gasPrice),
                new BigInteger(gasLimit),
                contractAddress,
                new BigInteger(value),
                data
        );
        //获取资格证书
        Credentials credentials = KeyStoreUtils.getCredentials(from);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        return Numeric.toHexString(signedMessage);
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
        if (!ETHWalletUtils.isBTCValidAddress(binding.receiveAddress.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.btc_receive_not_right));
            return false;
        }
        if (!ETHWalletUtils.isBTCValidAddress(binding.sendAddress.getText().toString().trim())) {
            ToastUtils.showToast(getString(R.string.btc_send_not_right));
            return false;
        }
        return true;
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
