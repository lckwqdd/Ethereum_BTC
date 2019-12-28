package com.alsc.net.bean.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/12.
 * 没有返回值的时候用
 */

public class TranstionResultEntity implements Serializable {
    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
