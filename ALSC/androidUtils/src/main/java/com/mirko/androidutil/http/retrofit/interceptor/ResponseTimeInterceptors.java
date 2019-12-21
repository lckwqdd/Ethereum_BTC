package com.mirko.androidutil.http.retrofit.interceptor;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author
 * @version 1.0
 * @date 2018/3/9
 */

public class ResponseTimeInterceptors implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String timestamp = response.header("time");
        if (timestamp != null) {
            //获取到响应header中的time
        }
        return response;
    }
}
