package com.alsc.net.bean;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/28.

 **返回参数说明**

 | 字段      | 类型 | 描述         |
 | --------- | ---- | ------------ |
 | total_income | float  |   返还总额       |
 | total_alsc | float  |   奖池分红总额       |
 | total_ach | float  |   收益总额       |
 | list.free | float  |  alsc数量 或者 节点收益        |
 | list.add_time  | date  | 时间 |
 | list.income     | float  | 分红收益 或者 增长业绩    |
 | page.counts | int  |  总条数        |
 | page.pages  | int  | 总页面数 |
 | page.currPage     | int  | 当前页面     |
 | page.pageSize     | int  |  每页显示条数     |
 */

public class JackPotResult implements Serializable {

    private float free;
    private float income;
    private String add_time;

    public float getFree() {
        return free;
    }

    public void setFree(float free) {
        this.free = free;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
