package com.school.shopping.holder;
import com.school.shopping.R;
import com.school.shopping.adapter.DefaultAdapter;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;


public class MoreHolder extends BaseHolder<Integer> {

	// 表示有更多的数据类型
	public static final int HAS_MORE = 0;
	// 表示没有更多的数据类型
	public static final int NO_MORE = 1;
	// 加载失败
	public static final int ERROR = 2;
	private RelativeLayout rl_more_error;
	private RelativeLayout rl_more_loading;
	
	private DefaultAdapter adapter;
	
	public MoreHolder(DefaultAdapter adapter) {
		super();
		this.adapter = adapter;
	}

	@Override
	public void refreshView(Integer data) {
		rl_more_loading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);
		rl_more_error.setVisibility(data == ERROR ? View.VISIBLE : View.GONE);
	}

	@Override
	public View initView() {
		view = UIUtils.inflate(R.layout.list_more_loading);
		rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
		rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
		return view;
	}
	
	
	@Override
	public View getRootView() {
		if(getData() == HAS_MORE){
			LogUtils.i("加载更多数据");
			loadMore();
		}
		return super.getRootView();
	}

	private void loadMore() {
		if(adapter != null){
			adapter.loadMore();
		}
		
	}

}
