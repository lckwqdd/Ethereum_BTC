package com.alsc.net.bean.entity;

import com.alsc.net.bean.JackPotResult;
import com.alsc.net.bean.PageResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/28.
 **返回参数说明**

 | 字段      | 类型 | 描述         |
 | --------- | ---- | ------------ |
 | total_usdt | float  |  总资产U         |
 | total_alsc  | float  | ALSC数量 |
 | profit     | float  | 已收益总额     |
 | exchange     | float  |  已兑换总额U     |
 | product_usdt     | float  |  可兑换的USDT     |


 */

public class CapitalResultEntity implements Serializable {

    private List<JackPotResult> list;
    private PageResult page;
    private float total_income;
    private float total_alsc;
    private float total_ach;

    public List<JackPotResult> getList() {
        return list;
    }

    public void setList(List<JackPotResult> list) {
        this.list = list;
    }

    public PageResult getPage() {
        return page;
    }

    public void setPage(PageResult page) {
        this.page = page;
    }

    public float getTotal_income() {
        return total_income;
    }

    public void setTotal_income(float total_income) {
        this.total_income = total_income;
    }

    public float getTotal_alsc() {
        return total_alsc;
    }

    public void setTotal_alsc(float total_alsc) {
        this.total_alsc = total_alsc;
    }

    public float getTotal_ach() {
        return total_ach;
    }

    public void setTotal_ach(float total_ach) {
        this.total_ach = total_ach;
    }
}
