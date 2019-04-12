package com.dj.simpleframework.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dj.frameworklib.http.retrofit.HttpUtils;
import com.dj.frameworklib.http.retrofit.ResponseCallback;
import com.dj.frameworklib.http.retrofit.RetrofitServiceCreator;
import com.dj.frameworklib.widget.dialog.LoadingDialog;
import com.dj.simpleframework.R;

import io.reactivex.disposables.Disposable;


/**
 * Created by dengjun on 2019/3/15.
 */

public class RetrofitTestActivity extends Activity {

    private TextView mTv;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_activity);
        findViewById(R.id.request_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTodayGank();
            }
        });
        findViewById(R.id.request_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategorys();
            }
        });

        mTv = findViewById(R.id.result_tv);
    }

    private void getTodayGank(){
        showLoadingDialog();
        HttpUtils.call(RetrofitServiceCreator.getRetrofitService(TestService.class, "http://gank.io/")
                .getTodayGank(), new ResponseCallback<String>() {
            @Override
            public void onSuccess(String s) {
                mTv.setText(s);
            }

            @Override
            public void onFailure(int code, Throwable e, String errorMsg) {
                super.onFailure(code, e, errorMsg);
                mTv.setText(errorMsg);
            }

            @Override
            public void onFinishRequest() {
                super.onFinishRequest();
                loadingDialog.dismiss();
            }
        });
    }

    private void getCategorys(){
        showLoadingDialog();
       Disposable disposable = HttpUtils.call(RetrofitServiceCreator.getRetrofitService(TestService.class, "http://gank.io/")
                .getCategories(), new ResponseCallback<TestEntity>() {
            @Override
            public void onSuccess(TestEntity categoryEntity) {
                mTv.setText(categoryEntity.toString());
            }

            @Override
            public void onFailure(int code, Throwable e, String errorMsg) {
                super.onFailure(code, e, errorMsg);
                mTv.setText(errorMsg);
            }

           @Override
           public void onFinishRequest() {
               super.onFinishRequest();
               loadingDialog.dismiss();
           }
       });

        Toast.makeText(this,"disposed is null == "+(disposable == null),Toast.LENGTH_SHORT).show();
    }

    private LoadingDialog loadingDialog;
    private void showLoadingDialog(){
        if(loadingDialog == null){
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show("LOADING...");
    }
}
