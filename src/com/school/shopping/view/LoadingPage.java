package com.school.shopping.view;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.school.shopping.R;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public abstract class LoadingPage extends FrameLayout{
	
	private FrameLayout framLayout;
	private View loadingView;
	private View errorView;
	private View emptyView;
	private View successView;
	public static Handler mHandler;
	public static int STATE_UNKNOW = 0;
	public static int STATE_LOADING = 1;
	public static int STATE_ERROR = 2;
	public static int STATE_EMPTY = 3;
	public static int STATE_SUCCESS = 4;
	public static int state = STATE_UNKNOW;

	public LoadingPage(Context context) {
		super(context);
		init();
	}

	private void init() {

		this.setBackgroundColor(UIUtils.getColor(R.color.bg_page));
		loadingView = createLoadingView();
		if (loadingView != null) {
			this.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		errorView = createErrorView();
		if (errorView != null) {
			this.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		emptyView = createEmptyView();
		if (emptyView != null) {
			this.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		// 根据状态控制三个界面的显示和隐藏
		showPage();
	
	}
	
	/*
	 * 创建成功界面
	 */
	public abstract View createLoadedView();
	
	/*
	 * 创建空界面
	 */
	private View createEmptyView() {
		return UIUtils.inflate(R.layout.page_empty);
	}

	/*
	 * 创建错误界面
	 */
	private View createErrorView() {
		View view=UIUtils.inflate(R.layout.page_error);
		Button btn_error=(Button) view.findViewById(R.id.page_bt);
		btn_error.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				show();//重新加载
				
			}
		});
		return view;
	}

	/*
	 * 创建加载中界面
	 */
	private View createLoadingView() {
		return UIUtils.inflate(R.layout.page_loading);
	}
	
	/*
	 * 加载数据
	 */
	public abstract  LoadResult load();
	
	// 根据什么状态显示什么界面
		public void showPage() {
			Log.i("info", "当前状态showPage："+state);
			if (loadingView != null) {
				// 如果界面未知或者加载中的状态让加载中的界面可见
				loadingView.setVisibility(state == STATE_UNKNOW
						|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
			}
			if (errorView != null) {
				errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
						: View.INVISIBLE);
			}
			if (emptyView != null) {
				emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
						: View.INVISIBLE);
			}
			if (state == STATE_SUCCESS && successView == null) {
				successView = createLoadedView();
				this.addView(successView, new FrameLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			
			}

		}
		
		public  void show() {
			Log.i("info", "当前状态show"+state);
			if (state == STATE_ERROR || state == STATE_EMPTY) {
				state = STATE_UNKNOW;// 如果之前的状态是错误或者空，则重新置为默认
			}

			if (state == STATE_UNKNOW) {
				state = STATE_LOADING;// 修改状态
				
			}
			if(state==STATE_LOADING){
				Log.i("info", "显示loadingView");
				Log.i("info", "连接服务器");
				// 连接服务器
				//new Thread(new LoadingTask()).start();
				ThreadManager.getSinglePool().execute(new LoadingTask());
			}
			showPage();
		}
		
		
		
		public enum LoadResult{
			error(2),empty(3),success(4);
			
			int value;
			
			LoadResult(int value){
				this.value=value;
			}
			
			public int getValue(){
				return value;
			}
		}
		
		
		private class LoadingTask implements Runnable{

			@Override
			public void run() {
				//SystemClock.sleep(1000);//模拟服务器耗时操作
				LoadResult result=load();
				final int value=result.getValue();
				state=value;//修改当前程序的状态
				
				UIUtils.runInMainThread(new Runnable() {
					@Override
					public void run() {
						showPage();
					}
				});
			}
			
		}

}
