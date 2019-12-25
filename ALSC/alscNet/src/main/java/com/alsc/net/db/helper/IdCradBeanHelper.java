package com.alsc.net.db.helper;

import com.alsc.net.db.base.BaseDao;
import com.alsc.net.db.bean.IdCradBean;

/**
 * Created by wuquan on 2019-04-12.
 */

public class IdCradBeanHelper extends BaseDao<IdCradBean> {
    private static IdCradBeanHelper instance;

    private IdCradBeanHelper() {
    }

    public static IdCradBeanHelper getInstance() {
        if (instance == null) {
            instance = new IdCradBeanHelper();
        }
        return instance;
    }
}
