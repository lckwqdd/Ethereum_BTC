package com.alsc.net.bean.request;

/**
 *
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 | amount | float | 否       | 金额 |
 | receive_url | String | 否       | 接收地址 |
 | send_url | String | 否       | 发送地址 |
 | price | float | 否       | 实时价格 |
 | symbol | int | 否       | 币种 1.alsc 2.eth 3.eth的usdt 4.btc的usdt 5.btc |
 */
public class DevoteTransferRequest {

    private String token;
    private float amount;
    private String receive_url;
    private String send_url;
    private float price;
    private int symbol;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getReceive_url() {
        return receive_url;
    }

    public void setReceive_url(String receive_url) {
        this.receive_url = receive_url;
    }

    public String getSend_url() {
        return send_url;
    }

    public void setSend_url(String send_url) {
        this.send_url = send_url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }
}
