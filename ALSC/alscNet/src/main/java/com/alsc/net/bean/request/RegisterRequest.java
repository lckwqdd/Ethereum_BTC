package com.alsc.net.bean.request;

/**
 *
 * 注册请求
 *  | 字段  | 类型   | 是否可选 | 描述  |
 *  | ----- | ------ | -------- | ----- |
 *  | pay_pass | String | 否       | 支付密码 |
 *  | email | String | 否       | 邮箱 |
 *  | pwd | String | 否       | 密码 |
 *  | code | String | 否       | 图片验证码 |
 *  | invite_code | String | 否       | 邀请码 |
 *  | captcha | String | 否       | 邮箱验证码 |
 */
public class RegisterRequest {

    private String pay_pass;
    private String email;
    private String pwd;
    private String code;
    private String invite_code;
    private String captcha;

    public String getPay_pass() {
        return pay_pass;
    }

    public void setPay_pass(String pay_pass) {
        this.pay_pass = pay_pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
