package com.alsc.wallet.interact;

import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.bean.BtcWalletDao;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.net.db.bean.ETHWalletDao;
import com.alsc.net.db.helper.BTCWalletHelper;
import com.alsc.net.db.helper.ETHWalletHelper;
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.WalletDaoUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.Scheduler;

/**
 * Created by WuQuan
 */

public class FetchWalletInteract {


    public FetchWalletInteract() {
    }


    public Single<List<ETHWallet>> fetch() {


        return Single.fromCallable(() -> {
            return WalletDaoUtils.loadAll();
        }).observeOn(AndroidSchedulers.mainThread());

    }

    public Single<ETHWallet> findDefault() {

        return Single.fromCallable(() -> {
            return WalletDaoUtils.getCurrent();
        });

    }

    public Single<List<BtcWallet>> fetchBTC() {


        return Single.fromCallable(() -> {
            return BTCWalletDaoUtils.loadAll();
        }).observeOn(AndroidSchedulers.mainThread());

    }

    public Single<ETHWallet> findDefaultByAddress(String address) {

        return Single.fromCallable(() -> {
            List<ETHWallet> btcWalletList = ETHWalletHelper.getInstance().QueryObject(ETHWallet.class, ETHWalletDao.Properties.Address.columnName + "=?", new String[]{address});
            ETHWallet btcWallet = btcWalletList.get(0);
            return btcWallet;
        });
    }

    public Single<BtcWallet> findDefaultBTC() {

        return Single.fromCallable(() -> {
            return BTCWalletDaoUtils.getCurrent();
        });
    }

    public Single<BtcWallet> findDefaultBTCByAddress(String address) {

        return Single.fromCallable(() -> {
            List<BtcWallet> btcWalletList = BTCWalletHelper.getInstance().QueryObject(ETHWallet.class, BtcWalletDao.Properties.Address.columnName + "=?", new String[]{address});
            BtcWallet btcWallet = btcWalletList.get(0);
            return btcWallet;
        });
    }
    public Single<BtcWallet> findFirstBTC() {

        return Single.fromCallable(() -> {
            List<BtcWallet> btcWalletList = BTCWalletHelper.getInstance().QueryAll(BtcWallet.class);
            BtcWallet btcWallet = btcWalletList.get(0);
            return btcWallet;
        });
    }


    public Single<String> genrateSingtureHex() {
        return Single.fromCallable(() -> {
            return "";
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

}
