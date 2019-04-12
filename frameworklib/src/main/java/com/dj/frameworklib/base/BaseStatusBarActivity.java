package com.dj.frameworklib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dj.frameworklib.R;
import com.dj.frameworklib.utils.StatusBarUtil;

/**
 * Created by dengjun on 2019/1/28.
 *
 * 在使用该Activity时，记得在Fragment或Activity的根布局上添加
 *     android:fitsSystemWindows="true"
 */

public abstract class BaseStatusBarActivity extends BaseActivity {

    /**
     * 是否使用系统默认状态栏
     * @return
     */
    protected boolean useSystemStatusBar(){
        return false;
    }

    /**
     * 状态栏字体颜色是否是深色
     * @return
     */
    protected boolean isStatusBarTextDark(){
        return true;
    }

    /**
     * 状态栏颜色
     * @return
     */
    protected int getStatusBarColor(){
        return R.color.white;
    }

    /**
     * 在设置黑色字体失败后，是否使用黑色的状态栏
     * @return
     */
    protected boolean useBlackStatusBarIfTextIsWhite(){
        return true;
    }

    /**
     * 设置状态栏
     */
    private  void setStatusBar(){
        boolean isDarkText = isStatusBarTextDark();
        boolean result = StatusBarUtil.setLightStatusBar(this,isDarkText);

        if(result){
            StatusBarUtil.setStatusBarColor(this,getStatusBarColor());
        }else if(useBlackStatusBarIfTextIsWhite()){
            //如果设置黑色字体不成功,则设置状态栏颜色为白色，不然可能出现白色字体，白色状态栏的情况。
            StatusBarUtil.setStatusBarColor(this,R.color.black);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            if(!useSystemStatusBar()){
                setStatusBar();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);

    }
}
