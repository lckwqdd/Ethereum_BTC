package com.alsc.wallet.utils;

import android.text.TextUtils;

import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.net.db.helper.BTCWalletHelper;
import com.alsc.net.db.helper.ETHWalletHelper;

import java.util.List;

/**
 * Created by WuQuan
 * 比特币钱包相关
 */

public class BTCWalletDaoUtils {

    /**
     * 插入新创建钱包
     *
     * @param btcWallet 新创建钱包
     */
    public static void insertNewWallet(BtcWallet btcWallet) {
        updateCurrent(-1);
        btcWallet.setIsCurrent(true);
        BTCWalletHelper.getInstance().insertObject(btcWallet);
    }

    /**
     * 更新选中钱包
     *
     * @param id 钱包ID
     */
    public static BtcWallet updateCurrent(long id) {
        List<BtcWallet> btcWallets = BTCWalletHelper.getInstance().QueryAll(BtcWallet.class);
        BtcWallet currentWallet = null;
        for (BtcWallet btcWallet : btcWallets) {
            if (id != -1 && btcWallet.getId() == id) {
                btcWallet.setIsCurrent(true);
                currentWallet = btcWallet;
            } else {
                btcWallet.setIsCurrent(false);
            }
            BTCWalletHelper.getInstance().updateObject(btcWallet);
        }
        return currentWallet;
    }

    /**
     * 获取当前钱包
     *
     * @return 钱包对象
     */
    public static BtcWallet getCurrent() {
        List<BtcWallet> btcWallets = BTCWalletHelper.getInstance().QueryAll(BtcWallet.class);
        for (BtcWallet btcWallet : btcWallets) {
            if (btcWallet.getIsCurrent()) {
                btcWallet.setIsCurrent(true);
                return btcWallet;
            }
        }
        return null;
    }

    /**
     * 查询所有钱包
     */
    public static List<BtcWallet> loadAll() {
        return BTCWalletHelper.getInstance().QueryAll(BtcWallet.class);
    }

    /**
     * 检查钱包名称是否存在
     *
     * @param name
     * @return
     */
    public static boolean walletNameChecking(String name) {
        List<BtcWallet> btcWallets = loadAll();
        for (BtcWallet btcWallet : btcWallets
        ) {
            if (TextUtils.equals(btcWallet.getName(), name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置isBackup为已备份
     *
     * @param walletId 钱包Id
     */
    public static void setIsBackup(long walletId) {
        BtcWallet btcWallet = BTCWalletHelper.getInstance().QueryById(walletId, BtcWallet.class);
        btcWallet.setIsBackup(true);
        BTCWalletHelper.getInstance().updateObject(btcWallet);
    }

    /**
     * 以助记词检查钱包是否存在
     *
     * @param mnemonic
     * @return true if repeat
     */
    public static boolean checkRepeatByMenmonic(String mnemonic) {
        List<BtcWallet> btcWallets = loadAll();
        for (BtcWallet btcWallet : btcWallets
        ) {
            if (TextUtils.isEmpty(btcWallet.getMnemonic())) {
                LogUtils.d("wallet mnemonic empty");
                continue;
            }
            if (TextUtils.equals(btcWallet.getMnemonic().trim(), mnemonic.trim())) {
                LogUtils.d("aleady");
                return true;
            }
        }
        return false;
    }


    public static boolean isValid(String mnemonic) {
        return mnemonic.split(" ").length >= 12;
    }

    public static boolean checkRepeatByKeystore(String keystore) {
        return false;
    }

    /**
     * 修改钱包名称
     *
     * @param walletId
     * @param name
     */
    public static void updateWalletName(long walletId, String name) {
        BtcWallet wallet = BTCWalletHelper.getInstance().QueryById(walletId, BtcWallet.class);
        wallet.setName(name);
        BTCWalletHelper.getInstance().updateObject(wallet);
    }

    public static void setCurrentAfterDelete() {
        List<BtcWallet> btcWallets = BTCWalletHelper.getInstance().QueryAll(BtcWallet.class);
        if (btcWallets != null && btcWallets.size() > 0) {
            BtcWallet btcWallet = btcWallets.get(0);
            btcWallet.setIsCurrent(true);
            BTCWalletHelper.getInstance().updateObject(btcWallet);
        }
    }
}
