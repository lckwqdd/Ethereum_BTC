package com.alsc.net.bean.entity;

import com.alsc.net.bean.JackPotResult;
import com.alsc.net.bean.PageResult;
import com.alsc.net.bean.RecommendUserResult;

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

    private List<RecommendUserResult> list;
    private PageResult page;

    public List<RecommendUserResult> getList() {
        return list;
    }

    public void setList(List<RecommendUserResult> list) {
        this.list = list;
    }

    public PageResult getPage() {
        return page;
    }

    public void setPage(PageResult page) {
        this.page = page;
    }



}
