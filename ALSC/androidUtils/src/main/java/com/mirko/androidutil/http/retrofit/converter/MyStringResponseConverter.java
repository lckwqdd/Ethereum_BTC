package com.mirko.androidutil.http.retrofit.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author sam
 * @version 1.0
 * @date 2018/3/14
 */

public class MyStringResponseConverter implements Converter<ResponseBody, String> {
    
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}

