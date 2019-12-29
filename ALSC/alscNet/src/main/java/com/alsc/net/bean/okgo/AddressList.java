package com.alsc.net.bean.okgo;

import com.alsc.net.bean.JackPotResult;
import com.alsc.net.bean.PageResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/28.



 */

public class AddressList implements Serializable {

    private String tx_hash;
    private int tx_output_n;
    private int tx_output_n2;
    private int value;
    private int confirmations;

    public String getTx_hash() {
        return tx_hash;
    }

    public void setTx_hash(String tx_hash) {
        this.tx_hash = tx_hash;
    }

    public int getTx_output_n() {
        return tx_output_n;
    }

    public void setTx_output_n(int tx_output_n) {
        this.tx_output_n = tx_output_n;
    }

    public int getTx_output_n2() {
        return tx_output_n2;
    }

    public void setTx_output_n2(int tx_output_n2) {
        this.tx_output_n2 = tx_output_n2;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }
}
