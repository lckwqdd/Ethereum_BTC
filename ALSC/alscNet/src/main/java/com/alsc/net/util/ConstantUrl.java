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

    //资产相关信息
    String CAPITAL_INFO_URL = "api/waller_info";
    //参与合约
    String CREAT_CONTRACT_URL = "api/create_heyue";
    //信誉明细
    String REPUTATION_INFO_URL = "api/xingyu_info";
    //奖池明细
    String JACKPOT_INFO_URL = "api/jackpot";
    //共享明细
    String SHARED_INFO_URL = "api/team_log";
    //获取用户直推用户列表
    String RECOMMEND_INFO_URL = "api/recommend";
    //转账/兑换记录
    String TRANSFER_RECORD_URL = "api/transfer_list";
    //收益总额
    String INCOME_TOTAL_URL = "api/log";

    //手机区号列表
    String PHONE_CODE_URL = "api/phone_codes";
    //手机验证码
    String MOBILE_CODE_URL = "api/get_mobile_code";
    //修改登录密码
    String MODIFY_LOGIN_PSW_URL = "api/up_password";
    //修改支付密码
    String MODIFY_PAY_PSW_URL = "api/up_pay";
    //绑定手机
    String BIND_PHONE_URL = "api/bang_phone";
    //绑定邮箱
    String BIND_EMAIL_URL = "api/bang_email";
    //上传头像
    String UPDATE_LOGO_URL = "api/up_logo";
    //修改昵称
    String UPDATE_NAME_URL = "api/up_uname";
    //找回密码
    String FIND_PASSWORD_URL = "api/find_password";
    //验证支付密码
    String CHECK_PAY_URL = "api/check_pay";
    //参与贡献
    String CREATE_HEYUE_URL = "api/create_heyue";

}
