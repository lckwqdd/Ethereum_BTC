package com.mirko.alsc.utils;

import android.app.Activity;
import android.util.Log;

import com.alsc.net.api.EmailCodeApi;
import com.alsc.net.api.HomeMsgApi;
import com.alsc.net.api.MobileCodeApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.bean.okgo.AddressEntity;
import com.alsc.net.bean.request.AddressRegisterRequest;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.net.util.ConstantUrl;
import com.alsc.wallet.bean.request.CodeTransfersInofRequest;
import com.alsc.wallet.bean.request.TransfersRequest;
import com.alsc.wallet.bean.request.TranslationRequest;
import com.alsc.wallet.bean.response.BalanceBeanResponse;
import com.alsc.wallet.bean.response.CodeTransferInfoResponse;
import com.alsc.wallet.bean.response.TranlationResponse;
import com.alsc.wallet.bean.response.TransferResponse;
import com.alsc.wallet.entity.ethTranstionBean;
import com.alsc.wallet.entity.nonceBean;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.Call;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;
import com.mirko.alsc.constant.Constants;
import com.mirko.androidutil.utils.android.LogUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by Mirko on 2019/12/27 08:09.
 */
public class UrlRequstUtils {

    private static final String TAG = "ComUtils";
    private static final String basicUrl = "http://45.77.37.117:8001/";


