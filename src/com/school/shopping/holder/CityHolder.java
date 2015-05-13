package com.school.shopping.holder;

import android.view.View;
import android.widget.TextView;

import com.school.shopping.R;
import com.school.shopping.utils.UIUtils;

public class CityHolder extends BaseHolder<String> {
	
	private boolean isEnd;
	
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	private TextView tv_city;

	@Override
	public void refreshView(String data) {
		tv_city.setText(data);
	}

	@Override
	public View initView() {
		View view=UIUtils.inflate(R.layout.listitem_city);
		if(isEnd){
			View divider=view.findViewById(R.id.divider);
			divider.setVisibility(View.GONE);
		}
		tv_city=(TextView)view.findViewById(R.id.tv_city);
		return view;
	}

}
