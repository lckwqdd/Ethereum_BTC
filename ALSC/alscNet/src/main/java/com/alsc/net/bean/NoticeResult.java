package com.alsc.net.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mirko on 2019/12/23.
 *
 * | list.aid | int  |  文章id         |
 * | list.add_time     | date  |  创建时间     |
 * | list.synopsis     | string  |  简介     |
 * | list.synopsis_en     | string  |  简介 （英文）    |
 * | list.is_top     | int  |  是否置顶 1是     |
 * | list.title     | string  |  标题     |
 * | list.title_en     | string  |  标题（英文）     |
 */

public class NoticeResult implements Serializable {

    private int aid;
    private String add_time;
    private String synopsis;
    private String synopsis_en;
    private int is_top;
    private String title;
    private String title_en;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getSynopsis_en() {
        return synopsis_en;
    }

    public void setSynopsis_en(String synopsis_en) {
        this.synopsis_en = synopsis_en;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }
}
