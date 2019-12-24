package com.mirko.alsc.bean;

/**
 * Created by Mirko on 2019/12/23 20:57.
 * 币种数据
 */
public class CurrencyData {

    private String name; //币种名字
    private float available; //可用
    private float frozen; //冻结
    private float total; //总资产

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAvailable() {
        return available;
    }

    public void setAvailable(float available) {
        this.available = available;
    }

    public float getFrozen() {
        return frozen;
    }

    public void setFrozen(float frozen) {
        this.frozen = frozen;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
