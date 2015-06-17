package com.school.shopping.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.handmark.pulltorefresh.library.internal.LoadingLayout;
import com.school.shopping.R;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.utils.ViewUtils;
import com.school.shopping.view.LoadingPage;
import com.school.shopping.view.LoadingPage.LoadResult;

public abstract class BaseFragment extends Fragment {
	public   LoadingPage loadingPage;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (loadingPage == null) {
			LogUtils.i("create loadingview");
			loadingPage = new LoadingPage(getActivity()) {
				
				@Override
				public LoadResult load() {
					return BaseFragment.this.load();
				}
				
				@Override
				public View createLoadedView() {
					return BaseFragment.this.createLoadedView();
				}
			};
		} else {
			// 将之前的父view移除
			ViewUtils.removeSelfFromParent(loadingPage);
		}
		show();
		return loadingPage;
	}
	
	public void show(){
		LogUtils.i("showwwwwww");
		if(loadingPage!=null){
			loadingPage.show();
			LogUtils.i("bu null");
		}else{
			LogUtils.i("null");
		}
	}
	
	/*
	 * 创建成功界面
	 */
	public abstract View createLoadedView();
	
	/*
	 * 加载数据
	 */
	public abstract  LoadResult load();
}
