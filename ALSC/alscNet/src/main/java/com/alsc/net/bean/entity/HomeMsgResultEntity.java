package com.alsc.net.bean.entity;

import com.alsc.net.bean.UserInfoResult;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/23.
 * | 字段      | 类型 | 描述         |
 * | --------- | ---- | ------------ |
 |
 * | sinvestment | string  |  超级节点剩余alsc数量         |
 * | contri     | float  |  贡献额     |
 * | totalJackpot     | float  | 全球矿池     |
 * | surplusRep     | float  |  信誉值     |
 * | max_sinvestment     | float  |  超级节点投资额     |
 * | share_total     | float  |  共享矿池     |
 * | fenx_total     | float  |  分享算力     |
 */

public class HomeMsgResultEntity implements Serializable {

    private UserInfoResult user_info;
    private String sinvestment;
    private float contri;
    private float totalJackpot;
    private float surplusRep;
    private float max_sinvestment;
    private float share_total;
    private float fenx_total;

    public UserInfoResult getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoResult user_info) {
        this.user_info = user_info;
    }

    public String getSinvestment() {
        return sinvestment;
    }

    public void setSinvestment(String sinvestment) {
        this.sinvestment = sinvestment;
    }

    public float getContri() {
        return contri;
    }

    public void setContri(float contri) {
        this.contri = contri;
    }

    public float getTotalJackpot() {
        return totalJackpot;
    }

    public void setTotalJackpot(float totalJackpot) {
        this.totalJackpot = totalJackpot;
    }

    public float getSurplusRep() {
        return surplusRep;
    }

    public void setSurplusRep(float surplusRep) {
        this.surplusRep = surplusRep;
    }

    public float getMax_sinvestment() {
        return max_sinvestment;
    }

    public void setMax_sinvestment(float max_sinvestment) {
        this.max_sinvestment = max_sinvestment;
    }

    public float getShare_total() {
        return share_total;
    }

    public void setShare_total(float share_total) {
        this.share_total = share_total;
    }

    public float getFenx_total() {
        return fenx_total;
    }

    public void setFenx_total(float fenx_total) {
        this.fenx_total = fenx_total;
    }
}
