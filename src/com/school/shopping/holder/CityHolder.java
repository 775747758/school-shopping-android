package com.school.shopping.holder;

import android.view.View;
import android.widget.TextView;

import com.school.shopping.R;
import com.school.shopping.utils.UIUtils;

public class CityHolder extends BaseHolder<String> {

	private TextView tv_city;

	@Override
	public void refreshView(String data) {
		tv_city.setText(data);
	}

	@Override
	public View initView() {
		View view=UIUtils.inflate(R.layout.listitem_city);
		tv_city=(TextView)view.findViewById(R.id.tv_city);
		return view;
	}

}
