package com.alsc.net.bean.request;

/**
 *
 * 登录请求
 | 字段  | 类型   | 是否可选 | 描述  |
 | ----- | ------ | -------- | ----- |
 | token | String | 否       | token |
 | old_pwd | String | 否       | 旧密码 |
 | new_pwd | String | 否       | 新密码 |
 | code | String | 否       | 验证码 |
 | type | int | 否       | 类型 1手机验证  2邮箱验证 |
 */
public class ModifyPswRequest {

    private String token;
    private String old_pwd;
    private String new_pwd;
    private String code;
    private String sid;
    private int type;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOld_pwd() {
        return old_pwd;
    }

    public void setOld_pwd(String old_pwd) {
        this.old_pwd = old_pwd;
    }

    public String getNew_pwd() {
        return new_pwd;
    }

    public void setNew_pwd(String new_pwd) {
        this.new_pwd = new_pwd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
