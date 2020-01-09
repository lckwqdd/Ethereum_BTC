package com.alsc.wallet.bean.response;

import com.alsc.wallet.bean.BasicData;

import java.util.List;


/**
 *
 * **返回参数说明**
 *
 * | 字段      | 类型 | 描述         |
 * | --------- | ---- | ------------ |
 * | list.id | int  |  id         |
 * | list.amount     | float  | 金额     |
 * | list.hash     | string  |  交易hash    |
 * | list.status     | int  |  状态   1确认中  2已确认 3.交易失败  |
 * | list.type     | int  |  类型   1.收款 2转账  |
 * | list.symbol     | int  |  币种     |
 * | list.add_time     | datetime  |  转账时间     |
 * | page.counts | int  |  总条数        |
 * | page.pages  | int  | 总页面数 |
 * | page.currPage     | int  | 当前页面     |
 * | page.pageSize     | int  |  每页显示条数     |*/
public class TransferResponse extends BasicData<TransferResponse> {


    /**
     * list : [{"id":7407,"amount":"20.00000000","hash":"","status":1,"type":2,"add_time":"2020-01-07 16:27:11","symbol":"alsc"},{"id":7408,"amount":"20.00000000","hash":"","status":1,"type":2,"add_time":"2020-01-08 14:14:04","symbol":"alsc"}]
     * page : {"counts":2,"pages":1,"currPage":1,"pageSize":10}
     */

    private PageBean page;
    private List<ListBean> list;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class PageBean {
        /**
         * counts : 2
         * pages : 1
         * currPage : 1
         * pageSize : 10
         */

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

    public static class ListBean {
        /**
         * id : 7407
         * amount : 20.00000000
         * hash :
         * status : 1
         * type : 2
         * add_time : 2020-01-07 16:27:11
         * symbol : alsc
         */

        private int id;
        private String amount;
        private String hash;
        private int status;
        private int type;
        private String add_time;
        private String symbol;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }
}
