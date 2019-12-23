package com.alsc.net.bean.entity;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/23.
 */

public class LoginResultEntity implements Serializable {

    private String token;
    private String expires_time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires_time() {
        return expires_time;
    }

    public void setExpires_time(String expires_time) {
        this.expires_time = expires_time;
    }
}
