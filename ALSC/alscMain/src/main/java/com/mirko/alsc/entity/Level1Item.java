package com.mirko.alsc.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mirko.alsc.adapter.ExpandableItemAdapter;

public class Level1Item implements MultiItemEntity {
    private String alscName;
    private String alscAddress;
    private String alscBalance;
    private String alscValue;

    public Level1Item(String alscName, String alscAddress, String alscBalance, String alscValue) {
        this.alscName = alscName;
        this.alscAddress = alscAddress;
        this.alscBalance = alscBalance;
        this.alscValue = alscValue;
    }

    public String getAlscName() {
        return alscName;
    }

    public void setAlscName(String alscName) {
        this.alscName = alscName;
    }

    public String getAlscAddress() {
        return alscAddress;
    }

    public void setAlscAddress(String alscAddress) {
        this.alscAddress = alscAddress;
    }

    public String getAlscBalance() {
        return alscBalance;
    }

    public void setAlscBalance(String alscBalance) {
        this.alscBalance = alscBalance;
    }

    public String getAlscValue() {
        return alscValue;
    }

    public void setAlscValue(String alscValue) {
        this.alscValue = alscValue;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }
}
