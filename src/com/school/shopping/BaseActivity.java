package com.school.shopping;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.testin.agent.TestinAgent;

public abstract class BaseActivity extends FragmentActivity {
	
	private static Activity runActivity;
	
	private static List<BaseActivity> mActivity=new LinkedList<BaseActivity>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		synchronized (mActivity) {
			//存储所有打开的activity
			LogUtils.i("创建：："+this.getLocalClassName());
			mActivity.add(this);
		}
		initTextInAgent();
		init();
		initView();
		initActionBar();
	}
	
	private void initTextInAgent() {
		TestinAgent.init(this,"f42870bb73ae40aed7940517427e9fb9", "DWJ");
		
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
	public  void exitApp(){
		List<BaseActivity> copy;
		synchronized (mActivity) {
			copy = new LinkedList<BaseActivity>(mActivity);
			LogUtils.i("mActivity  size：："+mActivity.size());
			LogUtils.i("copy  size：："+copy.size());
		}
		for(BaseActivity activity:copy){
			activity.finish();
		}
		//杀死自己进程
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	//把所有activity finish掉
		public  void finishAllActivity(){
			List<BaseActivity> copy;
			synchronized (mActivity) {
				copy = new LinkedList<BaseActivity>(mActivity);
				LogUtils.i("mActivity  size：："+mActivity.size());
				LogUtils.i("copy  size：："+copy.size());
			}
			for(BaseActivity activity:copy){
				LogUtils.i(activity.getLocalClassName());
				activity.finish();
			}
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
	
	public static void backToHome(){
		if(runActivity!=null){
			Intent intent=new Intent(runActivity,MainActivity.class);
			UIUtils.startActivity(intent);
			finishRunActivity();
		}
	}

}
