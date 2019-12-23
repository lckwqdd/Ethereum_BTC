package com.alsc.net.bean.entity;

import com.alsc.net.bean.NoticePageResult;
import com.alsc.net.bean.NoticeResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/23.
 */

public class NoticeResultEntity implements Serializable {

    private List<NoticeResult> list;
    private NoticePageResult page;

    public List<NoticeResult> getList() {
        return list;
    }

    public void setList(List<NoticeResult> list) {
        this.list = list;
    }

    public NoticePageResult getPage() {
        return page;
    }

    public void setPage(NoticePageResult page) {
        this.page = page;
    }
}
