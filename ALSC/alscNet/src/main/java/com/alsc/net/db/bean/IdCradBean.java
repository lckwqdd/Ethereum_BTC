package com.alsc.net.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by win 10 on 2019-04-12.
 */
@Entity
public class IdCradBean {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String sex;
    private String nation;
    private String birth;
    private String address;
    private String idNumber;
    private String depart;
    private byte[] photo;
    private String validityTime;
    @Generated(hash = 1263519112)
    public IdCradBean(Long id, String name, String sex, String nation, String birth,
                      String address, String idNumber, String depart, byte[] photo,
                      String validityTime) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.birth = birth;
        this.address = address;
        this.idNumber = idNumber;
        this.depart = depart;
        this.photo = photo;
        this.validityTime = validityTime;
    }
    @Generated(hash = 963691596)
    public IdCradBean() {
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
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getNation() {
        return this.nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public String getBirth() {
        return this.birth;
    }
    public void setBirth(String birth) {
        this.birth = birth;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getIdNumber() {
        return this.idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public String getDepart() {
        return this.depart;
    }
    public void setDepart(String depart) {
        this.depart = depart;
    }
    public byte[] getPhoto() {
        return this.photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    public String getValidityTime() {
        return this.validityTime;
    }
    public void setValidityTime(String validityTime) {
        this.validityTime = validityTime;
    }
}
