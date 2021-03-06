package com.alsc.net.retrofit.http.cookie;

import com.alsc.net.db.CookieDbUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * gson持久化截取保存数据
 * Created by WZG on 2016/10/20.
 */
public class CookieInterceptor implements Interceptor {
    private CookieDbUtil dbUtil;
    /*是否缓存标识*/
    private boolean cache;

    public CookieInterceptor( boolean cache) {
        dbUtil=CookieDbUtil.getInstance();
        this.cache=cache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
//        Request build = builder.addHeader(ConstantApi.HEAD_NAME, ConstantApi.HEAD_VALUE).build(); //添加请求头

        builder.addHeader("Accept", "*/*");
        builder.addHeader("Content-Type", "application/json;charset=UTF-8;");
        builder.addHeader("Cache-Control", "no-cache");

//        if (CacheManager.LoginTookenMsg.get() != null) {
//            LoginTookenMsg tookenMsg = CacheManager.LoginTookenMsg.get();
//            if (tookenMsg.getTooken() != null && tookenMsg.getTookenV() != null && tookenMsg.getTooken().length() > 0 && tookenMsg.getTookenV().length() > 0) {
//                builder.addHeader("token", tookenMsg.getTooken());
//                builder.addHeader("tokenV", tookenMsg.getTookenV());
//            }
//        }
        Request build = builder.build();
//        LogUtils.d("SyncData","请求头："+build.headers().toString());
        Response response = chain.proceed(build);
        if(cache){
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = Charset.defaultCharset();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String bodyString = buffer.clone().readString(charset);
            String url = request.url().toString();
            CookieResulte resulte= dbUtil.queryCookieBy(url);
            long time=System.currentTimeMillis();
            /*保存和更新本地数据*/
            if(resulte==null){
                resulte  =new CookieResulte(url,bodyString,time);
                dbUtil.saveCookie(resulte);
            }else{
                resulte.setResulte(bodyString);
                resulte.setTime(time);
                dbUtil.updateCookie(resulte);
            }
        }
        return response;
    }
}
