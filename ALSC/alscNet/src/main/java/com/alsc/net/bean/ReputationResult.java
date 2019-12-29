package com.alsc.net.bean;

import java.io.Serializable;

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

public class ReputationResult implements Serializable {

    private float gx;
    private float xy;
    private float js;
    private String add_time;

    public float getGx() {
        return gx;
    }

    public void setGx(float gx) {
        this.gx = gx;
    }

    public float getXy() {
        return xy;
    }

    public void setXy(float xy) {
        this.xy = xy;
    }

    public float getJs() {
        return js;
    }

    public void setJs(float js) {
        this.js = js;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
