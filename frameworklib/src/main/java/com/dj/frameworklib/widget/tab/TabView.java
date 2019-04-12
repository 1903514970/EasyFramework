package com.dj.frameworklib.widget.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dj.frameworklib.R;


/**
 * Created by dengjun on 2019/1/24.
 */

public class TabView extends RelativeLayout {

    private TextView mBargeTv;

    private ImageView mIconIv;
    private TextView mNameTv;

    private int mSelectedResId;
    private int mNormalResId;
    private CharSequence mSelectedName;
    private CharSequence mNormalName;
    private int mSelectedNameColor;
    private int mNormalNameColor;

    private boolean isTabSelected;

    public TabView(Context context) {
        super(context);
        init(context);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.tab_item,this,true);
        setGravity(Gravity.CENTER_VERTICAL);
        mIconIv = findViewById(R.id.tab_icon);
        mNameTv = findViewById(R.id.tab_name);
        mBargeTv = findViewById(R.id.tab_barge);
    }

    public void setTabAttribute(int selectedIconRes,int normalIconRes,int selectedColor,int normalColor,CharSequence slectedName,
                                CharSequence normalName){
        this.mSelectedResId = selectedIconRes;
        this.mNormalResId = normalIconRes;
        this.mSelectedNameColor = selectedColor;
        this.mNormalNameColor = normalColor;
        this.mSelectedName = slectedName;
        this.mNormalName = normalName;
        setInitShow();
    }

    private void setInitShow(){
        mIconIv.setImageResource(mNormalResId);
        mNameTv.setText(mNormalName);
        mNameTv.setTextColor(mNormalNameColor);
    }

    void setTabSelected(boolean isSelected){
        if(this.isTabSelected == isSelected){
            return;
        }
        this.isTabSelected = isSelected;
        if(isSelected){
            mIconIv.setImageResource(mSelectedResId);
            mNameTv.setText(mSelectedName);
            mNameTv.setTextColor(mSelectedNameColor);
        }else{
            mIconIv.setImageResource(mNormalResId);
            mNameTv.setText(mNormalName);
            mNameTv.setTextColor(mNormalNameColor);
        }
    }

    public boolean isTabSelected(){
        return isTabSelected;
    }

    /**
     * 设置右上角未读数
     * @param count
     */
    public void setBargeCount(int count){
        if(count > 0){
            mBargeTv.setVisibility(View.VISIBLE);
            mBargeTv.setText(String.valueOf(count));
        }else{
            mBargeTv.setVisibility(View.GONE);
            mBargeTv.setText(String.valueOf(count));
        }
    }
}
