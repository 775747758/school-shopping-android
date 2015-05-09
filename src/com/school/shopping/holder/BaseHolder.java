package com.school.shopping.holder;

import android.app.Activity;
import android.view.View;

public abstract class BaseHolder<T> {

	protected View view;
	protected Activity activity;

	private T data;

	public BaseHolder() {
		view = initView();
		view.setTag(this);
	}
	
	//当有调用此构造方法，说明是从activity中调用
	public BaseHolder(View view,Activity activity) {
		this.view = view;
		this.activity=activity;
		initView();
		//view.setTag(this);
	}

	public void setData(T data) {
		this.data = data;
		refreshView(data);
	}

	public abstract void refreshView(T data);

	public T getData() {
		return data;
	}

	public View getRootView() {
		return view;
	}
	
	public Activity getCurrentActivity(){
		return activity;
	}

	public abstract View initView();

}
