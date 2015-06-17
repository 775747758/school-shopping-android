package com.school.shopping.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.entity.Province;
import com.school.shopping.holder.ProvinceHolder;
import com.school.shopping.login.Activity_Register2;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.me.Activity_AlterCity;
import com.school.shopping.me.Activity_City;
import com.school.shopping.me.Activity_DetailUserInfo;
import com.school.shopping.net.AlterUserInfoProtocal;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;

public class Adapter_Province extends BaseAdapter{
	
	private List<Province> provinces;
	
	private OnItemClickListenerMe onItemClickListenerMe;

	public void setOnItemClickListener(OnItemClickListenerMe onItemClickListenerMe) {
		this.onItemClickListenerMe = onItemClickListenerMe;
	}

	
	
	
	public Adapter_Province( List<Province> provinces,ListView lv) {
		super();
		this.provinces = provinces;
	}

	@Override
	public int getCount() {
		return provinces.size();
	}

	@Override
	public Object getItem(int position) {
		return provinces.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ProvinceHolder holder=null;
		holder=new ProvinceHolder();
		holder.setData(provinces.get(position));
		
		return holder.getRootView();
	}
	
	public void setData(List<Province> provinces) {
		this.provinces = provinces;
		notifyDataSetChanged();
	}
	
	
	
	/*
	 * 这段代码在代码中出现3次，有点冗余，所以采用接口方式
	 */
	interface OnItemClickListenerMe{
		public void onItemClick();
	}
	

}
