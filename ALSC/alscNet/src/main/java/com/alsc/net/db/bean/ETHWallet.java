package com.alsc.net.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 以太坊钱包账号实体类
 */

@Entity
public class ETHWallet {

    @Id(autoincrement = true)
    private Long id;

    /**
     * 以太坊钱包相关
     * */
    private String name;
    private String password;
    public  String address;
    private String keystorePath;
    private String mnemonic;

    private boolean isCurrent;
    private boolean isBackup;
    @Generated(hash = 196828815)
    public ETHWallet(Long id, String name, String password, String address,
            String keystorePath, String mnemonic, boolean isCurrent,
            boolean isBackup) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.keystorePath = keystorePath;
        this.mnemonic = mnemonic;
        this.isCurrent = isCurrent;
        this.isBackup = isBackup;
    }
    @Generated(hash = 1963897189)
    public ETHWallet() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getKeystorePath() {
        return this.keystorePath;
    }
    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }
    public String getMnemonic() {
        return this.mnemonic;
    }
    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
    public boolean getIsCurrent() {
        return this.isCurrent;
    }
    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }
    public boolean getIsBackup() {
        return this.isBackup;
    }
    public void setIsBackup(boolean isBackup) {
        this.isBackup = isBackup;
    }



}
