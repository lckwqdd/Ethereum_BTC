package com.alsc.net.bean.entity;

import com.alsc.net.bean.PageResult;
import com.alsc.net.bean.ReputationResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/28.
 **返回参数说明**

 | 字段      | 类型 | 描述         |
 | --------- | ---- | ------------ |
 | total | float  |  总加速         |
 | list.gx | float  |  贡献值         |
 | list.xy  | float  | 信誉值 |
 | list.js     | float  | 已加速     |
 | list.add_time     | date  |  时间     |
 | page.counts | int  |  总条数        |
 | page.pages  | int  | 总页面数 |
 | page.currPage     | int  | 当前页面     |
 | page.pageSize     | int  |  每页显示条数     |
 */

public class SharedResultEntity implements Serializable {

    private List<ReputationResult> list;
    private PageResult page;
    private float total;

    public List<ReputationResult> getList() {
        return list;
    }

    public void setList(List<ReputationResult> list) {
        this.list = list;
    }

    public PageResult getPage() {
        return page;
    }

    public void setPage(PageResult page) {
        this.page = page;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
