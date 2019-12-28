package com.alsc.net.bean.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/12.
 * 没有返回值的时候用
 */

public class AddressRegisterResultEntity implements Serializable {
    private String assets;

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }
}
