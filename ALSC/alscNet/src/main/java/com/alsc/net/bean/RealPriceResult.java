package com.alsc.net.bean;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/28.
 * 实时价格
 */

public class RealPriceResult implements Serializable {

    private String symbol;
    private String price;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
