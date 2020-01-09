package com.mirko.alsc.bean;

public class AlscCurrentData {
    private String name; //币种名字
    private String time; //交易时间
    private String mount; //交易金额
    private String state; //交易状态

    public AlscCurrentData() {
    }

    public AlscCurrentData(String name, String time, String mount, String state) {
        this.name = name;
        this.time = time;
        this.mount = mount;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
