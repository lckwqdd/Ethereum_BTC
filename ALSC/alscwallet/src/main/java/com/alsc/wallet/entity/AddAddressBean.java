package com.alsc.wallet.entity;

public class AddAddressBean {
    private String name;
    private String address;
    private String note;

    public AddAddressBean(String name, String address, String note) {
        this.name = name;
        this.address = address;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
