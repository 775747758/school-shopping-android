package com.school.shopping.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.school.shopping.R;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.utils.ViewUtils;

public abstract class BaseFragment extends Fragment {
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (framLayout == null) {
			framLayout = new FrameLayout(getActivity());
			init();// 把四个界面添加到帧布局中，再根据状态控制该显示哪个界面
		} else {
			// 将之前的父view移除
			ViewUtils.removeSelfFromParent(framLayout);
		}
		
		initHandler();
		
		show();// 请求服务器，根据服务器返回的数据，修改界面

		// return view;
		return framLayout;
	}
	
	/*
	 * 把四个界面全部添加到帧布局中
	 */

	private void init() {
		Log.i("info", "执行init");
		framLayout.setBackgroundColor(UIUtils.getColor(R.color.bg_page));
		loadingView = createLoadingView();
		if (loadingView != null) {
			framLayout.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		errorView = createErrorView();
		if (errorView != null) {
			framLayout.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		emptyView = createEmptyView();
		if (emptyView != null) {
			framLayout.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		// 根据状态控制三个界面的显示和隐藏
		showPage();
	}

	

	

	// 根据什么状态显示什么界面
	public void showPage() {
		Log.i("info", "执行showPage");
		Log.i("info", "当前状态showPage："+state);
		if (loadingView != null) {
			// 如果界面未知或者加载中的状态让加载中的界面可见
			loadingView.setVisibility(state == STATE_UNKNOW
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
			Log.i("info", "显示loadingView");
		}
		if (errorView != null) {
			loadingView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
			Log.i("info", "显示errorView");
		}
		if (emptyView != null) {
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
			Log.i("info", "显示emptyView");
		}
		if (state == STATE_SUCCESS && successView == null) {
			successView = createLoadedView();
			framLayout.addView(successView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			Log.i("info", "显示successView");
		}

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
		return UIUtils.inflate(R.layout.page_error);
	}

	/*
	 * 创建加载中界面
	 */
	private View createLoadingView() {
		return UIUtils.inflate(R.layout.page_loading);
	}

	public  void show() {
		Log.i("info", "当前状态show："+state);
		if (state == STATE_ERROR || state == STATE_EMPTY) {
			state = STATE_UNKNOW;// 如果之前的状态是错误或者空，则重新置为默认
		}

		if (state == STATE_UNKNOW) {
			state = STATE_LOADING;// 修改状态
			
		}
		if(state==STATE_LOADING){
			Log.i("info", "连接服务器");
			// 连接服务器
			load();
		}
		showPage();
	}
	/*
	 * 加载数据
	 */
	public abstract  void load();
	
	/*
	 * 初始化handler：用于访问服务器回调
	 */
	
	public abstract  void initHandler();
}
