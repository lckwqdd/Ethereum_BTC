package com.mirko.alsc.utils;



public interface Constant {


    /**错误码*/
    String EXTRA_REGISTER_REQUST = "reigster_requst";

    String  CACHE_PATH = "ALSC/ImageLoader/Cache";


    /**绑定手机状态*/
    String EXTRA_KEY_IS_BING_PHONE = "bind_phone";
    /**绑定邮箱状态*/
    String EXTRA_KEY_IS_BING_EMAIL = "bind_email";
    /**区号*/
    String EXTRA_KEY_AREA_CODE = "area_code";
    /**个人信息*/
    String EXTRA_KEY_USER_INFO = "user_info";
    /**修改密码信息*/
    String EXTRA_KEY_RESERT_PWD = "resert_pwd";

    /**手机跳转类型*/
    String EXTRA_SECUTITY_PHONE_TYPE = "phone_type";
    int SECUTITY_PHONE_TYPE_BIND = 1; //绑定
    int SECUTITY_PHONE_TYPE_MODITY = 2; //修改
    String EXTRA_SECUTITY_EMAIL_TYPE = "email_type";
    int SECUTITY_EMAIL_TYPE_BIND = 1; //绑定
    int SECUTITY_EMAIL_TYPE_MODITY = 2; //修改

    int TYPE_PHONE = 1;
    int TYPE_EMAIL = 2;

    int NOTICE_TYPE_SYSTEM = 1;
    int NOTICE_TYPE_ARTICLE = 2;



}
