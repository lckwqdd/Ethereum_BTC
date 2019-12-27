package com.alsc.net.db.helper;

import com.alsc.net.db.base.BaseDao;
import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.bean.ETHWallet;

/**
 * Created by wuquan on 2019-04-12.
 */

public class BTCWalletHelper extends BaseDao<BtcWallet> {
    private static BTCWalletHelper instance;

    private BTCWalletHelper() {
    }

    public static BTCWalletHelper getInstance() {
        if (instance == null) {
            instance = new BTCWalletHelper();
        }
        return instance;
    }
}
