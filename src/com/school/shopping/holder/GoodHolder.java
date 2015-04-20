package com.school.shopping.holder;

import java.io.UnsupportedEncodingException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.meg7.widget.CustomShapeImageView;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.Good;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public class GoodHolder extends BaseHolder<Good> {

	private ImageView goodbg_iv;
	private TextView goodName_tv;
	private TextView newLevel_tv;
	private TextView price_tv;
	private TextView isAdjust_tv;
	private TextView type_tv;
	private CustomShapeImageView portrait;
	@Override
	public void refreshView(Good good) {
		
		
		//Good good = getData();
		
		goodName_tv.setText(good.getGoodName());
		isAdjust_tv.setText(good.getIsAdjust()+"");
		newLevel_tv.setText(good.getNewLevel());
		price_tv.setText(good.getPrice());
		type_tv.setText(good.getType());
		
		LogUtils.i(goodName_tv.getText()+"lalallal");
		
		URLParam paramGood = new URLParam(URLProtocol.DOWNLOAD_DOOD_PIC);
		URLParam paramUser = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			paramGood.addParam("filename", "good_"+good.getUid()+"_"+good.getId()+".jpg");
			paramUser.addParam("filename", "user_"+good.getUid()+".jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i("URI", paramUser.getQueryStr());
		Log.i("URI", paramGood.getQueryStr());
		MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(), portrait,MyApplication.getOptions());
		MyApplication.getImageLoader().displayImage(paramGood.getQueryStr(), goodbg_iv,MyApplication.getOptions());
		
		//ImageLoader.getInstance().displayImage(paramGood.getQueryStr(), holder.goodbg_iv, options, animateFirstListener);
		
	}

	@Override
	public View initView() {
		this.view= UIUtils.inflate(R.layout.listitem_goods);
		goodbg_iv = (ImageView) view.findViewById(R.id.goodbg_iv);
		goodName_tv = (TextView) view.findViewById(R.id.goodName_tv);
		newLevel_tv = (TextView) view.findViewById(R.id.newLevel_tv);
		price_tv = (TextView) view.findViewById(R.id.price_tv);
		isAdjust_tv = (TextView) view.findViewById(R.id.isAdjust_tv);
		type_tv = (TextView) view.findViewById(R.id.type_tv);
		portrait=(CustomShapeImageView)view.findViewById(R.id.portrait);
		return this.view;
	}

}
