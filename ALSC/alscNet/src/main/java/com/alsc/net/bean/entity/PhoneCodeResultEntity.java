package com.alsc.net.bean.entity;

import com.alsc.net.bean.PhoneCodeInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/23.
 */

public class PhoneCodeResultEntity implements Serializable {


    private List<PhoneCodeInfo> phone_code;

    public List<PhoneCodeInfo> getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(List<PhoneCodeInfo> phone_code) {
        this.phone_code = phone_code;
    }
}
