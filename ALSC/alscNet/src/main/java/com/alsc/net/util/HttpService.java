package com.alsc.net.util;

import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.bean.entity.LoginResultEntity;
import com.alsc.net.bean.entity.NoticeDetailResultEntity;
import com.alsc.net.bean.entity.NoticeResultEntity;
import com.alsc.net.bean.entity.PicCodeResultEntity;
import com.alsc.net.bean.entity.RegisterResultEntity;
import com.alsc.net.retrofit.entity.BaseResultEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Mirko on 2016/11/30.
 *
 */

public interface HttpService {

//    @retrofit.http.POST("promotion/collection")
//    Call<ProductEntity> getAllVedio(@retrofit.http.Body boolean once_no);

//    @POST("promotion/collection")
//    Observable<ProductEntity> getAllVedioBy(@Body boolean once_no);


    @POST(ConstantUrl.NOTICE_URL)
    Observable<BaseResultEntity<NoticeResultEntity>> getNotice(@Body RequestBody body);

    @POST(ConstantUrl.REGISTER_URL)
    Observable<BaseResultEntity<RegisterResultEntity>> register(@Body RequestBody body);

    @POST(ConstantUrl.LOGIN_URL)
    Observable<BaseResultEntity<LoginResultEntity>> login(@Body RequestBody body);

    @POST(ConstantUrl.EMAIL_CODE_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> getEmailCode(@Body RequestBody body);

    @POST(ConstantUrl.PIC_CODE_URL)
    Observable<BaseResultEntity<PicCodeResultEntity>> getPicCode(@Body RequestBody body);

    @POST(ConstantUrl.HOME_MSG_URL)
    Observable<BaseResultEntity<HomeMsgResultEntity>> getHomeMsg(@Body RequestBody body);

    @POST(ConstantUrl.NOTICE_DETAIL_URL)
    Observable<BaseResultEntity<NoticeDetailResultEntity>> getNoticeDetail(@Body RequestBody body);



}
