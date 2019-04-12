package com.dj.frameworklib.utils;

import android.content.Context;

/**
 * Created by dengjun on 2019/1/25.
 */

public class DimenUtil {

    /**
     * 将像素转换为px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px转换为dp
     */

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
