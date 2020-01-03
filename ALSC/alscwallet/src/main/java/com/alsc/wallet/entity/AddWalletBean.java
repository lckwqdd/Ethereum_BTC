package com.alsc.wallet.entity;

public class AddWalletBean {
    private int symbol;
    private String symbolName;
    private String symbolDescrice;

    public AddWalletBean(int symbol, String symbolName, String symbolDescrice) {
        this.symbol = symbol;
        this.symbolName = symbolName;
        this.symbolDescrice = symbolDescrice;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getSymbolDescrice() {
        return symbolDescrice;
    }

    public void setSymbolDescrice(String symbolDescrice) {
        this.symbolDescrice = symbolDescrice;
    }
}
