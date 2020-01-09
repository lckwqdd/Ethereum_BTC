package com.alsc.wallet.bean.request;


/**
 * | 字段  | 类型   | 是否可选 | 描述  |
 * | ----- | ------ | -------- | ----- |
 * | addr | string | 否       | 用户钱包地址 |
 * | symbol | string | 否       | 币种 |
 * | type | int | 是       | 类型 0全部（默认） 1.收款 2转账 |
 * | page_index | int | 是       | 当前第几页 |
 * | page_size | int | 是       |  每页显示条数 |
 * */
public class TransfersRequest {
    private String addr;
    private String symbol;
    private int type;
    private int page_index;
    private int page_size;

    public TransfersRequest(String addr, String symbol, int type, int page_index, int page_size) {
        this.addr = addr;
        this.symbol = symbol;
        this.type = type;
        this.page_index = page_index;
        this.page_size = page_size;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}
