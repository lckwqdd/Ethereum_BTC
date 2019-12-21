package com.alsc.net.retrofit.entity;

/**
 * Created by Mirko on 2016/11/30.
 *
 * 共用部分，通过泛型传入具体数据实体。
 *
 * 通过HttpService 里面来定义请求对象和返回
 */

public class BaseResultEntity<T> {

    //  判断标示
    private boolean success;
    //    提示信息
    private String msg;
    //显示数据（用户需要关心的数据）
    private T data;

    private String statusMsg;

    private int statusCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
