 package com.school.shopping.adapter;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.school.shopping.entity.Good;
import com.school.shopping.holder.BaseHolder;
import com.school.shopping.holder.HomeHolder;
import com.school.shopping.home.Activity_GoodDetail;
import com.school.shopping.net.ShowGoodProtocal;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.vo.GoodVo;


public class Adapter_Home extends DefaultAdapter<GoodVo> {

	private List<GoodVo> goods;
	
	private ShowGoodProtocal protocal;

	public void setProtocal(ShowGoodProtocal protocal) {
		this.protocal = protocal;
	}
	public Adapter_Home(List<GoodVo> goods) {
		super(goods);
		this.goods=goods;
	}
	@Override
	public BaseHolder getHolder() {
		return new HomeHolder();
	}
	
	@Override
	public List onLoadMore() {
		if(protocal==null){
			protocal=ShowGoodProtocal.getInstance();
		}
		if(goods==null){
			LogUtils.i("旧数据为空，所以加载0--20");
			return protocal.load(0, 19,false);
		}
		else{
			LogUtils.i("加载"+goods.size()+"---"+(goods.size()+20));
			return protocal.load(getData().size(), getData().size()+20,false);
		}
	}
	@Override
	protected void itemClick(int position,View view) {
		LogUtils.i("position"+position);
		Intent intent=new Intent(UIUtils.getContext(),Activity_GoodDetail.class);
		Bundle bundle=new Bundle();
		bundle.putParcelable("GoodVo", getData().get(position-1));
		intent.putExtras(bundle);
		intent.putExtra("from", "Activity_ShowGoods");
		UIUtils.startActivity(intent);
	}
	
	
	
}