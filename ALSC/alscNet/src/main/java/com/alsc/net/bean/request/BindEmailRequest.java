package com.alsc.net.bean.request;

/**
 *
 * 绑定Email请求体
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 | email | String | 否       | 邮箱 |
 | code | String | 否       | 验证码 |
 */
public class BindEmailRequest {

    private String token;
    private String email;
    private String code;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
