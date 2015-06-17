 package com.school.shopping.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.school.shopping.BaseActivity;
import com.school.shopping.entity.Good;
import com.school.shopping.holder.BaseHolder;
import com.school.shopping.holder.MyGoodHolder;
import com.school.shopping.home.Activity_GoodDetail;
import com.school.shopping.net.MyGoodProtocal;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.vo.GoodVo;


public class Adapter_MyGoods extends DefaultAdapter<Good> {

	private List<Good> goods = null;
	
	private boolean isMyGoods;
	private String otherUname;
	

	public void setOtherUname(String otherUname) {
		this.otherUname = otherUname;
	}

	public void setMyGoods(boolean isMyGoods) {
		this.isMyGoods = isMyGoods;
	}

	private MyGoodProtocal protocal;

	public void setProtocal(MyGoodProtocal protocal) {
		this.protocal = protocal;
	}

	public Adapter_MyGoods(List<Good> goods) {
		super(goods);
		this.goods = goods;
	}

	@Override
	protected void itemClick(int position,View view) {
		Intent intent=new Intent(UIUtils.getContext(),Activity_GoodDetail.class);
		Bundle bundle=new Bundle();
		Good good=goods.get(position-1);
		GoodVo goodvo=new GoodVo(good.getId(), good.getGoodName(), good.getType(), good.getPrice(), good.getIsAdjust(), good.getNewLevel(), good.getIntroduction(), good.getUid(), null, null, otherUname);
		bundle.putParcelable("GoodVo", goodvo);
		intent.putExtras(bundle);
		intent.putExtra("isMyGoods", isMyGoods);
		UIUtils.startActivity(intent);
	}

	@Override
	public BaseHolder getHolder() {
		return new MyGoodHolder();
	}

	@Override
	protected List onLoadMore() {
		if(goods==null||goods.size()==0){
			LogUtils.i("旧数据为空，所以加载0--19");
			return protocal.load(0, 19,false);
		}
		else{
			LogUtils.i("加载"+goods.size()+"---"+(goods.size()+20));
			return protocal.load(getData().size(), getData().size()+20,false);
		}
		
	}

	
}