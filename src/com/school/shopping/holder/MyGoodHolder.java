package com.school.shopping.holder;

import java.io.UnsupportedEncodingException;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.Good;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.vo.GoodVo;

public class MyGoodHolder extends BaseHolder<Good> {

	
	
	private static final String[] types={"衣服","书籍","数码","其它"};
	
	private TextView goodName_tv;
	private TextView price_tv;
	private TextView type_tv;

	private ImageView goodbg_iv;
	@Override
	public void refreshView(Good good) {
		goodName_tv.setText(good.getGoodName());
		price_tv.setText("¥"+good.getPrice());
		type_tv.setText(types[good.getType()-1]);
		URLParam paramGood = new URLParam(URLProtocol.DOWNLOAD_DOOD_PIC);
		try {
			paramGood.addParam("filename", "good_" + good.getUid() + "_" + good.getId() + ".jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i("URI", paramGood.getQueryStr());
		MyApplication.getImageLoader().displayImage(paramGood.getQueryStr(), goodbg_iv, MyApplication.getOptions());
	}

	@Override
	public View initView() {
		this.view= UIUtils.inflate(R.layout.listitem_mygoods);
		goodName_tv = (TextView) view.findViewById(R.id.goodName_tv);
		price_tv=(TextView) view.findViewById(R.id.price_tv);
		type_tv=(TextView) view.findViewById(R.id.type_tv);
		goodbg_iv=(ImageView)view.findViewById(R.id.goodbg_iv);
		return this.view;
	}

}
