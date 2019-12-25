package com.alsc.net.bean.entity;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/23.
 */

public class PicCodeResultEntity implements Serializable {

    private String code;
    private String sid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
