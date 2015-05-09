package com.school.shopping.adapter;


import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.school.shopping.holder.BaseHolder;
import com.school.shopping.holder.MoreHolder;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public abstract class DefaultAdapter<T> extends BaseAdapter {
	
	List<T> data;
	public static final int TYPE_MORE=0;
	public static final int TYPE_ITEM=1;
	private MoreHolder moreHolder;
	private boolean is_load;
	
	PullToRefreshListView listview=null;
	
	
	public PullToRefreshListView getListview() {
		return listview;
	}
	public void setListview(PullToRefreshListView listview) {
		this.listview = listview;
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemClick(position);
			}

			
		});
	}
	
	protected abstract  void itemClick(int position);
	
	public DefaultAdapter(List<T> data) {
		setData(data);
	}
	public void setData(List<T> data) {
		this.data = data;
		notifyDataSetChanged();
	}
	public List<T> getData(){
		return data;
	}

	@Override
	public int getCount() {
		// 加一是因为最后一项为加载中item
		return data.size()+1;
	}

	@Override
	public Object getItem(int position) {
		if(position<data.size()){
			return data.get(position);
		}
		else{
			//最后一项没有数据
			return position;
		}
		
	}
	
	
	//根据位置判断类型
	@Override
	public int getItemViewType(int position) {
		//最后一项返回加载更多
		if(position==data.size()){
			return TYPE_MORE;
		}else{
			return getInnerItemViewType(position);
		}
	}
	
	private int getInnerItemViewType(int position) {
		return TYPE_ITEM;
	}
	//返回listview有多少不同的item ；默认值为1
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount()+1;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		BaseHolder holder=null;
		if (convertView == null) {
			if(getItemViewType(position)==TYPE_MORE){
				LogUtils.i("加载到最后一项");
				holder=getMoreHolder();
			}else{
				holder = getHolder();
			}
			
			//view=holder.initView();
		} else if(convertView != null&&convertView.getTag() instanceof BaseHolder){
			//view=convertView;
			holder = (BaseHolder) convertView.getTag();
		}
		if(getItemViewType(position)==TYPE_MORE){
			holder.setData(MoreHolder.HAS_MORE);
		}else{
			holder.setData(data.get(position));
		}
		return holder.getRootView();
	}
	
	private BaseHolder getMoreHolder() {
		if(moreHolder == null){
			moreHolder = new MoreHolder(this);
		}
		return moreHolder;
	}
	public abstract BaseHolder getHolder();
	
	//当显示view的时候调调用
	public void loadMore() {
		
		if(!is_load){
			LogUtils.i("进入加载数据线程");
			is_load = true;
		ThreadManager.getLongPool().execute(new Runnable() {
			@Override
			public void run() {
				final List<T> newDatas=onLoadMore();
				UIUtils.runInMainThread(new Runnable() {
					@Override
					public void run() {
						if(newDatas == null){
							LogUtils.i("新数据为空");
							getMoreHolder().setData(MoreHolder.ERROR);
						}else if(newDatas.size()<20){
							LogUtils.i("没有更多数据");
							getMoreHolder().setData(MoreHolder.NO_MORE);
						}else{
							LogUtils.i("有更多数据");
							getMoreHolder().setData(MoreHolder.HAS_MORE);
						}
						
						if(newDatas!=null){
							LogUtils.i("新数据不为空");
							if(data!=null){
								LogUtils.i("旧数据不为空，将新数据添加到旧数据中");
								data.addAll(newDatas);
							}
							else{
								LogUtils.i("旧数据空，将新数据添加到旧数据中");
								data=newDatas;
							}
							//更新界面
							notifyDataSetChanged();
							if(listview!=null){
								listview.scrollTo(0,0);
							}
							
							is_load = false;
						}
					
					}
				});
				
				
			}
		});
		}
		else{
			LogUtils.i("没有进入加载数据线程");
			getMoreHolder().setData(MoreHolder.NO_MORE);
			is_load = false;
		}
		
	}
	
	protected abstract List onLoadMore();

}
