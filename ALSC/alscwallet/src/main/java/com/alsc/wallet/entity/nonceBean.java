package com.alsc.wallet.entity;

public class nonceBean {
    /**
     * rspCode : success
     * rspMsg : 获取nonce成功
     * data : 0
     */

    private String rspCode;
    private String rspMsg;
    private String data;

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
