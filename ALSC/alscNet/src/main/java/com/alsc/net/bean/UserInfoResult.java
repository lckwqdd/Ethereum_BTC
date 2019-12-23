package com.alsc.net.bean;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/23.
 *
 *  * | user_info.uname | string  |  用户名         |
 *  * | user_info.avatar | string  |  头像         |
 *  * | user_info.uid | int  |  用户id         |
 *  * | user_info.real_name | string  |  真实姓名         |
 *  * | user_info.lev | string  |  层级         |
 *  * | user_info.email | string  |  邮箱         |
 *  * | user_info.referrer_id | string  |  推荐用户id         |
 *  * | user_info.is_super | string  |  是否是超级节点 1是         |
 *  * | user_info.total_price | string  |  用户累计投资本金         |
 *  * | user_info.level | string  |  用户等级  (0普通 1vip 2小节点 3中节点 4大节点 5主节点)       |
 *  * | user_info.usdt | string  |  用户usdt余额（充值+产出）        |
 *  * | user_info.alsc | string  |  用户alsc数量余额      |
 *  * | user_info.Investment | string  |  投资总余额(usdt)         |
 *  * | user_info.reputation | string  |  信誉总余额(usdt)         |
 *  * | user_info.total_profit | string  |  已收益总额（usdt）
 */

public class UserInfoResult implements Serializable {

    private String uname;
    private String avatar;
    private int uid;
    private String real_name;
    private String lev;
    private String email;
    private String referrer_id;
    private String is_super;
    private String total_price;
    private String usdt;
    private String alsc;
    private String Investment;
    private String reputation;
    private String total_profit;


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getLev() {
        return lev;
    }

    public void setLev(String lev) {
        this.lev = lev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferrer_id() {
        return referrer_id;
    }

    public void setReferrer_id(String referrer_id) {
        this.referrer_id = referrer_id;
    }

    public String getIs_super() {
        return is_super;
    }

    public void setIs_super(String is_super) {
        this.is_super = is_super;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getUsdt() {
        return usdt;
    }

    public void setUsdt(String usdt) {
        this.usdt = usdt;
    }

    public String getAlsc() {
        return alsc;
    }

    public void setAlsc(String alsc) {
        this.alsc = alsc;
    }

    public String getInvestment() {
        return Investment;
    }

    public void setInvestment(String investment) {
        Investment = investment;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public String getTotal_profit() {
        return total_profit;
    }

    public void setTotal_profit(String total_profit) {
        this.total_profit = total_profit;
    }
}
