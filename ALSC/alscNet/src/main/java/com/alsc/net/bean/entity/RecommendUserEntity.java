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
 | user_id | int  |      用户ID    |
 | level | int  |      用户等级  （0普通 1vip 2小节点 3中节点 4大节点）  |
 | account | string  |     账号      |
 | total_price | float  |     贡献额      |
 | page.counts | int  |  总条数        |
 | page.pages  | int  | 总页面数 |
 | page.currPage     | int  | 当前页面     |
 | page.pageSize     | int  |  每页显示条数     |

 */

public class RecommendUserEntity implements Serializable {

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
