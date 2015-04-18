 package com.school.shopping.adapter;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meg7.widget.CustomShapeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.Good;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.UIUtils;


public class Adapter_Goods extends BaseAdapter {

	List<Good> goods = null;
	
	public Adapter_Goods(List<Good> goods) {
		this.goods = goods;
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
		final ViewHolder holder;
		if (convertView == null) {
			view = UIUtils.inflate(R.layout.listitem_goods);
			holder = new ViewHolder();
			holder.goodbg_iv = (ImageView) view.findViewById(R.id.goodbg_iv);
			holder.goodName_tv = (TextView) view.findViewById(R.id.goodName_tv);
			holder.newLevel_tv = (TextView) view.findViewById(R.id.newLevel_tv);
			holder.price_tv = (TextView) view.findViewById(R.id.price_tv);
			holder.isAdjust_tv = (TextView) view.findViewById(R.id.isAdjust_tv);
			holder.type_tv = (TextView) view.findViewById(R.id.type_tv);
			holder.portrait=(CustomShapeImageView)view.findViewById(R.id.portrait);
			view.setTag(holder);
		} else {
			view=convertView;
			holder = (ViewHolder) view.getTag();
		}
		Log.i("good", goods.get(position).toString());
		holder.goodName_tv.setText(goods.get(position).getGoodName());
		holder.isAdjust_tv.setText(goods.get(position).getIsAdjust()+"");
		holder.newLevel_tv.setText(goods.get(position).getNewLevel());
		holder.price_tv.setText(goods.get(position).getPrice());
		holder.type_tv.setText(goods.get(position).getType());
		URLParam paramGood = new URLParam(URLProtocol.DOWNLOAD_DOOD_PIC);
		URLParam paramUser = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			paramGood.addParam("filename", "good_"+goods.get(position).getUid()+"_"+goods.get(position).getId()+".jpg");
			paramUser.addParam("filename", "user_"+goods.get(position).getUid()+".jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i("URI", paramUser.getQueryStr());
		Log.i("URI", paramGood.getQueryStr());
		MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(), holder.portrait,MyApplication.getOptions());
		MyApplication.getImageLoader().displayImage(paramGood.getQueryStr(), holder.goodbg_iv,MyApplication.getOptions());
		//ImageLoader.getInstance().displayImage(paramGood.getQueryStr(), holder.goodbg_iv, options, animateFirstListener);
		
		return view;
	}
	
	private static class ViewHolder {
		TextView isAdjust_tv;
		TextView newLevel_tv;
		TextView price_tv;
		TextView type_tv;
		TextView goodName_tv;
		ImageView goodbg_iv;
		CustomShapeImageView portrait;
	}
	
	//分页加载时加载更多商品
	public void addMoreGoods(List<Good> newGoods){
		for(Good good:newGoods){
			goods.add(good);
		}
		notifyDataSetChanged();
	}
}