package com.dj.frameworklib.http.retrofit;



import com.dj.frameworklib.http.retrofit.entity.BaseResultEntity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by dengjun on 2019/3/21.
 */

public class DefaultObserver<T> implements Observer<T> {

    private ResponseCallback<T> responseCallback;

    private Disposable disposable;

    public DefaultObserver(ResponseCallback<T> responseCallback) {
        this.responseCallback = responseCallback;
    }

    public Disposable getDisposable(){
        return disposable;
    }


    @Override
    public void onError(Throwable e) {
        if(responseCallback != null){
            responseCallback.onFailure(ResponseCode.UNKONWN_ERROR,e,e.getMessage());
            responseCallback.onFinishRequest();
        }
    }

    @Override
    public void onComplete() {
        if(responseCallback != null){
            responseCallback.onFinishRequest();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(T t) {
        if(responseCallback != null){
            if(t instanceof BaseResultEntity){
                BaseResultEntity resultEntity = (BaseResultEntity) t;
                if(resultEntity.isSuccess()){
                    responseCallback.onSuccess(t);
                }else{
                    String errorMsg;
                    try {
                        errorMsg = resultEntity.getResponseMessage().getMessage().getZh_CN();
                    }catch (Exception e){
                        errorMsg = "数据返回格式错误";
                    }
                    responseCallback.onFailure(ResponseCode.WRONG_DATA,new RuntimeException(errorMsg),errorMsg);
                }
            }else{
                responseCallback.onSuccess(t);
            }
        }
    }


}
