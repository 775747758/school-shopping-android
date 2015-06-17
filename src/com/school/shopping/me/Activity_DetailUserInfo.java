package com.school.shopping.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.User;
import com.school.shopping.holder.DetailUserInfoHolder;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.DetailUserInfoProtocol;
import com.school.shopping.utils.CompressPicture;
import com.school.shopping.utils.FileUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

import de.greenrobot.event.EventBus;

public class Activity_DetailUserInfo extends BaseActivity {

	private User user;
	private DetailUserInfoHolder holder;
	private String from=null;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_detailuserinfo);
		//在activity中直接使用holder要传递根view，否则没有办法获取到本页面的控件
		holder=new DetailUserInfoHolder(getWindow().getDecorView());
		if("Activity_AlterCity".equals(from)||"Activity_AlterUserInfo".equals(from)||"Activity_City".equals(from)||"Activity_School".equals(from)){
			LogUtils.i("从Activity_AlterUserInfo返回"+Config.getCachedUser().toString());
			holder.setData(Config.getCachedUser());
		}else{
			LogUtils.i("从qitalai");
			ThreadManager.getLongPool().execute(new Runnable() {
				@Override
				public void run() {
					final DetailUserInfoProtocol protocal=DetailUserInfoProtocol.getInstance();
					user=protocal.load(-1, -1, false);
					if(user!=null){
						Config.cacheUser( user);
					}
					UIUtils.runInMainThread(new Runnable() {
						@Override
						public void run() {
							if(protocal.isNetError()){
								UIUtils.showMsg("网络错误，请检查网络！");
								holder.setData(Config.getCachedUser());
							}else{
								holder.setData(user);
							}
							
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
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (!FileUtils.isSDCardAvailable()) {
				Log.i("TestFile", "SD card is not avaiable/writeable right now.");
				return;
			}
			//通知主界面的头像发生变化
			EventBus.getDefault().post("update");
			holder.onActivityResult(requestCode);
		}
	}
	
	@Override
	protected void onDestroy() {
		if(holder!=null){
			if(holder.bitmap!=null){
				holder.bitmap.recycle();
				holder.bitmap=null;
			}
		}
		holder=null;
		super.onDestroy();
	}
}
