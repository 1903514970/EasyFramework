package com.dj.frameworklib.app;

import android.app.Application;

import com.dj.frameworklib.http.retrofit.HttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by dengjun on 2019/3/25.
 */

public class MainApplication extends Application {

    private static MainApplication instance;

    public static MainApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //设置网络请求OkHttpClient
        HttpUtils.setOkHttpClient(initOkHttpClient());
    }

    private OkHttpClient initOkHttpClient(){

        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(5,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .callTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)//设置日志打印拦截器
                .build();
    }

}
