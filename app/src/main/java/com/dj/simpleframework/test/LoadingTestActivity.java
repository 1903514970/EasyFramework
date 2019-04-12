package com.dj.simpleframework.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dj.frameworklib.widget.MyLoadingFrameLayout;
import com.dj.frameworklib.widget.dialog.LoadingDialog;
import com.dj.simpleframework.R;

/**
 * Created by dengjun on 2019/3/15.
 */

public class LoadingTestActivity extends Activity implements View.OnClickListener {

    private TextView mTv;
    private MyLoadingFrameLayout loadingFrameLayout;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_test);
        findViewById(R.id.show_loading).setOnClickListener(this);
        findViewById(R.id.show_empty).setOnClickListener(this);
        findViewById(R.id.show_error).setOnClickListener(this);
        findViewById(R.id.show_reload).setOnClickListener(this);
        findViewById(R.id.show_dialog).setOnClickListener(this);
        findViewById(R.id.hide).setOnClickListener(this);

        loadingFrameLayout = findViewById(R.id.result_loading);
        loadingFrameLayout.setOnReloadClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoadingTestActivity.this,"重新加载",Toast.LENGTH_SHORT).show();
            }
        });
        mTv = findViewById(R.id.result_tv);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.show_loading){
            loadingFrameLayout.showLoadingView();
        }else if(view.getId() == R.id.show_empty){
            loadingFrameLayout.showEmptyView(R.drawable.ic_library_empty,"暂无数据");
        }else if(view.getId() == R.id.show_error){
            loadingFrameLayout.showErrorView(R.drawable.ic_library_load_failed,"加载失败",false);
        }else if(view.getId() == R.id.show_reload){
            loadingFrameLayout.showErrorView(R.drawable.ic_library_load_failed,"加载失败",true);
        }else if(view.getId() == R.id.hide){
            loadingFrameLayout.hideLoadingLayout();
        }else if(view.getId() == R.id.show_dialog){
            showLoadingDialog();
        }
    }

    private LoadingDialog loadingDialog;
    private void showLoadingDialog(){
        if(loadingDialog == null){
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show("LOADING...");
    }
}
