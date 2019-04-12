package com.dj.frameworklib.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 获取屏幕锁工具类，防止屏幕自动锁定
 * 乐同步全局只需要一个锁
 * @author Gavin
 */
public final class WakeLockUtil {
	private static final int DELAY_RELEASE_LOCK_TIME = 2000;
	private final static ReentrantLock reentrantLock = new ReentrantLock();
    private static WakeLock wakeLock = null;
    private static AtomicInteger counter = new AtomicInteger(0);
    
    private WakeLockUtil(){}
    
    public static void acquireWakeLock(Context context){
    	try{
    		reentrantLock.lock();
    		
	    	if(wakeLock == null){
		    	PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
		    	wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, context.getClass().getCanonicalName());
		    	wakeLock.acquire();
	    	}
	    	counter.incrementAndGet();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		reentrantLock.unlock();
    	}
    }
    
    public static void releaseWakeLock(){
	    try{
	    	reentrantLock.lock();

	    	if(counter.get() > 0 && counter.decrementAndGet() == 0){
		    	delayReleaseWakeLock();
	    	}
	    }catch(Exception e){
			e.printStackTrace();
	    }finally{
    		reentrantLock.unlock();
    	}
    }

	private static void delayReleaseWakeLock() {
		ThreadUtil.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				try{
					if(wakeLock != null && wakeLock.isHeld()){
						wakeLock.release();
						wakeLock = null;
					}
				}catch(Exception e){
					e.printStackTrace();
			    }
			}
		}, DELAY_RELEASE_LOCK_TIME);
	}
}
