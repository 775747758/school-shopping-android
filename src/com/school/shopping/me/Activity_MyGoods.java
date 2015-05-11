package com.school.shopping.me;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.school.shopping.BaseActivity;
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

public class Activity_MyGoods extends BaseActivity{
	
	private PullToRefreshListView myGoodsLV;
	List<Good> goodsData = new ArrayList<Good>();
	private MyApplication myApp;
	private LoadingPage mContentView;
	private Adapter_MyGoods adapter;
	private MyGoodProtocal myGoodProtocal;
	private TextView tv_null;
	private ImageView sv_menu_right;
	
	
	@Override
	protected void initView() {
	}

	@Override
	protected void init() {
		
		LogUtils.i("重新打开MYgoodactivity");
		
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

		if(myGoodProtocal==null){
			myGoodProtocal=new MyGoodProtocal();
		}

		goodsData=myGoodProtocal.load(0,20,true);
		
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				if( Activity_MyGoods.this.adapter!=null){
					LogUtils.i("refresh");
					if(tv_null!=null){
						tv_null.setVisibility(View.GONE);
					}
					adapter.setData(goodsData);
				}
			}
		});
		return LoadResult.success;
	}

	protected View createLoadedView() {
		View view = UIUtils.inflate(R.layout.activity_mygoods);
		sv_menu_right=(ImageView)view.findViewById(R.id.sv_menu_right);
		sv_menu_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(BaseActivity.getRunActivity(),Activity_AddGood.class);
				UIUtils.startActivity(intent);
			}
		});
		tv_null=(TextView)view.findViewById(R.id.tv_null);
		myGoodsLV=(PullToRefreshListView)view.findViewById(R.id.myGoods_lv);
		adapter=new Adapter_MyGoods(goodsData);
		adapter.setProtocal(myGoodProtocal);
		adapter.setListview(myGoodsLV);
		myGoodsLV.setAdapter(adapter);
		return view;
	}
	

}
