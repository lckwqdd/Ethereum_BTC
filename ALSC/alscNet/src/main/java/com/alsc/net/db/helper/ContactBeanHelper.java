package com.alsc.net.db.helper;

import com.alsc.net.db.base.BaseDao;
import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.bean.ContactBean;

/**
 * Created by wuquan on 2019-04-12.
 */

public class ContactBeanHelper extends BaseDao<ContactBean> {
    private static ContactBeanHelper instance;

    private ContactBeanHelper() {
    }

    public static ContactBeanHelper getInstance() {
        if (instance == null) {
            instance = new ContactBeanHelper();
        }
        return instance;
    }
}
