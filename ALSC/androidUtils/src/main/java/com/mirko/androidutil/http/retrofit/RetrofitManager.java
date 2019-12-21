package com.mirko.androidutil.http.retrofit;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mirko.androidutil.http.HttpConfig;
import com.mirko.androidutil.http.data.BaseResponse;
import com.mirko.androidutil.http.data.JsonFormatParser;
import com.mirko.androidutil.http.retrofit.factory.MyGsonConverterFactory;
import com.mirko.androidutil.http.retrofit.factory.MyStringConverterFactory;
import com.mirko.androidutil.http.retrofit.interceptor.BaseUrlInterceptor;
import com.mirko.androidutil.http.retrofit.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 *
 * 获取创建的retriofit对象
 *
 */
public class RetrofitManager {

    private static final String TAG = "RetrofitManager";
    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;
    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .registerTypeAdapter(BaseResponse.class, new JsonFormatParser(BaseResponse.class))
            .create();
    private RetrofitManager() {
        initRetrofit();
    }

    public static synchronized RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                mRetrofitManager = new RetrofitManager();
            }
        }
        return mRetrofitManager;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.baseURL)//配置服务器路径
                .addConverterFactory(MyStringConverterFactory.create())//配置转化库，String
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //配置支持rxjava2
//                .addConverterFactory(FastJsonConverterFactory.create())//配置转化库，FastJson
                .addConverterFactory(MyGsonConverterFactory.create())//配置转化库，Gson
                .client(genericClient())
                .build();
    }

    public static OkHttpClient genericClient() {

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(5, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        //新建log拦截器
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(HttpConfig.TAG, "genericClient log: " + message);
            }
        });
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BASIC;
        loggingInterceptor.setLevel(level);
        if (HttpConfig.isNetRequestInterceptor) {
            builder.addInterceptor(loggingInterceptor);//添加retrofit日志打印 } 
        }
        if (HttpConfig.isBaseURLInterceptor) {
            builder.addInterceptor(new BaseUrlInterceptor());
        }
        if (HttpConfig.isHeaderInterceptor) {
            builder.addInterceptor(new HeaderInterceptor());
        }
        OkHttpClient httpClient = builder.build();
        return httpClient;
    }


}
