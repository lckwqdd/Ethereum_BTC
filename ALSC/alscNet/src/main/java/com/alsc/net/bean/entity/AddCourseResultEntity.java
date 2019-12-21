package com.alsc.net.bean.entity;

import com.alsc.net.bean.AddCourseResult;

import java.io.Serializable;

/**
 * Created by Mirko on 2018/4/18.
 */

public class AddCourseResultEntity implements Serializable {

    private AddCourseResult course;

    public AddCourseResult getCourse() {
        return course;
    }

    public void setCourse(AddCourseResult course) {
        this.course = course;
    }
}
