package com.alsc.net.bean.request;

public class AddressRegisterRequest {
    private String address;
    private String symbol;

    public AddressRegisterRequest(String address, String symbol) {
        this.address = address;
        this.symbol = symbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
