package com.dj.frameworklib.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dj.frameworklib.R;


/**
 * Created by dengjun on 2019/3/27.
 */

public class MyLoadingFrameLayout extends FrameLayout {

    private ViewGroup mParentLayout,mErrorLayout;
    private View mLoadView;
    private ImageView mErrorIv;
    private TextView mErrorMsgTv,mErrorReloadBtn;
    private OnClickListener reloadOnClickListener;

    public MyLoadingFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MyLoadingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLoadingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initLoadingView(){
        if(mParentLayout == null){
            mParentLayout = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.loading_framelayout_loading_view,null);
            mLoadView = mParentLayout.findViewById(R.id.loading_view);
            mErrorLayout = mParentLayout.findViewById(R.id.error_layout);
            mErrorMsgTv = mParentLayout.findViewById(R.id.error_msg_tv);
            mErrorIv = mParentLayout.findViewById(R.id.error_iv);
            mErrorReloadBtn = mParentLayout.findViewById(R.id.error_reload_btn);

            if(reloadOnClickListener != null){
                mErrorReloadBtn.setOnClickListener(reloadOnClickListener);
            }

            addView(mParentLayout);
        }

        if(mParentLayout.getVisibility() != View.VISIBLE){
            mParentLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示加载中动画
     */
    public void showLoadingView(){
        initLoadingView();
        mLoadView.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
    }

    /**
     * 隐藏加载中和空数据布局
     */
    public void hideLoadingLayout(){
        if(mParentLayout != null && mParentLayout.getVisibility() != View.GONE){
            mParentLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 显示空数据布局
     * @param emptyResId 图片资源id
     * @param emptyString 空数据提示语
     */
    public void showEmptyView(@DrawableRes int emptyResId,CharSequence emptyString){
        initLoadingView();
        mLoadView.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);

        mErrorIv.setVisibility(View.VISIBLE);
        mErrorMsgTv.setVisibility(View.VISIBLE);
        mErrorReloadBtn.setVisibility(View.GONE);

        mErrorIv.setImageResource(emptyResId);
        mErrorMsgTv.setText(emptyString == null ? "" : emptyString);
    }

    /**
     * 显示空数据布局
     * @param emptyString 空数据提示语
     */
    public void showDefaultEmptyView(CharSequence emptyString){
        showEmptyView(R.drawable.ic_library_empty,emptyString);
    }

    /**
     * 显示加载错误布局
     * @param errorResId 图片资源id
     * @param errorString 加载错误时提示语
     * @param showReloadBtn true:显示重新加载按钮,false:不显示重新加载按钮
     */
    public void showErrorView(@DrawableRes int errorResId,CharSequence errorString,boolean showReloadBtn){
        initLoadingView();
        mLoadView.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);

        mErrorIv.setVisibility(View.VISIBLE);
        mErrorMsgTv.setVisibility(View.VISIBLE);
        mErrorReloadBtn.setVisibility(showReloadBtn ? View.VISIBLE:View.GONE);

        mErrorIv.setImageResource(errorResId);
        mErrorMsgTv.setText(errorString == null ? "" : errorString);
    }

    /**
     * 显示加载错误布局
     * @param errorString 加载错误时提示语
     * @param showReloadBtn true:显示重新加载按钮,false:不显示重新加载按钮
     */
    public void showDefaultErrorView(CharSequence errorString,boolean showReloadBtn){
        showErrorView(R.drawable.ic_library_load_failed,errorString,showReloadBtn);
    }

    /**
     * 设置重新加载按钮点击事件监听
     * @param onClickListener
     */
    public void setOnReloadClickListener(OnClickListener onClickListener){
        reloadOnClickListener = onClickListener;
        if(mErrorReloadBtn != null){
            mErrorReloadBtn.setOnClickListener(onClickListener);
        }
    }

}
