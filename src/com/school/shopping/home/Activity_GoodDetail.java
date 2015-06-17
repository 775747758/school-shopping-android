package com.school.shopping.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.school.shopping.BaseActivity;
import com.school.shopping.entity.Good;
import com.school.shopping.holder.GoodDetailHolder;
import com.school.shopping.net.GoodDetailProtocal;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.utils.ViewUtils;
import com.school.shopping.view.LoadingPage;
import com.school.shopping.view.LoadingPage.LoadResult;
import com.school.shopping.vo.GoodVo;

public class Activity_GoodDetail extends BaseActivity {

	private GoodVo goodVo;
	private LoadingPage mContentView;
	private boolean isMyGoods;
	private Good good;

	//后初始化界面
	@Override
	protected void initView() {
		
		if(mContentView == null){
			mContentView = new LoadingPage(UIUtils.getContext()) {
				
				@Override
				public LoadResult load() {
					
					return Activity_GoodDetail.this.load();
				}
				
				@Override
				public View createLoadedView() {
					return Activity_GoodDetail.this.createLoadedView();
				}
			};
		}else{
			ViewUtils.removeSelfFromParent(mContentView);
		}
		
		setContentView(mContentView);
		mContentView.show();
	
	}

	protected LoadResult load() {
		final GoodDetailProtocal goodDetailProtocal = GoodDetailProtocal.getInstance(goodVo.getId());
		goodDetailProtocal.setId(goodVo.getId());
		good = goodDetailProtocal.load(-1,-1, false);
		UIUtils.runInMainThread(new Runnable() {
			
			@Override
			public void run() {
				if(goodDetailProtocal.isNetError()){
					UIUtils.showMsg("网络错误，请检查网络！");
				}
			}
		});
		if(good==null){
			return LoadResult.error;
		}
		return LoadResult.success;
	}

	protected View createLoadedView() {
		GoodDetailHolder holder=new GoodDetailHolder();
		holder.setIsMyGoods(isMyGoods);
		holder.setActivity(Activity_GoodDetail.this);
		GoodVo vo=new GoodVo(good.getId(), good.getGoodName(), good.getType(), good.getPrice(), good.getIsAdjust(), good.getNewLevel(), good.getIntroduction(), goodVo.getUid(), goodVo.getLatitude(), goodVo.getLongitude(), goodVo.getUname());
		holder.setData(vo);
		return holder.getRootView();
	}

	//先调用初始化数据
	@Override
	protected void init() {
	    Intent intent = getIntent();  
        Bundle bundle = intent.getExtras();  
        goodVo = (GoodVo)bundle.getParcelable("GoodVo");
        LogUtils.i("goodVo::"+goodVo.toString());
        isMyGoods=intent.getBooleanExtra("isMyGoods", false);
	}

}
