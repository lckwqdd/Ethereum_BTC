package com.alsc.wallet.bean.request;

public class TranslationRequest {
    private String hex;
    private String from_addr;
    private String to_addr;
    private float number;
    private float fee;
    private String symbol;

    public TranslationRequest(String hex, String from_addr, String to_addr, float number, float fee, String symbol) {
        this.hex = hex;
        this.from_addr = from_addr;
        this.to_addr = to_addr;
        this.number = number;
        this.fee = fee;
        this.symbol = symbol;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getFrom_addr() {
        return from_addr;
    }

    public void setFrom_addr(String from_addr) {
        this.from_addr = from_addr;
    }

    public String getTo_addr() {
        return to_addr;
    }

    public void setTo_addr(String to_addr) {
        this.to_addr = to_addr;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
