package com.school.shopping.utils;

import java.lang.reflect.Field;

import android.view.ViewConfiguration;

public class ActionBarUtils {
	
	public static void initActionBar(){
		setOverflowButtonAlways();
	}
	
	private static void setOverflowButtonAlways()
	{
		try
		{
			ViewConfiguration config = ViewConfiguration.get(UIUtils.getContext());
			Field menuKey = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
