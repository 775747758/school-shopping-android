package com.school.shopping.holder;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.CircleImageView;
import com.school.shopping.vo.GoodVo;

public class ShowGoodHolder extends BaseHolder<GoodVo> {

	private TextView goodName_tv;
	private ImageView goodbg_iv;
	private TextView newLevel_tv;
	private TextView price_tv;
	private CircleImageView portrait;
	private TextView tv_distance;
	private ImageLoadingListener animateFirstListener = new MyApplication.AnimateFirstDisplayListener();

	@Override
	public void refreshView(GoodVo good) {
		goodName_tv.setText(good.getGoodName());

		// isAdjust_tv.setText(good.getIsAdjust()+"");
		// newLevel_tv.setText("");
		price_tv.setText("¥" + good.getPrice());
		// type_tv.setText(good.getType());
		

		URLParam paramGood = new URLParam(URLProtocol.DOWNLOAD_DOOD_PIC);
		URLParam paramUser = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			paramGood.addParam("filename", "good_" + good.getUid() + "_" + good.getId() + ".jpg");
			paramUser.addParam("filename", "user_" + good.getUid() + ".jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i("URI", paramUser.getQueryStr());
		Log.i("URI", paramGood.getQueryStr());
		// MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(),
		// portrait,MyApplication.getOptions());
		MyApplication.getImageLoader().displayImage(paramGood.getQueryStr(), goodbg_iv);
	}

	@Override
	public View initView() {
		this.view = UIUtils.inflate(R.layout.listitem_showgoods);
		goodName_tv = (TextView) view.findViewById(R.id.goodName_tv);

		goodbg_iv = (ImageView) view.findViewById(R.id.goodbg_iv);
		goodName_tv = (TextView) view.findViewById(R.id.goodName_tv);
		newLevel_tv = (TextView) view.findViewById(R.id.newLevel_tv);
		price_tv = (TextView) view.findViewById(R.id.price_tv);
		// isAdjust_tv = (TextView) view.findViewById(R.id.isAdjust_tv);
		// type_tv = (TextView) view.findViewById(R.id.type_tv);
		portrait = (CircleImageView) view.findViewById(R.id.portrait);
		tv_distance = (TextView) view.findViewById(R.id.tv_distance);
		return this.view;
	}

	

}
