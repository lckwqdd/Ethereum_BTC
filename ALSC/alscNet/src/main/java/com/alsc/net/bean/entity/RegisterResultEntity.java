package com.alsc.net.bean.entity;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/23.
 */

public class RegisterResultEntity implements Serializable {

    private String user_id;
    private int res;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
