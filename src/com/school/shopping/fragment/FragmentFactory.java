package com.school.shopping.fragment;

import io.rong.imkit.fragment.ConversationListFragment;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public class FragmentFactory {
	private static final int Title_HOME = 0;
	private static final int Title_FRIENDS = 1;
	private static final int Title_CHAT= 2;
	private static final int Title_ME = 3;
    //初始化缓存的HashMap
	private static HashMap<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();
	
	public static Fragment createFragment(int position) {
		/*if(mFragments==null){
			
		}*/
		
		Fragment baseFragment = mFragments.get(position);
		
		if(baseFragment == null){
			switch (position) {
			case Title_HOME:
				Log.i("info", "创建：Title_HOME");
				baseFragment = new Fragment_Home();
				break;

			case Title_FRIENDS:
				Log.i("info", "创建：Title_FRIENDS");
				baseFragment = new Fragment_Friends();
				break;

				//ConversationListFragment不能转换为BaseFragmnet，所以不能在这里初始化
			case Title_CHAT:
				Log.i("info", "创建：Title_CHAT");
				baseFragment = new ConversationListFragment();
				break;

			case Title_ME:
				Log.i("info", "创建：Title_ME");
				baseFragment = new Fragment_Me();
				break;
			}
			mFragments.put(position, baseFragment);
		}
		else{
			Log.i("info", "bu创建");
		}
		return baseFragment;
	}
	
	
	public static void destoryFragments(){
		//mFragments.clear();
		//mFragments=null;
	}
	
	public static void initFragments(){
		for(int i=0;i<4;i++){
			createFragment(i);
		}
	}
}
