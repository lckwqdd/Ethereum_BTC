package com.alsc.net.bean.entity;

import com.alsc.net.bean.NoticePageResult;
import com.alsc.net.bean.NoticeResult;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Mirko on 2019/12/23.
 *
 * | aid | int  |  文章id         |
 * | type  | int  | 文章类型 1公告 2资讯|
 * | content     | string  | 内容     |
 * | add_time     | date  |  创建时间     |
 * | update_time     | date  |  更新时间     |
 * | author     | string  |  作者     |
 * | image_input     | string  | 图片     |
 * | synopsis     | string  |  简介     |
 * | synopsis_en     | string  |  简介（英文）当语言版本不是中文的时候显示英文     |
 * | share_title     | string  |  分享标题（暂无作用）     |
 * | share_synopsis     | string  |  分享简介（暂无作用）     |
 * | status     | int  |  状态 1正常     |
 * | is_show     | int  |  是否显示  1是     |
 * | sort     | int  |  排序     |
 * | is_top     | int  |  是否置顶 0否 1是     |
 * | is_tj     | int  |  是否推荐 0否 1是     |
 * | is_hot     | int  |  是否热点 0否 1是     |
 * | visit     | int  |  浏览数量     |
 * | title     | string  |  标题     |
 * | title_en     | string  |  标题（英文）     |
 * | keyword     | string  |  关键字     |
 * | keyword_en     | string  |  关键字（英文）     |
 * | content_en     | string  |  内容（英文）     |
 */

public class NoticeDetailResultEntity implements Serializable {

    private int aid;
    private int type;
    private String content;
    private Date add_time;
    private Date update_time;
    private String author;
    private String image_input;
    private String synopsis;
    private String synopsis_en;
    private String share_title;
    private String share_synopsis;
    private int status;
    private int is_show;
    private int sort;
    private int is_top;
    private int is_tj;
    private int is_hot;
    private int visit;
    private String title;
    private String title_en;
    private String keyword;
    private String keyword_en;
    private String content_en;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage_input() {
        return image_input;
    }

    public void setImage_input(String image_input) {
        this.image_input = image_input;
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

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_synopsis() {
        return share_synopsis;
    }

    public void setShare_synopsis(String share_synopsis) {
        this.share_synopsis = share_synopsis;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getIs_tj() {
        return is_tj;
    }

    public void setIs_tj(int is_tj) {
        this.is_tj = is_tj;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword_en() {
        return keyword_en;
    }

    public void setKeyword_en(String keyword_en) {
        this.keyword_en = keyword_en;
    }

    public String getContent_en() {
        return content_en;
    }

    public void setContent_en(String content_en) {
        this.content_en = content_en;
    }
}
