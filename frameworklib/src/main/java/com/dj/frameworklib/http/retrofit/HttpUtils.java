package com.dj.frameworklib.http.retrofit;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by dengjun on 2019/3/21.
 */

public class HttpUtils {

    public static OkHttpClient okHttpClient;

    /**
     * 在外部设置OkHttpClient，最好在Application入口处设置一次
     * @param client
     */
    public static void setOkHttpClient(OkHttpClient client){
        okHttpClient = client;
    }

    /**
     * 获取OkHttpClient
     * @return
     */
    static OkHttpClient getOkHttpClient(){
        if(okHttpClient == null){
            Log.i("HttpUtils","okHttpClient = null,will use default OkHttpClient");
            HttpLoggingInterceptor loggingInterceptor =
                    new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5,TimeUnit.SECONDS)
                    .readTimeout(10,TimeUnit.SECONDS)
                    .callTimeout(5,TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)//设置日志打印拦截器
                    .build();
        }
        return okHttpClient;
    }


    /**
     * 请求网络
     * @param observable
     * @param callback
     * @param <T>
     */
    public static <T> Disposable call(Observable<T> observable, ResponseCallback<T> callback){
        if(observable == null){
            if(callback != null){
                String errorMsg = "observable is null";
                callback.onFailure(ResponseCode.UNKONWN_ERROR,new NullPointerException(errorMsg),errorMsg);
            }
            return null;
        }

        DefaultObserver<T> defaultObserver =  new DefaultObserver<>(callback);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(defaultObserver);

        return defaultObserver.getDisposable();
    }

}
