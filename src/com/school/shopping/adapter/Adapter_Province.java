package com.school.shopping.adapter;

import java.util.List;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.school.shopping.entity.Province;
import com.school.shopping.holder.ProvinceHolder;
import com.school.shopping.me.Activity_City;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public class Adapter_Province extends BaseAdapter{
	
	List<Province> provinces;

	public Adapter_Province( List<Province> provinces,ListView lv) {
		super();
		this.provinces = provinces;
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LogUtils.i("dainjile1");
				Intent intent=new Intent(UIUtils.getContext(),Activity_City.class);
				intent.putExtra("proId", Adapter_Province.this.provinces.get(position).getId());
				intent.putExtra("proName", Adapter_Province.this.provinces.get(position).getProName());
				UIUtils.startActivity(intent);
			}
		});
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

}
