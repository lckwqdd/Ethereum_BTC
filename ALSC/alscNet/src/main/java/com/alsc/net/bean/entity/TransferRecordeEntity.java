package com.alsc.net.bean.entity;

import com.alsc.net.bean.JackPotResult;
import com.alsc.net.bean.PageResult;
import com.alsc.net.bean.TransferRecordeResult;

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

public class TransferRecordeEntity implements Serializable {

    private List<TransferRecordeResult> list;
    private PageResult page;
    private float total;

    public List<TransferRecordeResult> getList() {
        return list;
    }

    public void setList(List<TransferRecordeResult> list) {
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
