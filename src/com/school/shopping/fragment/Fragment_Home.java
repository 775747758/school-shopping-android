package com.school.shopping.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_Goods;
import com.school.shopping.entity.Good;
import com.school.shopping.entity.User;
import com.school.shopping.net.HomeProtocal;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.net.UploadUtil;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.LoadingPage.LoadResult;

public class Fragment_Home extends BaseFragment {
	
	private static PullToRefreshListView allGoods_lv;
	private static List<Good> goodsData = new ArrayList<Good>();
	private static Adapter_Goods adapter=null;
	private int allGoodCount=0;//数据库中所有的商品的数量
	private int startIndex=0;

	/*
	 *  加载成功的界面
	 */
	@Override
	public View createLoadedView() {
		View view=UIUtils.inflate(R.layout.activity_showgoods);
		final User user=Config.getCachedUser(UIUtils.getContext());
		allGoods_lv=(PullToRefreshListView)view.findViewById(R.id.allGoods_lv);
		allGoods_lv.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					int lastPosition=view.getLastVisiblePosition();
					if(adapter!=null&&lastPosition==adapter.getCount()-1){
						startIndex+=20;
						UploadUtil.getGoods(UIUtils.getContext(),startIndex,startIndex+20);
					}
					break;
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		adapter=new Adapter_Goods(goodsData);
		allGoods_lv.setAdapter(adapter);
		return view;
	}
	/*
	 *  请求服务器获取数据
	 */
	@Override
	public LoadResult load() {
		HomeProtocal protocal=new HomeProtocal();
		goodsData=protocal.load(0, 20,true);
		return LoadResult.success;
	}
}
