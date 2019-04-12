package com.dj.frameworklib.base;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dengjun on 2019/1/25.
 */

public class BaseFragment extends Fragment {

    protected View mCacheView;
    private boolean isUseCache = false;


    public View getCacheView(LayoutInflater inflater, int layoutId) {
        if (mCacheView == null) {
            mCacheView = inflater.inflate(layoutId, null);
            isUseCache = false;
        } else {
            isUseCache = true;
        }
        ViewGroup parent = (ViewGroup) (mCacheView != null ? mCacheView.getParent() : null);
        if (parent != null) {
            parent.removeView(mCacheView);
        }
        return mCacheView;
    }
}
