 package com.school.shopping.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.school.shopping.R;
import com.school.shopping.entity.Good;
import com.school.shopping.holder.BaseHolder;
import com.school.shopping.holder.GoodHolder;
import com.school.shopping.holder.MoreHolder;
import com.school.shopping.net.HomeProtocal;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.NonScrollGridView;


public class Adapter_Home extends BaseAdapter {
	
	String[] typeAry={"衣服","书籍","数码","杂货铺"};

	Map<String, Object> goods;
	public Adapter_Home(Map<String, Object> goods) {
		
		this.goods=goods;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return goods.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return goods.get(position);
	}
	

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view;
		HomeGoodHolder holder=null;
		if (convertView == null) {
			holder = new HomeGoodHolder();
		} else if(convertView != null&&convertView.getTag() instanceof BaseHolder){
			holder = (HomeGoodHolder) convertView.getTag();
		}
		
		holder.setData((List<Good>) goods.get(typeAry[position]));
		return holder.getRootView();
	}
	
	class HomeGoodHolder extends BaseHolder<List<Good>>{

		
		private TextView tv_title;
		private NonScrollGridView gv;
		
		@Override
		public View initView() {
			View view= UIUtils.inflate(R.layout.listitem_home);
			tv_title=(TextView) view.findViewById(R.id.tv_title);
			gv=(NonScrollGridView) view.findViewById(R.id.gv_goods);
			return view;
		}

		@Override
		public void refreshView(List<Good> data) {
			
			tv_title.setText(data.get(0).getType());
			gv.setAdapter(new GridAdapter_Home(data));
		}
		
	}



	
}