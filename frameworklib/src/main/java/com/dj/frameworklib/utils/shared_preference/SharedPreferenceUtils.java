package com.dj.frameworklib.utils.shared_preference;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by dengjun on 2019/3/27.
 */

public class SharedPreferenceUtils {

    private static HashMap<String,WeakReference<ISharedPreference>> mCacheMap = new HashMap<>();

    /**
     * 获取共享参数文件，一个class对应一个文件。
     * @param context applicationContext
     * @param clazz extends ISharedPreferenceKeysEntity
     * @return
     */
    public static <T extends ISharedPreferenceKeysEntity> ISharedPreference<T> getSharedPreference(Context context, Class<T> clazz){
        if(clazz == null){
            throw new NullPointerException();
        }
        String className = clazz.getName();
        ISharedPreference sharedPreference = null;
        //首先从缓存中获取
        if(mCacheMap.containsKey(className)){
            sharedPreference  = mCacheMap.get(className).get();
        }
        //如果缓存中没有
        if(sharedPreference == null){
            sharedPreference = new SharedPreferenceImpl<>(context,clazz);
            //添加到缓存
            mCacheMap.put(className,new WeakReference<>(sharedPreference));
        }
        return sharedPreference;
    }
}
