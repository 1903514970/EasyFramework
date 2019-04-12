package com.dj.frameworklib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dj.frameworklib.R;


public class SimpleTitleBar extends RelativeLayout implements View.OnClickListener {

    private TextView mLeftTv,mTitleTv,mRightTv;
    private OnTitleBarClickListener mTitleBarClickListener;

    public SimpleTitleBar(Context context) {
        super(context);
        init(context,null);
    }

    public SimpleTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SimpleTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.simple_title_bar_layout,this,true);
        mLeftTv = findViewById(R.id.simple_title_bar_back_tv);
        mTitleTv = findViewById(R.id.simple_title_bar_title_tv);
        mRightTv = findViewById(R.id.simple_title_bar_right_tv);
        mLeftTv.setOnClickListener(this);
        mTitleTv.setOnClickListener(this);
        mRightTv.setOnClickListener(this);

        if(attrs != null){
            TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.SimpleTitleBar);

            boolean showLeft = array.getBoolean(R.styleable.SimpleTitleBar_showLeft,true);
            int leftResource = array.getResourceId(R.styleable.SimpleTitleBar_leftResource,R.drawable.ic_common_back);
            String leftText = array.getString(R.styleable.SimpleTitleBar_leftText);
            int leftTextColor = array.getColor(R.styleable.SimpleTitleBar_leftTextColor,getResources().getColor(R.color.white));

            boolean showRight = array.getBoolean(R.styleable.SimpleTitleBar_showRight,false);
            int rightResource = array.getResourceId(R.styleable.SimpleTitleBar_rightResource,-1);
            String rightText = array.getString(R.styleable.SimpleTitleBar_rightText);
            int rightTextColor = array.getColor(R.styleable.SimpleTitleBar_rightTextColor,getResources().getColor(R.color.white));

            boolean showTitle = array.getBoolean(R.styleable.SimpleTitleBar_showTitle,true);
            String titleText = array.getString(R.styleable.SimpleTitleBar_title);
            int titleTextColor = array.getColor(R.styleable.SimpleTitleBar_titleColor,getResources().getColor(R.color.white));

            array.recycle();

            //设置返回按钮
            if(showLeft){
                mLeftTv.setVisibility(View.VISIBLE);
                if(leftResource > 0){
                    mLeftTv.setCompoundDrawablesWithIntrinsicBounds(leftResource,0,0,0);
                }

                mLeftTv.setText(TextUtils.isEmpty(leftText) ? "" : leftText);
                if(leftTextColor > 0){
                    mLeftTv.setTextColor(leftTextColor);
                }
            }else{
                mLeftTv.setVisibility(View.GONE);
            }

            //设置右边按钮
            if(showRight){
                mRightTv.setVisibility(View.VISIBLE);
                if(rightResource > 0){
                    mRightTv.setCompoundDrawablesWithIntrinsicBounds(rightResource,0,0,0);
                }

                mRightTv.setText(TextUtils.isEmpty(rightText) ? "" : rightText);
                if(rightTextColor > 0){
                    mRightTv.setTextColor(rightTextColor);
                }
            }else{
                mRightTv.setVisibility(View.INVISIBLE);
            }

            //设置标题
            if(showTitle){
                mTitleTv.setVisibility(View.VISIBLE);

                mTitleTv.setText(TextUtils.isEmpty(titleText) ? "" : titleText);
                if(titleTextColor > 0){
                    mTitleTv.setTextColor(titleTextColor);
                }
            }else{
                mTitleTv.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置左边按钮点击事件
     * @param clickListener
     */
    public void setOnLeftClickListener(OnClickListener clickListener){
        if(mLeftTv != null){
            mLeftTv.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置右边按钮点击事件
     * @param clickListener
     */
    public void setOnRightClickListener(OnClickListener clickListener){
        if(mRightTv != null){
            mRightTv.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置标题点击事件
     * @param clickListener
     */
    public void setOnTitleClickListener(OnClickListener clickListener){
        if(mTitleTv != null){
            mTitleTv.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置左边文字
     * @param charSequence
     */
    public void setLeftText(CharSequence charSequence){
        if(charSequence != null && mLeftTv != null){
            mLeftTv.setText(charSequence);
        }
    }
    /**
     * 设置右边文字
     * @param charSequence
     */
    public void setRightText(CharSequence charSequence){
        if(charSequence != null && mRightTv != null){
            mRightTv.setText(charSequence);
        }
    }
    /**
     * 设置标题文字
     * @param charSequence
     */
    public void setTitleText(CharSequence charSequence){
        if(charSequence != null && mTitleTv != null){
            mTitleTv.setText(charSequence);
        }
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener listener){
        mTitleBarClickListener = listener;
    }

    public TextView getLeftTv(){
        return  mLeftTv;
    }

    public TextView getTitleTv(){
        return  mTitleTv;
    }

    public TextView getRightTv(){
        return mRightTv;
    }

    @Override
    public void onClick(View v) {
        if(mTitleBarClickListener == null){
            return;
        }
        int i = v.getId();
        if (i == R.id.simple_title_bar_back_tv) {
            mTitleBarClickListener.onLeftClick(v);

        } else if (i == R.id.simple_title_bar_title_tv) {
            mTitleBarClickListener.onTitleClick(v);

        } else if (i == R.id.simple_title_bar_right_tv) {
            mTitleBarClickListener.onRightClick(v);

        }
    }

    public static abstract class OnTitleBarClickListener{
        public void onLeftClick(View view){};
        public void onTitleClick(View view){};
        public void onRightClick(View view){};
    }
}
