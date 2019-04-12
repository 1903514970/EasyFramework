package com.dj.frameworklib.widget.tab;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by dengjun on 2019/1/24.
 */

public class TabLayout extends LinearLayout implements View.OnClickListener {

    private TabView mSelectedTabView;

    private OnTabItemSelectedListener onTabItemSelectedListener;

    /**
     * 选择监听
     */
    public interface OnTabItemSelectedListener{
        void onTabSelected(int index);
    }

    public TabLayout(Context context) {
        super(context);
        init();
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(HORIZONTAL);
    }

    /**
     * 添加Tab
     * @param tab
     */
    public void addTab(TabView tab){
        if(tab == null){
            return;
        }
        addView(tab);
        LayoutParams params = new LayoutParams(0,LayoutParams.MATCH_PARENT,1);
        tab.setLayoutParams(params);
        tab.setOnClickListener(this);
    }

    /**
     * 设置默认选中
     * @param index
     */
    public void setDefaultSelected(int index){
        try {
            setDefaultSelected((TabView) getChildAt(index));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置默认选中
     * @param tab
     */
    public void setDefaultSelected(TabView tab){
        if(tab == null){
            return;
        }
        tab.performClick();
    }

    @Override
    public void onClick(View v) {
        if(v instanceof TabView){
            if(v == mSelectedTabView){
                return;
            }
            if(mSelectedTabView != null){
                mSelectedTabView.setTabSelected(false);
            }
            mSelectedTabView = (TabView) v;
            mSelectedTabView.setTabSelected(true);
            if(onTabItemSelectedListener != null){
                onTabItemSelectedListener.onTabSelected(indexOfChild(v));
            }
        }
    }

    /**
     * 设置item选择监听
     * @param listener
     */
    public void setOnTabItemSelectedListener(OnTabItemSelectedListener listener){
        this.onTabItemSelectedListener = listener;
    }

}
