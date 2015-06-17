 package com.school.shopping.adapter;

import java.util.List;

import android.view.View;

import com.school.shopping.entity.Good;
import com.school.shopping.holder.BaseHolder;
import com.school.shopping.holder.GoodHolder;
import com.school.shopping.net.HomeProtocal;
import com.school.shopping.utils.LogUtils;


public class Adapter_Goods extends DefaultAdapter<Good> {

	private List<Good> goods;
	public Adapter_Goods(List<Good> goods) {
		super(goods);
		this.goods=goods;
	}
	/*//分页加载时加载更多商品
	public void addMoreGoods(List<Good> newGoods){
		for(Good good:newGoods){
			goods.add(good);
		}
		notifyDataSetChanged();
	}*/
	@Override
	public BaseHolder getHolder() {
		return new GoodHolder();
	}
	@Override
	protected List onLoadMore() {
		HomeProtocal protocal=HomeProtocal.getInstance();
		/*if(goods==null){
			LogUtils.i("旧数据为空，所以加载0--20");
			return protocal.load(0, 20,false);
		}
		else{
			LogUtils.i("加载"+goods.size()+"---"+(goods.size()+20));
			return protocal.load(goods.size(), goods.size()+20,false);
		}*/
		return null;
	}
	@Override
	protected void itemClick(int position,View view) {
		// TODO Auto-generated method stub
		
	}
	
}