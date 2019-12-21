package com.alsc.net.bean;

import java.io.Serializable;

/**
 * Created by Mirko on 2018/4/18.
 * 添加功课成功结果
 */

public class AddCourseResult  implements Serializable {

    private int id;
    private String courseName;
    private int courseHot ;
    private long handLimit;
    private int perType ;
    private long createTime ;
    private int courseStatus ;
    private boolean myCourse ;
    private int courseType ;
    private long  updateTime ;
    private String userDevice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseHot() {
        return courseHot;
    }

    public void setCourseHot(int courseHot) {
        this.courseHot = courseHot;
    }

    public long getHandLimit() {
        return handLimit;
    }

    public void setHandLimit(long handLimit) {
        this.handLimit = handLimit;
    }

    public int getPerType() {
        return perType;
    }

    public void setPerType(int perType) {
        this.perType = perType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(int courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isMyCourse() {
        return myCourse;
    }

    public void setMyCourse(boolean myCourse) {
        this.myCourse = myCourse;
    }

    public String getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(String userDevice) {
        this.userDevice = userDevice;
    }

    @Override
    public String toString() {
        return "AddCourseResult{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", courseHot=" + courseHot +
                ", handLimit=" + handLimit +
                ", perType=" + perType +
                ", createTime=" + createTime +
                ", courseStatus=" + courseStatus +
                ", myCourse=" + myCourse +
                ", courseType=" + courseType +
                ", updateTime=" + updateTime +
                '}';
    }
}
