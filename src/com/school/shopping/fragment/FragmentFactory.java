package com.school.shopping.fragment;

import java.util.HashMap;

import android.util.Log;

public class FragmentFactory {
	private static final int Title_HOME = 0;
	private static final int Title_FRIENDS = 1;
	private static final int Title_CHAT= 2;
	private static final int Title_ME = 3;
    //初始化缓存的HashMap
	private static HashMap<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();
	
	public static BaseFragment createFragment(int position) {
		
		BaseFragment BaseFragment = mFragments.get(position);
		
		if(BaseFragment == null){
			switch (position) {
			case Title_HOME:
				Log.i("info", "创建：Title_HOME");
	            BaseFragment = new Fragment_Home();
				break;

			case Title_FRIENDS:
				Log.i("info", "创建：Title_FRIENDS");
	            BaseFragment = new TabFragment();
				break;

			case Title_CHAT:
				Log.i("info", "创建：Title_CHAT");
	            BaseFragment = new TabFragment();
				break;

			case Title_ME:
				Log.i("info", "创建：Title_ME");
				BaseFragment = new Fragment_Me();
				break;
			}
			mFragments.put(position, BaseFragment);
		}
		return BaseFragment;
	}
}
