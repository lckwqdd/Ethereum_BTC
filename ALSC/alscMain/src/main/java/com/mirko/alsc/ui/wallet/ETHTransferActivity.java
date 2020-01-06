package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.net.db.bean.ETHWalletDao;
import com.alsc.net.db.helper.ETHWalletHelper;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.C;
import com.alsc.wallet.entity.Address;
import com.alsc.wallet.entity.ethTranstionBean;
import com.alsc.wallet.interact.FetchWalletInteract;
import com.alsc.wallet.repository.TokenRepository;
import com.alsc.wallet.service.Web3JService;
import com.alsc.wallet.utils.BalanceUtils;
import com.alsc.wallet.utils.ETHWalletUtils;
import com.alsc.wallet.utils.KeyStoreUtils;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityHotWalletAlscTransferBinding;
import com.mirko.alsc.utils.UrlRequstUtils;
import com.mirko.androidutil.view.CustomeDialog;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
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
    public String contractAddress = "0xe38AB2AD0e3e85983f76Fe235b6A9869Cda0502d";
    private String nonce;
    private BigInteger gasPrice = BigInteger.valueOf(1000000000);
    private BigInteger gasLimit = BigInteger.valueOf(144000);
    private BigInteger tokenGasLimit = BigInteger.valueOf(100000);
    private static final double miner_min = 5;
    private static final double miner_max = 55;
    private String netCost;
    private CustomeDialog getCodeDialog;
    private FetchWalletInteract fetchWalletInteract;
    private ETHWallet currentWallet;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hot_wallet_alsc_transfer);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.wh_alsc_transfer));
        binding.commonHeader.ivHeaderRight.setVisibility(View.VISIBLE);
        binding.commonHeader.ivHeaderRight.setImageResource(R.mipmap.icon_scan);
        binding.commonHeader.ivHeaderRight.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        fetchWalletInteract = new FetchWalletInteract();

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
        fetchWalletInteract.findDefault().subscribe(this::onSuccess, this::onError);//找到当前以太坊钱包
//      getBalance();
    }

    private void onSuccess(ETHWallet ethWallet) {
        currentWallet = ethWallet;
    }

    private void onError(Throwable e) {
    }

    private void getNonceFromSendAddress(String address) {
        ThreadUtils.executeBySingleAtFixRate(new ThreadUtils.SimpleTask<String>() {
            @Override
            public String doInBackground() throws Throwable {
                String tempNonce = UrlRequstUtils.getNonceFromSendAddress(ETHTransferActivity.this, address);
                return tempNonce;
            }

            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    nonce = result;
                }
            }
        }, 5, TimeUnit.SECONDS);
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
     * (接通)
     * 以太坊非合约交易
     */
    private void sendTranstion() {
        if (checkInput()) {
            LogUtils.d("填写地址合法");
            Observable.create(new ObservableOnSubscribe<ethTranstionBean>() {
                @Override
                public void subscribe(ObservableEmitter<ethTranstionBean> e) throws Exception {
                    //签名交易信息
                    List<ETHWallet> ethWalletList = ETHWalletHelper.getInstance().QueryObject(ETHWallet.class, ETHWalletDao.Properties.Address.columnName + "=?", new String[]{ethAddress});
//                  LogUtils.d("当前钱包:"+ GsonUtils.toJson(ethWalletList.get(0)));
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

                    //发送交易广播
                    if (!TextUtils.isEmpty(hexValue)) {
                        ethTranstionBean ethTtrantion = UrlRequstUtils.sendEthBrocastTranstion(ETHTransferActivity.this, hexValue);
                        e.onNext(ethTtrantion);
                    }
                }
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ethTranstionBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(ethTranstionBean ethSendTransaction) {
                            LogUtils.d("发送数据:" + GsonUtils.toJson(ethSendTransaction));
                            if (ethSendTransaction.getRspCode().equals("success")) {
                                ToastUtils.showToast(ethSendTransaction.getRspMsg());
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
            Observable.create(new ObservableOnSubscribe<ethTranstionBean>() {
                @Override
                public void subscribe(ObservableEmitter<ethTranstionBean> e) throws Exception {
                    BigInteger gas = gasPrice.multiply(gasLimit);
                    BigInteger total = gas.add(Convert.toWei(binding.account.getText().toString().trim(), Convert.Unit.ETHER).toBigInteger());
                    LogUtils.d("gasPrice.toString():" + gasPrice.toString());
                    LogUtils.d("gasLimit.toString():" + gasLimit.toString());
                    LogUtils.d("nonce.toString():" + nonce);
                    LogUtils.d("total.toString():" + total.toString());
                    LogUtils.d("account:" + BalanceUtils.tokenToWei(new BigDecimal(binding.account.getText().toString().trim()), 18));
                    String txHash = sendTokenTransaction(binding.sendAddress.getText().toString().trim(),
                            binding.receiveAddress.getText().toString().trim(),
                            contractAddress,
                            BalanceUtils.tokenToWei(new BigDecimal(binding.account.getText().toString().trim()), 18).toBigInteger());
                    if (!TextUtils.isEmpty(txHash)) {
                        //发送交易广播
                        ethTranstionBean ethTtrantion = UrlRequstUtils.sendEthBrocastTranstion(ETHTransferActivity.this, txHash);
                        e.onNext(ethTtrantion);
                    }
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ethTranstionBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(ethTranstionBean ethTranstionBean) {
                            LogUtils.d("以太坊合约交易hex:" + GsonUtils.toJson(ethTranstionBean));
                            ToastUtils.showToast(ethTranstionBean.getRspMsg());
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

        String callFuncData = TokenRepository.createTokenTransferData(toAddress, amount);

        List<ETHWallet> ethWalletList = ETHWalletHelper.getInstance().QueryObject(ETHWallet.class, ETHWalletDao.Properties.Address.columnName + "=?", new String[]{ethAddress});

        ETHWallet tempWallet = ethWalletList.get(0);
        Credentials credentials = WalletUtils.loadCredentials(tempWallet.getPassword(), tempWallet.getKeystorePath());
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                new BigInteger(nonce, 10), gasPrice, tokenGasLimit, contractAddress, callFuncData);

        LogUtils.d("rawTransaction:" + rawTransaction);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        LogUtils.d("rawTransaction:" + hexValue);

        return hexValue;
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
                showCreateDialog();
                break;
        }
    }

    private void showCreateDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_input_wallet_pwd, null);
        getCodeDialog = new CustomeDialog(this, view);
        getCodeDialog.setCanceledOnTouchOutside(false);
        getCodeDialog.setGravity(Gravity.BOTTOM);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getCodeDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        getCodeDialog.getWindow().setAttributes(lp);

        ImageView walletCancel = view.findViewById(R.id.iv_cancle);
        EditText walletPwd = view.findViewById(R.id.wallet_password);
        Button walletSure = view.findViewById(R.id.btn_sure);

        walletCancel.setOnClickListener((v -> {
            getCodeDialog.dismiss();
        }));
        walletSure.setOnClickListener((v -> {
            if (currentWallet != null) {
                if (TextUtils.equals(walletPwd.getText().toString().trim(), currentWallet.getPassword())) {
                    sendContractTransaction();
                } else {
                    ToastUtils.showToast(getString(R.string.pwd_not_right));
                }
            }
            getCodeDialog.dismiss();
        }));
        getCodeDialog.show();
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
