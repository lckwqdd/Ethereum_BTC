package com.alsc.net.bean.okgo;

import com.alsc.net.bean.JackPotResult;
import com.alsc.net.bean.PageResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/28.



 */

public class AddressEntity implements Serializable {

    private AddressRequst data;

    public AddressRequst getData() {
        return data;
    }

    public void setData(AddressRequst data) {
        this.data = data;
    }
}
