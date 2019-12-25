package com.alsc.net.bean.request;

/**
 *
 * 登录请求
 * | 字段  | 类型   | 是否可选 | 描述  |
 * | ----- | ------ | -------- | ----- |
 * | name | String | 否       | 账号 |
 * | password | String | 否       | 密码 |
 * | code | String | 否       | 验证码 |
 */
public class LoginRequest {

    private String name;
    private String password;
    private String code;
    private String sid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
