package com.school.shopping.utils;

import org.apache.commons.logging.Log;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.school.shopping.MyApplication;

public class UIUtils {
	
	
	/* 
	 * 获取Context
	 */
	public static Context getContext() {
		return MyApplication.getApplication();
	}
	
	public static int getMainThreadId() {
		return MyApplication.getMainThreadId();
	}
	
	
	public static void showMsg(String text){
		Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
	}
	
	public static int getWIndowWidth(Activity  activity){
		WindowManager manager = activity.getWindowManager();
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		return width;
	}

	public static int getWindowHeight(Activity  activity){
		WindowManager manager = activity.getWindowManager();
		Display display = manager.getDefaultDisplay();
		int height = display.getHeight();
		return height;
	}
	
	/** 获取资源 */
	public static Resources getResources() {
		
		return getContext().getResources();
	}
	
	/** 获取文字 */
	public static String getString(int resId) {
		return getResources().getString(resId);
	}
	

	/** 获取颜色 */
	public static int getColor(int resId) {
		return getResources().getColor(resId);
	}
	
	/** 获取drawable */
	public static Drawable getDrawable(int resId) {
		return getResources().getDrawable(resId);
	}
	
	
	public static View inflate(int resId){
		return LayoutInflater.from(getContext()).inflate(resId,null);
	}
	
	//判断当前的线程是不是在主线程 
		public static boolean isRunInMainThread() {
			return android.os.Process.myTid() == getMainThreadId();
		}
	    
		public static void runInMainThread(Runnable runnable) {
			if (isRunInMainThread()) {
				runnable.run();
			} else {
				if(getHandler()==null){
					LogUtils.i("kong");
				}
				getHandler().post(runnable);
			}
		}

		
		/** 获取主线程的handler */
		public static Handler getHandler() {
			return MyApplication.getMainThreadHandler();
		}
}
