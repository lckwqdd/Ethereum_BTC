package com.alsc.net.bean.entity;

import com.alsc.net.bean.NoticePageResult;
import com.alsc.net.bean.NoticeResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/23.
 */

public class RegisterResultEntity implements Serializable {

    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
