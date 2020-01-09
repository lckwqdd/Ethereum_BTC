package com.alsc.wallet.bean.response;

import com.alsc.wallet.bean.BasicData;

/**
 * *返回参数说明**
 * <p>
 * | 字段      | 类型 | 描述         |
 * | --------- | ---- | ------------ |
 * | id | int  |  id         |
 * | amount     | float  | 金额     |
 * | hash     | string  |  交易hash    |
 * | status     | int  |  状态   1确认中  2已确认 3.交易失败  |
 * | type     | int  |  类型   1.收款 2转账  |
 * | add_time     | datetime  |  转账时间     |
 * | miner_fee     | float  |  矿工费     |
 */
public class CodeTransferInfoResponse extends BasicData<CodeTransferInfoResponse> {

    /**
     * id : 7407
     * amount : 20.00000000
     * hash :
     * status : 1
     * type : 2
     * add_time : 2020-01-07 16:27:11
     * miner_fee : 0.05660000
     */

    private int id;
    private String amount;
    private String hash;
    private int status;
    private int type;
    private String add_time;
    private String miner_fee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getMiner_fee() {
        return miner_fee;
    }

    public void setMiner_fee(String miner_fee) {
        this.miner_fee = miner_fee;
    }
}
