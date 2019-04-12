package com.dj.frameworklib.http;

import android.text.TextUtils;
import android.widget.Toast;

import com.dj.frameworklib.app.MainApplication;
import com.dj.frameworklib.http.retrofit.ResponseCallback;
import com.dj.frameworklib.http.retrofit.ResponseCode;

/**
 * Created by dengjun on 2019/3/25.
 *
 *  发生异常时，弹出Toast
 */

public abstract class ErrorToastResponseCallback<T> extends ResponseCallback<T> {

    @Override
    public void onFailure(int code, Throwable e, String errorMsg) {
        super.onFailure(code, e, errorMsg);
        //如果是服务器返回的错误信息，直接提示
        if(code == ResponseCode.WRONG_DATA){
            if(!TextUtils.isEmpty(errorMsg)){
                Toast.makeText(MainApplication.getInstance(),errorMsg,Toast.LENGTH_SHORT).show();
            }
        }else{//其他情况为请求错误
            if(!TextUtils.isEmpty(errorMsg)){
                Toast.makeText(MainApplication.getInstance(),String.format("请求错误：请重试! 原因：%s",errorMsg),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
