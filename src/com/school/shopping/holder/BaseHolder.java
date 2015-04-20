package com.school.shopping.holder;

import android.view.View;

public abstract class BaseHolder<T> {

	protected View view;

	private T data;
	public BaseHolder() {
		view = initView();
		view.setTag(this);
	}

	public void setData(T data){
		this.data = data;
		refreshView(data);
	}
	
	public abstract void refreshView(T data);

	public T getData(){
		return data;
	}
	
	public View getRootView(){
		return view;
	}
	
	public abstract View initView();


	
}
