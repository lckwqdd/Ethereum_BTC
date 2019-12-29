package com.alsc.net.util;

import com.alsc.net.bean.entity.CapitalResultEntity;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.bean.entity.IncomeTotalResultEntity;
import com.alsc.net.bean.entity.JackPotResultEntity;
import com.alsc.net.bean.entity.LoginResultEntity;
import com.alsc.net.bean.entity.NoticeDetailResultEntity;
import com.alsc.net.bean.entity.NoticeResultEntity;
import com.alsc.net.bean.entity.PhoneCodeResultEntity;
import com.alsc.net.bean.entity.PicCodeResultEntity;
import com.alsc.net.bean.entity.RealPriceResultEntity;
import com.alsc.net.bean.entity.RecommendUserEntity;
import com.alsc.net.bean.entity.RegisterResultEntity;
import com.alsc.net.bean.entity.ReputationResultEntity;
import com.alsc.net.bean.entity.SharedResultEntity;
import com.alsc.net.bean.entity.TransferRecordeEntity;
import com.alsc.net.retrofit.entity.BaseResultEntity;
import com.alsc.net.bean.entity.TranstionResultEntity;
import com.alsc.net.bean.entity.AddressRegisterResultEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Mirko on 2016/11/30.
 */

public interface HttpService {


    @POST(ConstantUrl.NOTICE_URL)
    Observable<BaseResultEntity<NoticeResultEntity>> getNotice(@Body RequestBody body);

    @POST(ConstantUrl.REGISTER_URL)
    Observable<BaseResultEntity<RegisterResultEntity>> register(@Body RequestBody body);

    @POST(ConstantUrl.LOGIN_URL)
    Observable<BaseResultEntity<LoginResultEntity>> login(@Body RequestBody body);

    @POST(ConstantUrl.EMAIL_CODE_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> getEmailCode(@Body RequestBody body);

    @GET(ConstantUrl.PIC_CODE_URL)
    Observable<BaseResultEntity<PicCodeResultEntity>> getPicCode();

    @POST(ConstantUrl.HOME_MSG_URL)
    Observable<BaseResultEntity<HomeMsgResultEntity>> getHomeMsg(@Body RequestBody body);

    @POST(ConstantUrl.NOTICE_DETAIL_URL)
    Observable<BaseResultEntity<NoticeDetailResultEntity>> getNoticeDetail(@Body RequestBody body);

    @GET(ConstantUrl.PHONE_CODE_URL)
    Observable<BaseResultEntity<PhoneCodeResultEntity>> getPhoneCode();

    @POST(ConstantUrl.MOBILE_CODE_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> getMobileCode(@Body RequestBody body);

    @POST(ConstantUrl.MODIFY_LOGIN_PSW_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> modifyLoginPsw(@Body RequestBody body);

    @POST(ConstantUrl.MODIFY_PAY_PSW_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> modifyPayPsw(@Body RequestBody body);

    @POST(ConstantUrl.BIND_PHONE_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> bindPhone(@Body RequestBody body);

    @POST(ConstantUrl.BIND_EMAIL_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> bindEmail(@Body RequestBody body);

    @POST(ConstantUrl.UPDATE_NAME_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> updateName(@Body RequestBody body);

    @POST(ConstantUrl.UPDATE_LOGO_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> updateLogo(@Body RequestBody body);

    @POST(ConstantUrl.FIND_PASSWORD_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> findPassword(@Body RequestBody body);

    @POST(ConstantUrl.CHECK_PAY_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> devoteCheckPayPwd(@Body RequestBody body);

    @POST(ConstantUrl.DEVOTE_CREATE_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> devoteCreat(@Body RequestBody body);

    @POST(ConstantUrl.DEVOTE_TRANSFER_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> devoteTransfer(@Body RequestBody body);

    @POST(ConstantUrl.DEVOTE_EXCHANGE_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> devoteExchange(@Body RequestBody body);

    @POST(ConstantUrl.DEVOTE_SUPER_URL)
    Observable<BaseResultEntity<EmptyResultEntity>> devoteSuper(@Body RequestBody body);

    @GET(ConstantUrl.DEVOTE_ALSC_PRICE_URL)
    Observable<BaseResultEntity<RealPriceResultEntity>> devotePriceReal();


    @POST(ConstantUrl.REPUTATION_INFO_URL)
    Observable<BaseResultEntity<ReputationResultEntity>> devoteReputationInfo(@Body RequestBody body);

    @POST(ConstantUrl.JACKPOT_INFO_URL)
    Observable<BaseResultEntity<JackPotResultEntity>> devoteJackpotInfo(@Body RequestBody body);

    @POST(ConstantUrl.SHARED_INFO_URL)
    Observable<BaseResultEntity<SharedResultEntity>> devoteSharedInfo(@Body RequestBody body);

    @POST(ConstantUrl.TRANSFER_RECORD_URL)
    Observable<BaseResultEntity<TransferRecordeEntity>> wTransferRecord(@Body RequestBody body);

    @POST(ConstantUrl.INCOME_TOTAL_URL)
    Observable<BaseResultEntity<IncomeTotalResultEntity>> wIncomeTotal(@Body RequestBody body);

    @POST(ConstantUrl.CAPITAL_INFO_URL)
    Observable<BaseResultEntity<CapitalResultEntity>> wCapitalInfo(@Body RequestBody body);

    @POST(ConstantUrl.RECOMMEND_INFO_URL)
    Observable<BaseResultEntity<RecommendUserEntity>> recommendInfo(@Body RequestBody body);

    @POST(ConstantUrl.REGISTER_ADDRESS_URL)
    Observable<BaseResultEntity<AddressRegisterResultEntity>> registerAddress(@Body RequestBody body);

    @POST(ConstantUrl.UPLOAD_TRANSTION_URL)
    Observable<BaseResultEntity<TranstionResultEntity>> uploadTranstion(@Body RequestBody body);


}
