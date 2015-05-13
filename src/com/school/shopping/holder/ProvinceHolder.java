package com.school.shopping.holder;

import android.view.View;
import android.widget.TextView;

import com.school.shopping.R;
import com.school.shopping.entity.Province;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public class ProvinceHolder extends BaseHolder<Province> {

	private TextView tv_province;

	@Override
	public void refreshView(Province data) {
		tv_province.setText(data.getProName());
		LogUtils.i("ProvinceHolder::"+data.getProName());
	}

	@Override
	public View initView() {
		View view=UIUtils.inflate(R.layout.listitem_province);
		View divider=view.findViewById(R.id.divider);
		divider.setVisibility(View.GONE);
		tv_province=(TextView)view.findViewById(R.id.tv_province);
		return view;
	}

}
