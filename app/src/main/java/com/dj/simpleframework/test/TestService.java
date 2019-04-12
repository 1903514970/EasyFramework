package com.dj.simpleframework.test;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by dengjun on 2019/3/21.
 */

public interface TestService {

    /**
     * 获取今日干货
     * @return
     */
    @GET("api/today")
    public Observable<String> getTodayGank();

    /**
     * 获取闲读的主分类
     * @return
     */
    @GET("api/xiandu/categories")
    @Headers("TestService: i'm dj")
    public Observable<TestEntity> getCategories();
}
