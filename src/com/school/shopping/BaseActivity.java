package com.school.shopping;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {
	
	List<BaseActivity> mActivity=new LinkedList<BaseActivity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		synchronized (mActivity) {
			//存储所有打开的activity
			mActivity.add(this);
		}
		init();
		initView();
		initActionBar();
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

	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
