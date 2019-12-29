package com.alsc.net.bean.request;

/**
 *
 * 分页查询
 ##奖池明细 信誉明细  共享明细

 ###请求方式

 `POST   /api/jackpot`

 **请求参数**

 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 | type | int | 是       | 类型 1超级节点 2主节点 |
 | page_size | int | 是       | 每页显示条数 |
 | page_index | int | 是       | 当前第几页 |


 */
public class ListPageRequest {

    private String token;
    private int type;
    private int page_size;
    private int page_index;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
