package com.dj.frameworklib.http.retrofit;

/**
 * Created by dengjun on 2019/3/21.
 */

public abstract class ResponseCallback<T> {

    /**
     * 成功回调
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 失败的回调
     * @param code
     * @param e
     * @param errorMsg
     */
    public void onFailure(int code, Throwable e, String errorMsg){}

    /**
     * 结束请求，成功或者失败都会走这个回调，可用于取消显示Dialog或
     * 在onSuccess和onFailure中都会走的逻辑代码
     */
    public void onFinishRequest(){}

}
