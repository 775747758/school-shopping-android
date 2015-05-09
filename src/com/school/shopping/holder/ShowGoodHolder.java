package com.school.shopping.holder;

import android.view.View;
import android.widget.TextView;

import com.school.shopping.R;
import com.school.shopping.entity.Good;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.vo.GoodVo;

public class ShowGoodHolder extends BaseHolder<GoodVo> {

	
	private TextView goodName_tv;
	@Override
	public void refreshView(GoodVo good) {
		goodName_tv.setText(good.getPrice());
	}

	@Override
	public View initView() {
		this.view= UIUtils.inflate(R.layout.listitem_showgoods);
		goodName_tv = (TextView) view.findViewById(R.id.goodName_tv);
		return this.view;
	}

}
