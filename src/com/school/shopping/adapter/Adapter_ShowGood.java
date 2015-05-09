 package com.school.shopping.adapter;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.school.shopping.entity.Good;
import com.school.shopping.holder.BaseHolder;
import com.school.shopping.holder.ShowGoodHolder;
import com.school.shopping.net.ShowGoodProtocal;
import com.school.shopping.showgoods.Activity_GoodDetail;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.vo.GoodVo;


public class Adapter_ShowGood extends DefaultAdapter<GoodVo> {

	private List<GoodVo> goods;
	private ShowGoodProtocal protocal;

	public void setProtocal(ShowGoodProtocal protocal) {
		this.protocal = protocal;
	}
	public Adapter_ShowGood(List<GoodVo> goods) {
		super(goods);
		this.goods=goods;
	}
	@Override
	public BaseHolder getHolder() {
		return new ShowGoodHolder();
	}
	
	@Override
	protected List onLoadMore() {
		if(protocal==null){
			protocal=new ShowGoodProtocal();
		}
		if(goods==null){
			LogUtils.i("旧数据为空，所以加载0--20");
			return protocal.load(0, 20,false);
		}
		else{
			LogUtils.i("加载"+goods.size()+"---"+(goods.size()+20));
			return protocal.load(goods.size(), goods.size()+20,false);
		}
	}
	@Override
	protected void itemClick(int position) {
		Intent intent=new Intent(UIUtils.getContext(),Activity_GoodDetail.class);
		Bundle bundle=new Bundle();
		bundle.putParcelable("GoodVo", goods.get(position));
		intent.putExtras(bundle);
		UIUtils.startActivity(intent);
	}
	
	
	
}