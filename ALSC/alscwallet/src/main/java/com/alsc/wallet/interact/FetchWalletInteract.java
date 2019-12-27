package com.alsc.wallet.interact;

import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.WalletDaoUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

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

    public Single<List<BtcWallet>> fetchBtc() {


        return Single.fromCallable(() -> {
            return BTCWalletDaoUtils.loadAll();
        }).observeOn(AndroidSchedulers.mainThread());

    }

    public Single<BtcWallet> findDefaultBtc() {

        return Single.fromCallable(() -> {
            return BTCWalletDaoUtils.getCurrent();
        });

    }
}
