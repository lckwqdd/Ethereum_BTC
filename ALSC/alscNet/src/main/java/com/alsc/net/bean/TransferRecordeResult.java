package com.alsc.net.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/28.

 **返回参数说明**


 | 字段      | 类型 | 描述         |
 | --------- | ---- | ------------ |
 | list.alsc | float  |   alsc数量       |
 | list.add_time | date  |   时间       |
 | list.status | int  |   状态（0待处理 1成功 2失败）       |
 | list.type | int  |  类型（1转入到app  2转出到交易所）      |
 | list.usdt | float  | usdt数量 |
 | list.hash     | string  | hash值    |
 | page.counts | int  |  总条数        |
 | page.pages  | int  | 总页面数 |
 | page.currPage     | int  | 当前页面     |
 | page.pageSize     | int  |  每页显示条数     |

 */

public class TransferRecordeResult implements Serializable {

    private float alsc;
    private String add_time;
    private float status;
    private int type;
    private float usdt;
    private String hash;

    public float getAlsc() {
        return alsc;
    }

    public void setAlsc(float alsc) {
        this.alsc = alsc;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public float getStatus() {
        return status;
    }

    public void setStatus(float status) {
        this.status = status;
    }

    public float getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getUsdt() {
        return usdt;
    }

    public void setUsdt(float usdt) {
        this.usdt = usdt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
