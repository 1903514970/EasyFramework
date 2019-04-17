package com.dj.frameworklib.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.dj.frameworklib.R;

/**
 * Created by dengjun on 2019/4/17.
 */

public class DefaultSwipeToLoadLayout extends SwipeToLoadLayout {
    private static String TAG = "DefaultSwipeToLoadLayout";
    private DefaultRefreshHeaderView mRefreshHeaderView;
    private DefaultLoadMoreFooterView mLoadMoreFooterView;

    public DefaultSwipeToLoadLayout(Context context) {
        super(context);
        setDefaultLayout(context);
    }

    public DefaultSwipeToLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultLayout(context);
    }

    public DefaultSwipeToLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDefaultLayout(context);
    }

    private void setDefaultLayout(Context context){
        LayoutInflater.from(context).inflate(R.layout.default_swipe_to_load_layout,this,true);
    }

    @Override
    protected void onFinishInflate() {
        int childNum = this.getChildCount();
        if(childNum > 3){
            throw new IllegalStateException("DefaultSwipeToLoadLayout only can has one direct child");
        }

        View targetView = findViewById(R.id.swipe_target);
        if(targetView == null){
            throw new IllegalStateException("child's id must be 'swipe_target'");
        }
        this.mRefreshHeaderView = this.findViewById(com.aspsine.swipetoloadlayout.R.id.swipe_refresh_header);
        this.mLoadMoreFooterView = this.findViewById(com.aspsine.swipetoloadlayout.R.id.swipe_load_more_footer);
        super.onFinishInflate();
    }

    /**
     * 获取顶部刷新View
     * @return
     */
    public DefaultRefreshHeaderView getRefreshHeaderView(){
        return mRefreshHeaderView;
    }


    /**
     * 获取底部加载View
     * @return
     */
    public DefaultLoadMoreFooterView getLoadMoreFooterView(){
        return mLoadMoreFooterView;
    }


    /**
     * 该方法无效
     * @param view
     */
    @Override
    public void setLoadMoreFooterView(View view) {
        Log.i(TAG,"DefaultSwipeToLoadLayout not support setLoadMoreFooterView method");
    }

    /**
     * 该方法无效
     * @param view
     */
    @Override
    public void setRefreshHeaderView(View view) {
        Log.i(TAG,"DefaultSwipeToLoadLayout not support setRefreshHeaderView method");
    }
}
