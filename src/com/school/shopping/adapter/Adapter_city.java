package com.school.shopping.adapter;

import java.util.List;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.school.shopping.holder.CityHolder;
import com.school.shopping.me.Activity_DetailUserInfo;
import com.school.shopping.utils.UIUtils;

public class Adapter_city extends BaseAdapter{
	
	List<String> cities;

	public Adapter_city(List<String> cities,ListView lv) {
		super();
		this.cities = cities;
		
	}

	@Override
	public int getCount() {
		return cities.size();
	}

	@Override
	public Object getItem(int position) {
		return cities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CityHolder holder=null;
		holder=new CityHolder();
		holder.setData(cities.get(position));
		return holder.getRootView();
	}
	
	public void setData(List<String> cities) {
		this.cities = cities;
		notifyDataSetChanged();
	}

}
