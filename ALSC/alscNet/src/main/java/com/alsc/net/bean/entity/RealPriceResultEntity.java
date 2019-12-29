package com.alsc.net.bean.entity;

import com.alsc.net.bean.RealPriceResult;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/28.
 * 实时价格
 */

public class RealPriceResultEntity implements Serializable {

    private RealPriceResult result;

    public RealPriceResult getResult() {
        return result;
    }

    public void setResult(RealPriceResult result) {
        this.result = result;
    }
}
