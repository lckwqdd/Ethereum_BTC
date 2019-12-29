package com.alsc.net.bean.entity;

import com.alsc.net.bean.PageResult;
import com.alsc.net.bean.NoticeResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/23.
 */

public class NoticeResultEntity implements Serializable {

    private List<NoticeResult> list;
    private PageResult page;

    public List<NoticeResult> getList() {
        return list;
    }

    public void setList(List<NoticeResult> list) {
        this.list = list;
    }

    public PageResult getPage() {
        return page;
    }

    public void setPage(PageResult page) {
        this.page = page;
    }
}
