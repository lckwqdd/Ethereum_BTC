package com.mirko.androidutil.http.data;

/**
    方法1：我们可以让后台返回的json数据中的data永远是个数组。
    方法2：1、自定义Gson响应体变换器 2、自定义一个响应变换工厂 继承自 retrofit的 converter.Factory
           3、在我们的自定义的Rxjava订阅者 subscriber中的onError（）中加入我们刚才定义的ResultException。
 */
public class BaseResponse<T> {
    public int statusCode;
    public String msg;
    public T data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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


    @Override
    public String toString() {
        return "{" +
                "data=" + data +
                ", code=" + statusCode +
                ", msg='" + msg + '\'' +
                '}';
    }
    
    public String getNewJsonStrin(){
        return "{" +
                "data=" + data +
                ", code=" + statusCode +
                ", msg='" + msg + '\'' +
                '}';
    }
}
