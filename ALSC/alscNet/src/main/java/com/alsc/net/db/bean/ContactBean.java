package com.alsc.net.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ContactBean {
    @Id(autoincrement = true)
    private Long id;

    private String name;
    private String note;
    private String alscAddress;
    @Generated(hash = 1055238414)
    public ContactBean(Long id, String name, String note, String alscAddress) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.alscAddress = alscAddress;
    }
    @Generated(hash = 1283900925)
    public ContactBean() {
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
    public String getNote() {
        return this.note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getAlscAddress() {
        return this.alscAddress;
    }
    public void setAlscAddress(String alscAddress) {
        this.alscAddress = alscAddress;
    }
    

}
