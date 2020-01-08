package com.alsc.wallet.utils;


import com.alsc.net.db.bean.BtcWallet;
import com.google.common.base.Joiner;
import org.bitcoinj.core.Address;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 以太坊钱包创建工具类
 */

public class BtcWalletUtils {


    public static BtcWallet generateMnemonic(String walletName, String pwd) {
        //生成比特币钱包相关
        MainNetParams parameters = MainNetParams.get();
        Wallet wallet = new Wallet(parameters);
        DeterministicKey deterministicKey = wallet.currentReceiveKey();
        String btcPrivateKey = deterministicKey.getPrivateKeyAsWiF(parameters);
        LogUtils.d("BTC私钥：" + btcPrivateKey);

        //获取比特币助记词
        DeterministicSeed seed = wallet.getKeyChainSeed();
        List<String> mnemonicCode = seed.getMnemonicCode();
        String mnemonic_str = Joiner.on(" ").join(mnemonicCode);



        //目录不存在则创建目录，创建不了则报错
        File fireBtc = new File(AppFilePath.Wallet_DIR, "BTCStore");
        if (!createParentDir(fireBtc)) {
            return null;
        }
        try {
            wallet.saveToFile(fireBtc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address currentReceiveAddress = wallet.currentReceiveAddress();
        String addressString = currentReceiveAddress.toBase58();

        BtcWallet tempBtcWallet = new BtcWallet();
        tempBtcWallet.setMnemonic(mnemonic_str);
        tempBtcWallet.setKeystorePath(fireBtc.getAbsolutePath());
        tempBtcWallet.setAddress(addressString);
        tempBtcWallet.setPrivateKey(btcPrivateKey);
        LogUtils.d("BTC地址：" + addressString);
        return tempBtcWallet;
    }

    private static boolean createParentDir(File file) {
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建");
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        return true;
    }

}
