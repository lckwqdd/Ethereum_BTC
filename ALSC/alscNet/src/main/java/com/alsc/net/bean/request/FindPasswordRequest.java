package com.alsc.net.bean.request;

import java.io.Serializable;

/**
 *
 * 找回密码请求体
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | email | String | 是       | 邮箱 |
 | mobile | String | 是       | 手机号 |
 | phone_code | int | 是       | 手机区号 |
 | code | String | 否       | 验证码 |
 | pwd | String | 否       | 密码 |
 | pwd2 | String | 否       | 密码2 |
 | sid | String | 否       | sessionid |
 */
public class FindPasswordRequest implements Serializable {

    private String email;
    private String mobile;
    private String phone_code;
    private String code;
    private String pwd;
    private String pwd2;
    private String sid;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
