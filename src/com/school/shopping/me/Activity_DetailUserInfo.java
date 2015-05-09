package com.school.shopping.me;

import android.content.Intent;

import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.entity.User;
import com.school.shopping.holder.DetailUserInfoHolder;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.DetailUserInfoProtocol;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public class Activity_DetailUserInfo extends BaseActivity {

	private User user;
	private DetailUserInfoHolder holder;
	private String from=null;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_detailuserinfo);
		//在activity中直接使用holder要传递根view，否则没有办法获取到本页面的控件
		holder=new DetailUserInfoHolder(getWindow().getDecorView(),Activity_DetailUserInfo.this);
		if("Activity_AlterUserInfo".equals(from)||"Activity_City".equals(from)||"Activity_School".equals(from)){
			LogUtils.i("从Activity_AlterUserInfo返回"+Config.getCachedUser().toString());
			holder.setData(Config.getCachedUser());
		}else{
			LogUtils.i("从qitalai");
			ThreadManager.getLongPool().execute(new Runnable() {
				@Override
				public void run() {
					DetailUserInfoProtocol protocal=new DetailUserInfoProtocol();
					user=protocal.load(-1, -1, false);
					if(user!=null){
						Config.cacheUser( user);
					}
					UIUtils.runInMainThread(new Runnable() {
						@Override
						public void run() {
							holder.setData(user);
						}
					});
				}
			});
		}
	}

	@Override
	protected void init() {
		Intent intent=getIntent();
		if(intent!=null){
			from=intent.getStringExtra("from");
		}
	}

}