    /**
     * 获取手机验证码
     */
    public static void getMobileCode(RxAppCompatActivity activity, String phone, String areaCode) {

        HttpManager.getInstance().doHttpDeal(new MobileCodeApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取手机验证码成功:");
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d(TAG, "获取手机验证码失败：" + e.toString());
                super.onError(e);
            }


        }), activity, phone, areaCode, ComUtils.getSid()));
    }


    /**
     * 获取邮箱验证码
     */
    public static void getEmailCode(RxAppCompatActivity activity, String email) {

        HttpManager.getInstance().doHttpDeal(new EmailCodeApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {

                if (result != null) {
                    LogUtils.d(TAG, "获取邮箱验证码成功:");
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d(TAG, "获取邮箱验证码失败：" + e.toString());
                super.onError(e);
            }


        }), activity, email, ComUtils.getSid()));
    }

    /**
     * 加载首页数据
     */
    public static void loadHomeData(RxAppCompatActivity activity) {

        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new HomeMsgApi((new HttpOnNextListener<HomeMsgResultEntity>() {
            @Override
            public void onNext(HomeMsgResultEntity result) {
                if (result != null) {
                    if (result.getUser_info() != null) {
                        UserInfoResult userInfo = result.getUser_info();
                        CacheManager.UserInfoResult.set(result.getUser_info());
                    }
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }


        }), activity, token));
    }


    public static void getUtxo2(Activity activity, String address) {

        String url = "https://blockchain.info/unspent?active=" + address;
        OkGo.<String>get(url)                            // 请求方式和请求url
                .tag(activity)                   // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("get", response.body());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


    public static void getUtxo1(Activity activity, String address) {

        String url = "https://chain.api.btc.com/v3/address/" + address + "/unspent";
        OkGo.<String>get(url)                            // 请求方式和请求url
                .tag(activity)                   // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.NO_CACHE)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("get", response.body());
                        String s = response.body().toString();
                        //解析
                        Gson gson = new Gson();
                        AddressEntity jsonBean = gson.fromJson(s, AddressEntity.class);
                        Log.i("解析后", jsonBean.toString());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


    /**
     * 广播交易
     *
     * @param activity
     * @param hexString
     */
    public static void btcBrocast(Activity activity, String hexString) {

        String url = "https://chain.api.btc.com/tools/tx-decode";
        OkGo.<String>post(url)
                .tag(activity)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.NO_CACHE)
                .params("rawhex", hexString)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        com.alsc.wallet.utils.LogUtils.d("交易成功:" + response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        com.alsc.wallet.utils.LogUtils.d("交易失败:" + response.body().toString());
                        super.onError(response);
                    }
                });
    }

    /**
     * 获取Nonce
     *
     * @param address
     */
    public static String getNonceFromSendAddress(Activity activity, String address) {
        String url = basicUrl + "getNonce/" + address;
        Call<String> call = OkGo.<String>get(url)
                .tag(activity)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.NO_CACHE).converter(new StringConvert()).adapt();
        Response<String> response = null;
        try {
            response = call.execute();
            String s = response.body().toString();
            nonceBean jsonBean = new Gson().fromJson(s, nonceBean.class);
            return jsonBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
            com.alsc.wallet.utils.LogUtils.d("nonceStr异常:" + e.toString());
        }
        return null;
    }

    /**
     * 获取钱包余额
     *
     * @param activity
     * @param addressRegisterRequest
     * @return
     */
    public static BalanceBeanResponse getBalanceByAddress(Activity activity, AddressRegisterRequest addressRegisterRequest) {
        Call<String> call = OkGo.<String>post(Constants.baseUrl + ConstantUrl.REGISTER_ADDRESS_URL)
                .tag(activity)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.NO_CACHE)
                .params("address", addressRegisterRequest.getAddress())
                .params("symbol", addressRegisterRequest.getSymbol())
                .converter(new StringConvert()).adapt();
        Response<String> response = null;
        try {
            response = call.execute();
            String s = response.body().toString();
            BalanceBeanResponse jsonBean = GsonUtils.fromJson(s, BalanceBeanResponse.class);
            return jsonBean;
        } catch (Exception e) {
            e.printStackTrace();
            com.alsc.wallet.utils.LogUtils.d("获取钱包余额异常:" + e.toString());
        }
        return null;
    }


    /**
     * 冷钱包交易广播(以太坊合约/非合约/比特币)
     *
     * @param activity
     * @param translationRequest
     * @return
     */
    public static TranlationResponse sendCodeTranslation(Activity activity, TranslationRequest translationRequest) {
        Call<String> call = OkGo.<String>post(Constants.baseUrl + ConstantUrl.UPLOAD_TRANSTION_URL)
                .tag(activity)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.NO_CACHE)
                .params("hex", translationRequest.getHex())
                .params("from_addr", translationRequest.getFrom_addr())
                .params("to_addr", translationRequest.getTo_addr())
                .params("number", translationRequest.getNumber())
                .params("fee", translationRequest.getFee())
                .params("symbol", translationRequest.getSymbol())
                .converter(new StringConvert()).adapt();
        Response<String> response = null;
        com.alsc.wallet.utils.LogUtils.d("sendCodeTranslation");
        try {
            response = call.execute();
            String s = response.body().toString();
            TranlationResponse jsonBean = GsonUtils.fromJson(s, TranlationResponse.class);
            return jsonBean;
        } catch (Exception e) {
            e.printStackTrace();
            com.alsc.wallet.utils.LogUtils.d("冷钱包交易广播异常:" + e.toString());
        }
        return null;
    }

    /**
     * 冷钱包转账记录列表
     *
     * @param activity
     * @param transfersRequest
     * @return
     */
    public static TransferResponse CodeTransfer(Activity activity, TransfersRequest transfersRequest) {
        Call<String> call = OkGo.<String>post(Constants.baseUrl + ConstantUrl.CODE_WALLET_TRANFER_RECORD)
                .tag(activity)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.NO_CACHE)
                .params("addr", transfersRequest.getAddr())
                .params("symbol", transfersRequest.getSymbol())
                .params("type", transfersRequest.getType())
                .params("page_index", transfersRequest.getPage_index())
                .params("page_size", transfersRequest.getPage_size())
                .converter(new StringConvert()).adapt();
        Response<String> response = null;
        try {
            response = call.execute();
            String s = response.body().toString();
            TransferResponse jsonBean = GsonUtils.fromJson(s, TransferResponse.class);
            return jsonBean;
        } catch (Exception e) {
            e.printStackTrace();
            com.alsc.wallet.utils.LogUtils.d("冷钱包转账记录列表异常:" + e.toString());
        }
        return null;
    }


    /**冷钱包交易转账详情
     * @param activity
     * @param codeTransfersInofRequest
     * @return
     */
    public static CodeTransferInfoResponse CodeTransferInfo(Activity activity, CodeTransfersInofRequest codeTransfersInofRequest) {
        Call<String> call = OkGo.<String>post(Constants.baseUrl + ConstantUrl.CODE_WALLET_TRANFER_DETAIL)
                .tag(activity)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.NO_CACHE)
                .params("id", codeTransfersInofRequest.getId())
                .converter(new StringConvert()).adapt();
        Response<String> response = null;
        try {
            response = call.execute();
            String s = response.body().toString();
            CodeTransferInfoResponse jsonBean = GsonUtils.fromJson(s, CodeTransferInfoResponse.class);
            return jsonBean;
        } catch (Exception e) {
            e.printStackTrace();
            com.alsc.wallet.utils.LogUtils.d("冷钱包转账记录列表异常:" + e.toString());
        }
        return null;
    }


    /**
     * 以太坊广播交易
     *
     * @param activity
     * @param hexStr
     * @return
     */
    public static ethTranstionBean sendEthBrocastTranstion(Activity activity, String hexStr) {
        String url = basicUrl + "sendTransaction/" + hexStr;
        Call<String> call = OkGo.<String>get(url)
                .tag(activity)
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.NO_CACHE).converter(new StringConvert()).adapt();
        Response<String> response = null;
        try {
            response = call.execute();
            String s = response.body().toString();
            ethTranstionBean jsonBean = new Gson().fromJson(s, ethTranstionBean.class);
            return jsonBean;
        } catch (Exception e) {
            e.printStackTrace();
            com.alsc.wallet.utils.LogUtils.d("以太坊广播交易异常:" + e.toString());
        }
        return null;
    }

}
