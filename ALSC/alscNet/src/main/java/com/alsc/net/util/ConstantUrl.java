package com.alsc.net.util;

/**
 * Created by Mirko on 2019/12/23.
 * 请求方法
 *
 */

public interface ConstantUrl {



    /**
     * 请求头名称和值
     */
    String HEAD_NAME = "TD-Agent";
    String HEAD_VALUE = "en_US@currency=USD,device=ios,ad421e72d15c0c3fa230c55cd728d7fd";


    //注册
    String REGISTER_URL = "api/register";
    //登录
    String LOGIN_URL = "api/login";
    //获取邮箱验证码
    String EMAIL_CODE_URL = "api/get_code";
    //获取图片验证码
    String PIC_CODE_URL = "api/captcha";
    //首页信息
    String HOME_MSG_URL = "api/index";
    //文章列表 -- 类型 1公告 2资讯
    String NOTICE_URL = "api/notice";
    //文章详情
    String NOTICE_DETAIL_URL = "api/article";


}
