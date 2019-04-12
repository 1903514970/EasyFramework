package com.dj.frameworklib.http;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dengjun on 2019/3/25.
 */

public class CommonHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originRequest = chain.request();
        Request newRequest = originRequest.newBuilder()
                .addHeader("DeviceType", "Android")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .build();
        return chain.proceed(newRequest);
    }
}
