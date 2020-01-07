package com.alsc.wallet.interact;

import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.BtcWalletUtils;
import com.alsc.wallet.utils.ETHWalletUtils;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.WalletDaoUtils;
import com.blankj.utilcode.util.GsonUtils;

import java.util.Arrays;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateWalletInteract {


    public CreateWalletInteract() {
    }

    //创建以太坊钱包
    public Single<ETHWallet> create(final String name, final String pwd, String confirmPwd) {
        return Single.fromCallable(() -> {
            ETHWallet ethWallet = ETHWalletUtils.generateMnemonic(name, pwd);
            LogUtils.d("以太坊:" + GsonUtils.toJson(ethWallet));
            WalletDaoUtils.insertNewWallet(ethWallet);
            return ethWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    //创建比特币钱包
    public Single<BtcWallet> createBTC(final String name, final String pwd, String confirmPwd) {
        return Single.fromCallable(() -> {
            BtcWallet btcWallet = BtcWalletUtils.generateMnemonic(name, pwd);
            LogUtils.d("比特币:" + GsonUtils.toJson(btcWallet));
            BTCWalletDaoUtils.insertNewWallet(btcWallet);
            return btcWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Single<ETHWallet> loadWalletByKeystore(final String keystore, final String pwd) {
        return Single.fromCallable(() -> {
            ETHWallet ethWallet = ETHWalletUtils.loadWalletByKeystore(keystore, pwd);
            if (ethWallet != null) {
                WalletDaoUtils.insertNewWallet(ethWallet);
            }
            return ethWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ETHWallet> loadWalletByPrivateKey(final String privateKey, final String pwd) {
        return Single.fromCallable(() -> {

                    ETHWallet ethWallet = ETHWalletUtils.loadWalletByPrivateKey(privateKey, pwd);
                    if (ethWallet != null) {
                        WalletDaoUtils.insertNewWallet(ethWallet);
                    }
                    return ethWallet;
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Single<ETHWallet> loadWalletByMnemonic(final String bipPath, final String mnemonic, final String pwd) {
        return Single.fromCallable(() -> {
            ETHWallet ethWallet = ETHWalletUtils.importMnemonic(bipPath
                    , Arrays.asList(mnemonic.split(" ")), pwd);
            if (ethWallet != null) {
                WalletDaoUtils.insertNewWallet(ethWallet);
            }
            return ethWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
