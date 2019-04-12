package com.dj.frameworklib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dj.frameworklib.http.retrofit.HttpUtils;
import com.dj.frameworklib.http.retrofit.ResponseCallback;
import com.dj.frameworklib.widget.dialog.LoadingDialog;

import io.reactivex.Observable;

/**
 * Created by dengjun on 2019/1/24.
 */

public abstract class BaseActivity extends FragmentActivity {


    protected abstract int getContentLayoutId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getContentLayoutId();
        if(layoutId > 0){
            setContentView(layoutId);
        }
    }

    /**
     * 请求网络
     * @param observable
     * @param callback
     * @param <T>
     */
    public <T>void call(Observable<T> observable, ResponseCallback<T> callback){
        HttpUtils.call(observable,callback);
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard()
    {
        hideKeyboard(InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘，使用hideSoftInputFromWindow中的flags
     *
     * @param flags
     *         InputMethodManager.hideSoftInputFromWindow（IBinder windowToken, int flags）中的flags
     */
    public void hideKeyboard(int flags)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View peekDecorView = getWindow().peekDecorView();
        inputMethodManager.hideSoftInputFromWindow(peekDecorView.getWindowToken(), flags);
    }

    /**
     * 弹出toast
     * @param content
     */
    public void showToast(CharSequence content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }


    private LoadingDialog loadingDialog;

    /**
     * 显示加载dialog
     * @param text 提示内容
     */
    public void showLoadingDialog(String text){
        if(loadingDialog == null){
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show(text == null ? "" : text);
    }

    /**
     * 取消显示加载dialog
     */
    public void dismissLoadingDialog(){
        if(loadingDialog != null){
            loadingDialog.dismiss();
        }
    }

    public void startActivity(Class activityClass){
        if(activityClass == null){
            return;
        }
        Intent intent = new Intent(this,activityClass);
        startActivity(intent);
    }

    public void startActivityForResult(Class activityClass,int requestCode){
        if(activityClass == null){
            return;
        }
        Intent intent = new Intent(this,activityClass);
        startActivityForResult(intent,requestCode);
    }
}
