package com.alsc.net.bean.request;

/**
 *
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 | usdt | float | 否       | usdt数量 |
 | alsc | float | 否       | alsc数量 |
 | price | float | 否       | 实时价格 |
 */
public class DevoteExchangeRequest {

    private String token;
    private float usdt;
    private float alsc;
    private float price;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getUsdt() {
        return usdt;
    }

    public void setUsdt(float usdt) {
        this.usdt = usdt;
    }

    public float getAlsc() {
        return alsc;
    }

    public void setAlsc(float alsc) {
        this.alsc = alsc;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
