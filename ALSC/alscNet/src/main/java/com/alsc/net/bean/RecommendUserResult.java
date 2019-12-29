package com.alsc.net.bean;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/28.
 **返回参数说明**

 | 字段      | 类型 | 描述         |
 | --------- | ---- | ------------ |
 | user_id | int  |      用户ID    |
 | level | int  |      用户等级  （0普通 1vip 2小节点 3中节点 4大节点）  |
 | account | string  |     账号      |
 | total_price | float  |     贡献额      |
 | page.counts | int  |  总条数        |
 | page.pages  | int  | 总页面数 |
 | page.currPage     | int  | 当前页面     |
 | page.pageSize     | int  |  每页显示条数     |
 */

public class RecommendUserResult implements Serializable {

    private int user_id;
    private int level;
    private String account;
    private float total_price;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }
}
