package com.alsc.net.bean;

import java.io.Serializable;

/**
 * Created by Mirko on 2019/12/28 22:49
 * 资产数据
 */
public class CapitalData implements Serializable {

    private String sinvestment;
    private float contri;
    private float totalJackpot;
    private float surplusRep;
    private float max_sinvestment;
    private float share_total;
    private float fenx_total;

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

    @Override
    public String toString() {
        return
                " 贡献额=" + contri + "\n"+
                " 全球矿池=" + totalJackpot + "\n"+
                " 信誉值=" + surplusRep + "\n"+
                " 共享矿池=" + share_total + "\n"+
                " 分享算力=" + fenx_total ;
    }
}
