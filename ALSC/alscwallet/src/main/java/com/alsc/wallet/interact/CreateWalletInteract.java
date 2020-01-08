package com.alsc.wallet.interact;

import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.BtcWalletUtils;
import com.alsc.wallet.utils.ETHWalletUtils;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.WalletDaoUtils;
import com.blankj.utilcode.util.GsonUtils;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
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

    /**
     * 以太坊导入Keystore
     *
     * @param keystore
     * @param pwd
     * @return
     */
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

    /**
     * 以太坊导入私钥
     *
     * @param privateKey
     * @param pwd
     * @return
     */
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

    /**
     * 以太坊导入助记词
     *
     * @param bipPath
     * @param mnemonic
     * @param pwd
     * @return
     */
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

    /**
     * 比特币导入助记词
     *
     * @param mnemonic
     * @return
     * @throws UnreadableWalletException
     */
    public Single<BtcWallet> importFromMnemonic(String mnemonic) throws UnreadableWalletException{

        return Single.fromCallable(() -> {
            MainNetParams params = MainNetParams.get();
            String passphrase = "";
            Long creationtime = System.currentTimeMillis() / 1000L;
            DeterministicSeed seed = new DeterministicSeed(mnemonic, null, passphrase, creationtime);
            DeterministicKey hd = HDKeyDerivation.createMasterPrivateKey(seed.getSeedBytes());
            //分层
            hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(44, true));
            hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(0, true));
            hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(0, true));
            hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(0, false));
            hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(0, false));
            ECKey ecKey = ECKey.fromPrivate(hd.getPrivKey());
//            LegacyAddress address = LegacyAddress.fromKey(params, ecKey);
//            System.out.println("普通地址：" + address.toString());

            BtcWallet btcWallet = new BtcWallet();
            btcWallet.setPrivateKey(hd.getPrivateKeyAsWiF(params));
//            btcWallet.setAddress(address.toString());
            btcWallet.setMnemonic(mnemonic);
            if (btcWallet != null) {
                BTCWalletDaoUtils.insertNewWallet(btcWallet);
            }
            return btcWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    /**
     * 比特币导入私钥
     *
     * @param privatekey
     * @return
     * @throws UnreadableWalletException
     */
    public Single<BtcWallet> importFromPrivateKey(String privatekey) {

        return Single.fromCallable(() -> {
            MainNetParams mainNetParams = MainNetParams.get();
            ECKey key = DumpedPrivateKey.fromBase58(MainNetParams.get(), privatekey).getKey();
//            LegacyAddress address = LegacyAddress.fromKey(mainNetParams, key);

            BtcWallet btcWallet = new BtcWallet();
            btcWallet.setPrivateKey(privatekey);
//            btcWallet.setAddress(address.toBase58());
            if (btcWallet != null) {
                BTCWalletDaoUtils.insertNewWallet(btcWallet);
            }
            return btcWallet;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
