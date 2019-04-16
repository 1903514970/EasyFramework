package com.dj.simpleframework.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dj.simpleframework.R;
import com.dj.simpleframework.test.pullToSwipe.PullToSwipeActivity;


/**
 * Created by dengjun on 2019/4/11.
 */

public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_test:
                startActivity(new Intent(this,LoadingTestActivity.class));
                break;
            case R.id.http_test:
                startActivity(new Intent(this,RetrofitTestActivity.class));
                break;
            case R.id.pull_demo:
                startActivity(new Intent(this,PullToSwipeActivity.class));
                break;
        }
    }
}
