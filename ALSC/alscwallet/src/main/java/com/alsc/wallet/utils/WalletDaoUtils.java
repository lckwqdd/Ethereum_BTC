package com.alsc.wallet.utils;

import android.text.TextUtils;

import com.alsc.net.db.bean.ETHWallet;
import com.alsc.net.db.helper.ETHWalletHelper;

import java.util.List;

/**
 * Created by WuQuan
 */

public class WalletDaoUtils {

    /**
     * 插入新创建钱包
     *
     * @param ethWallet 新创建钱包
     */
    public static void insertNewWallet(ETHWallet ethWallet) {
        updateCurrent(-1);
        ethWallet.setIsCurrent(true);
        ETHWalletHelper.getInstance().insertObject(ethWallet);
    }

    /**
     * 更新选中钱包
     *
     * @param id 钱包ID
     */
    public static ETHWallet updateCurrent(long id) {
        List<ETHWallet> ethWallets = ETHWalletHelper.getInstance().QueryAll(ETHWallet.class);
        ETHWallet currentWallet = null;
        for (ETHWallet ethwallet : ethWallets) {
            if (id != -1 && ethwallet.getId() == id) {
                ethwallet.setIsCurrent(true);
                currentWallet = ethwallet;
            } else {
                ethwallet.setIsCurrent(false);
            }
            ETHWalletHelper.getInstance().updateObject(ethwallet);
        }
        return currentWallet;
    }

    /**
     * 获取当前钱包
     *
     * @return 钱包对象
     */
    public static ETHWallet getCurrent() {
        List<ETHWallet> ethWallets = ETHWalletHelper.getInstance().QueryAll(ETHWallet.class);
        for (ETHWallet ethwallet : ethWallets) {
            if (ethwallet.getIsCurrent()) {
                ethwallet.setIsCurrent(true);
                return ethwallet;
            }
        }
        return null;
    }

    /**
     * 查询所有钱包
     */
    public static List<ETHWallet> loadAll() {
        return ETHWalletHelper.getInstance().QueryAll(ETHWallet.class);
    }

    /**
     * 检查钱包名称是否存在
     *
     * @param name
     * @return
     */
    public static boolean walletNameChecking(String name) {
        List<ETHWallet> ethWallets = loadAll();
        for (ETHWallet ethWallet : ethWallets
        ) {
            if (TextUtils.equals(ethWallet.getName(), name)) {
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
        ETHWallet ethWallet = ETHWalletHelper.getInstance().QueryById(walletId, ETHWallet.class);
        ethWallet.setIsBackup(true);
        ETHWalletHelper.getInstance().updateObject(ethWallet);
    }

    /**
     * 以助记词检查钱包是否存在
     *
     * @param mnemonic
     * @return true if repeat
     */
    public static boolean checkRepeatByMenmonic(String mnemonic) {
        List<ETHWallet> ethWallets = loadAll();
        for (ETHWallet ethWallet : ethWallets
        ) {
            if (TextUtils.isEmpty(ethWallet.getMnemonic())) {
                LogUtils.d("wallet mnemonic empty");
                continue;
            }
            if (TextUtils.equals(ethWallet.getMnemonic().trim(), mnemonic.trim())) {
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
        ETHWallet wallet = ETHWalletHelper.getInstance().QueryById(walletId,ETHWallet.class);
        wallet.setName(name);
        ETHWalletHelper.getInstance().updateObject(wallet);
    }

    public static void setCurrentAfterDelete() {
        List<ETHWallet> ethWallets = ETHWalletHelper.getInstance().QueryAll(ETHWallet.class);
        if (ethWallets != null && ethWallets.size() > 0) {
            ETHWallet ethWallet = ethWallets.get(0);
            ethWallet.setIsCurrent(true);
            ETHWalletHelper.getInstance().updateObject(ethWallet);
        }
    }
}
