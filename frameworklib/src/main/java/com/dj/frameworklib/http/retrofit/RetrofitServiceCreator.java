package com.dj.frameworklib.http.retrofit;

import com.dj.frameworklib.http.retrofit.converter.StringAndGsonConverterFactory;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by dengjun on 2019/3/21.
 */

public class RetrofitServiceCreator {

    /**
     * 缓存Retrofit
     */
    private static HashMap<String,Retrofit> mCacheRetrofitMap = new HashMap<>();


    /**
     * 获取Retrofit 定义的interface
     * @param service  Retrofit 定义的interface
     * @param url 请求base地址
     * @param <T>
     * @return
     */
    public static <T>T getRetrofitService(Class<T> service, String url){
        Retrofit retrofit = getRetrofit(url);
        return retrofit.create(service);
    }


    private static Retrofit getRetrofit(String url){
        Retrofit retrofit = mCacheRetrofitMap.get(url);
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(HttpUtils.getOkHttpClient())
                    .baseUrl(url)
                    .addConverterFactory(StringAndGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            //缓存url相同的Retrofit
            mCacheRetrofitMap.put(url,retrofit);
        }
        return retrofit;
    }


}
