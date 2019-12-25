package com.alsc.net.db.helper;

import com.alsc.net.db.base.BaseDao;
import com.alsc.net.db.bean.IdCradBean;
import com.alsc.net.db.bean.UserInfoBean;

/**
 * Created by wuquan on 2019-04-12.
 */

public class UserInfoBeanHelper extends BaseDao<UserInfoBean> {
    private static UserInfoBeanHelper instance;

    private UserInfoBeanHelper() {
    }

    public static UserInfoBeanHelper getInstance() {
        if (instance == null) {
            instance = new UserInfoBeanHelper();
        }
        return instance;
    }
}
