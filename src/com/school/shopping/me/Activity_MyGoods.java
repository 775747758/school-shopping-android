package com.school.shopping.me;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_MyGoods;
import com.school.shopping.entity.Good;
import com.school.shopping.net.MyGoodProtocal;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.utils.ViewUtils;
import com.school.shopping.view.LoadingPage;
import com.school.shopping.view.LoadingPage.LoadResult;

public class Activity_MyGoods extends BaseActivity implements OnClickListener{
	
	private PullToRefreshListView myGoodsLV;
	List<Good> goodsData = new ArrayList<Good>();
	private MyApplication myApp;
	private LoadingPage mContentView;
	private Adapter_MyGoods adapter;
	private MyGoodProtocal myGoodProtocal;
	private TextView tv_null;
	private ImageView sv_menu_right;
	private boolean isMyGoods;
	private int otherId;
	private String otherUname;
	private ImageView iv_back;
	
	@Override
	protected void init() {
		Intent intent=getIntent();
		if(intent!=null){
			isMyGoods=intent.getBooleanExtra("isMyGoods", false);
			otherId=intent.getIntExtra("otherId",-1);
			otherUname=intent.getStringExtra("otherUname");
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(myGoodsLV!=null){
			myGoodsLV.setRefreshing();
		}
	}
	
	
	@Override
	protected void initView() {
		if(mContentView == null){
			mContentView = new LoadingPage(UIUtils.getContext()){

				@Override
				public View createLoadedView() {
					return Activity_MyGoods.this.createLoadedView();
				}

				@Override
				public LoadResult load() {
					LoadResult loadResult=Activity_MyGoods.this.load();
					return loadResult;
				}
			};
		}else{
			ViewUtils.removeSelfFromParent(mContentView);
		}
		
		setContentView(mContentView);
		mContentView.show();
	}

	

	protected LoadResult load() {

		myGoodProtocal=MyGoodProtocal.getInstance(isMyGoods==true?Config.getUID():otherId);
		myGoodProtocal.setUid(isMyGoods==true?Config.getUID():otherId);
		if(myGoodsLV!=null){
			if(myGoodsLV.isRefreshing()){
				goodsData=myGoodProtocal.load(0,19,false);
			}else{
				goodsData=myGoodProtocal.load(0,19,true);
			}
		}
		
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				if(myGoodProtocal.isNetError()){
					UIUtils.showMsg("您没有连接网络，请查看网络！");
				}else{
					if( Activity_MyGoods.this.adapter!=null){
						LogUtils.i("refresh");
						
						if(goodsData==null||goodsData.size()==0){
							if(tv_null!=null){
								tv_null.setVisibility(View.VISIBLE);
							}
							
						}else{
							adapter.setData(goodsData);
						}
						
					}
					if(myGoodsLV!=null){
						if(myGoodsLV.isRefreshing()){
							myGoodsLV.onRefreshComplete();
						}
					}
				}
				
				
			}
		});
		return LoadResult.success;
	}

	protected View createLoadedView() {
		LogUtils.i("FGFFGFGFFGFGFGFGFGFGFGF");
		View view = UIUtils.inflate(R.layout.activity_mygoods);
		sv_menu_right=(ImageView)view.findViewById(R.id.sv_menu_right);
		if(!isMyGoods){
			sv_menu_right.setVisibility(View.GONE);
		}else{
			sv_menu_right.setOnClickListener(this);
		}
		tv_null=(TextView)view.findViewById(R.id.tv_null);
		
		if(goodsData==null||goodsData.size()==0){
			tv_null.setVisibility(View.VISIBLE);
		}
		myGoodsLV=(PullToRefreshListView)view.findViewById(R.id.myGoods_lv);
		
		View emptyView=UIUtils.inflate(R.layout.page_empty);
		emptyView.setVisibility(View.GONE);
		((ViewGroup)myGoodsLV.getParent()).addView(emptyView);  
		myGoodsLV.setEmptyView(view.findViewById(R.id.tv_null));
		initRefresh();
		iv_back=(ImageView)view.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		adapter=new Adapter_MyGoods(goodsData);
		adapter.setProtocal(myGoodProtocal);
		adapter.setListview(myGoodsLV);
		adapter.setMyGoods(isMyGoods);
		adapter.setOtherUname(otherUname);
		myGoodsLV.setAdapter(adapter);
		return view;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.sv_menu_right:
			Intent intent=new Intent(BaseActivity.getRunActivity(),Activity_AddGood.class);
			UIUtils.startActivity(intent);
			break;
		}
	}
	
	
	public void initRefresh() {
		//当快速滑动时，不加载图片
		myGoodsLV.setOnScrollListener(new PauseOnScrollListener(MyApplication.getImageLoader(), false, true));    
		myGoodsLV.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				mContentView.show();
			}
		});
		ILoadingLayout startLabels = myGoodsLV.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在载入...");// 刷新时
		startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
	}

}
