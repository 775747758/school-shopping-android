package com.school.shopping;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import com.school.shopping.utils.LogUtils;

public abstract class BaseActivity extends Activity {
	
	private static Activity runActivity;
	
	List<BaseActivity> mActivity=new LinkedList<BaseActivity>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		synchronized (mActivity) {
			//存储所有打开的activity
			mActivity.add(this);
		}
		init();
		initView();
		initActionBar();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		runActivity=this;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		runActivity=null;
	}
	public static Activity getRunActivity() {
		return runActivity;
	}

	public void setRunActivity(Activity runActivity) {
		this.runActivity = runActivity;
	}

	//把所有activity finish掉
	public void exitApp(){
		List<BaseActivity> copy;
		synchronized (mActivity) {
			copy = new LinkedList<BaseActivity>(mActivity);
		}
		for(BaseActivity activity:copy){
			activity.finish();
		}
		//杀死自己进程
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	@Override
	protected void onDestroy() {
		synchronized (mActivity) {
			mActivity.remove(this);
		}
		super.onDestroy();
	}

	protected void initActionBar() {
		// TODO Auto-generated method stub
		
	}

	protected abstract void init();
	
	protected abstract void initView();

	
	
	public static void finishRunActivity(){
		if(runActivity!=null){
			LogUtils.i("已经停止当前activity");
			runActivity.finish();
		}
	}

}
