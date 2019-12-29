package com.alsc.net.bean;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/23.
 *
 * | page.counts | int  |  总条数        |
 * | page.pages  | int  | 总页面数 |
 * | page.currPage     | int  | 当前页面     |
 * | page.pageSize     | int  |  每页显示条数     |
 */

public class PageResult implements Serializable {

    private int counts;
    private int pages;
    private int currPage;
    private int pageSize;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
