package com.alsc.net.db.helper;

import com.alsc.net.db.base.BaseDao;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.net.db.bean.IdCradBean;

/**
 * Created by wuquan on 2019-04-12.
 */

public class ETHWalletHelper extends BaseDao<ETHWallet> {
    private static ETHWalletHelper instance;

    private ETHWalletHelper() {
    }

    public static ETHWalletHelper getInstance() {
        if (instance == null) {
            instance = new ETHWalletHelper();
        }
        return instance;
    }
}
