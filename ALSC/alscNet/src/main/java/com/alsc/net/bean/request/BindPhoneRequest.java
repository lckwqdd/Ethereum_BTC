package com.alsc.net.bean.request;

/**
 *
 * 绑定手机请求体
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 | phone | String | 否       | 邮箱 |
 | code | String | 否       | 验证码 |
 */
public class BindPhoneRequest {

    private String token;
    private String phone;
    private String code;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
