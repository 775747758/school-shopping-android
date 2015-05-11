package com.school.shopping.showgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.school.shopping.BaseActivity;
import com.school.shopping.R;
import com.school.shopping.holder.GoodDetailHolder;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.utils.ViewUtils;
import com.school.shopping.view.LoadingPage;
import com.school.shopping.view.LoadingPage.LoadResult;
import com.school.shopping.vo.GoodVo;

public class Activity_GoodDetail extends BaseActivity {

	private GoodVo goodVo;
	private LoadingPage mContentView;
	private String from;

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
		return LoadResult.success;
	}

	protected View createLoadedView() {
		GoodDetailHolder holder=new GoodDetailHolder();
		if("Activity_ShowGoods".equals(from)){
			holder.setIsMyGoods(false);
		}
		else if("Activity_MyGoods".equals(from)){
			holder.setIsMyGoods(true);
		}
		holder.setActivity(Activity_GoodDetail.this);
		holder.setData(goodVo);
		return holder.getRootView();
	}

	//先调用初始化数据
	@Override
	protected void init() {
	    Intent intent = getIntent();  
        Bundle bundle = intent.getExtras();  
        goodVo = (GoodVo)bundle.getParcelable("GoodVo");
        from=intent.getStringExtra("from");
	}

}
