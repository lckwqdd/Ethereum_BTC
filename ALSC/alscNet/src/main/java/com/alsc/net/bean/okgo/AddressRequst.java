package com.alsc.net.bean.okgo;

import com.alsc.net.bean.JackPotResult;
import com.alsc.net.bean.PageResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mirko on 2019/12/28.



 */

public class AddressRequst implements Serializable {

    private List<AddressList> list;
    private int total_count;
    private int pagesize;
    private int page;

    public List<AddressList> getList() {
        return list;
    }

    public void setList(List<AddressList> list) {
        this.list = list;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
