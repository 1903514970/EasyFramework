package com.dj.frameworklib.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public final class ThreadUtil {
    public static final String TAG = "ThreadUtil";
    
    private ThreadUtil(){}
    
    public static long getThreadId() {
        Thread t = Thread.currentThread();
        return t.getId();
    }

    public static String getThreadSignature() {
        Thread t = Thread.currentThread();
        long id = t.getId();
        String name = t.getName();
        long p = t.getPriority();
        String groupName = t.getThreadGroup().getName();
        return (name + ":(id)" + id + ":(priority)" + p + ":(group)" + groupName);
    }

    public static void logThreadSignature() {
        Log.d(TAG, getThreadSignature());
    }

    public static void sleepForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception ex) {
            //Do nothing
        }
    }
    
    public static void sleepForMilliseconds(int milliseconds) {
    	try {
    		Thread.sleep(milliseconds);
    	} catch (Exception ex) {
    	}
    }
    
    public static boolean isUiThread() {
    	return getThreadId() == 1;
    }

    public static void runOnUiThread(Runnable runnable, long delayMillis){
    	Looper mainlooper = Looper.getMainLooper();
    	Handler handler = new Handler( mainlooper );
    	handler.postDelayed( runnable, delayMillis );
    }

    public static void runOnUiThread( Runnable runnable ){
    	Looper mainlooper = Looper.getMainLooper();
    	Handler handler = new Handler( mainlooper );
    	handler.post( runnable );
    }
    
    public static void runOnNewThread( Runnable runnable ){
    	new Thread(runnable).start();
    }
}
