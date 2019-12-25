package com.alsc.net.bean.request;

import java.io.Serializable;

/**
 *
 * 注册请求
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | username | String | 否       | 用户名 |
 | email | String | 是       | 邮箱 |
 | phone | String | 是       | 手机|
 | pwd | String | 否       | 登录密码 |
 | pic_code | String | 否       | 图片验证码 |
 | invite_code | String | 否       | 邀请码 |
 | captcha | String | 否       | 手机验证码 邮箱验证码 |
 | pwd2 | String | 否       | 登录密码2 |
 | pay_pwd | String | 否       | 支付密码 |
 | pay_pwd2 | String | 否       | 支付密码2 |
 */
public class RegisterRequest implements Serializable{

    private String username;
    private String email;
    private String phone;
    private String pwd;
    private String pic_code;
    private String invite_code;
    private String captcha;
    private String pwd2;
    private String pay_pwd;
    private String pay_pwd2;
    private String sid;
    private String phone_area;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPic_code() {
        return pic_code;
    }

    public void setPic_code(String pic_code) {
        this.pic_code = pic_code;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
    }

    public String getPay_pwd() {
        return pay_pwd;
    }

    public void setPay_pwd(String pay_pwd) {
        this.pay_pwd = pay_pwd;
    }

    public String getPay_pwd2() {
        return pay_pwd2;
    }

    public void setPay_pwd2(String pay_pwd2) {
        this.pay_pwd2 = pay_pwd2;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPhone_area() {
        return phone_area;
    }

    public void setPhone_area(String phone_area) {
        this.phone_area = phone_area;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", pic_code='" + pic_code + '\'' +
                ", invite_code='" + invite_code + '\'' +
                ", captcha='" + captcha + '\'' +
                ", pwd2='" + pwd2 + '\'' +
                ", pay_pwd='" + pay_pwd + '\'' +
                ", pay_pwd2='" + pay_pwd2 + '\'' +
                ", sid='" + sid + '\'' +
                '}';
    }
}
