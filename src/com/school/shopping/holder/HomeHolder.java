package com.school.shopping.holder;

import java.io.UnsupportedEncodingException;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.gc.materialdesign.views.ButtonFloat;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.me.Activity_DetailUserInfo;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.user.Activity_OthersUserInfo;
import com.school.shopping.utils.DistanceUtils;
import com.school.shopping.utils.LocationUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.CircleImageView;
import com.school.shopping.vo.GoodVo;

public class HomeHolder extends BaseHolder<GoodVo> {

	private TextView goodName_tv;
	private ImageView goodbg_iv;
	private TextView newLevel_tv;
	private TextView price_tv;
	private CircleImageView portrait;
	private TextView tv_distance;
	private ImageLoadingListener animateFirstListener = new MyApplication.AnimateFirstDisplayListener();
	private ButtonFloat buttonFloat;

	@Override
	public void refreshView(final GoodVo good) {
		goodName_tv.setText(good.getGoodName());
		price_tv.setText("¥" + good.getPrice());
		String distance=null;
		int diatance=getDistance(good.getLatitude(),good.getLongitude());
		if(diatance!=-1){
			distance=DistanceUtils.getDistance(diatance);
		}
		
		if(!StringUtils.isEmpty(distance)){
			tv_distance.setText(distance);
		}
		portrait.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=null;
				if(good.getUid()==Config.getUID()){
					intent=new Intent(BaseActivity.getRunActivity(),Activity_DetailUserInfo.class);
				}else{
					intent=new Intent(BaseActivity.getRunActivity(),Activity_OthersUserInfo.class);
					intent.putExtra("uid", good.getUid());
				}
				UIUtils.startActivity(intent);
			}
		});
		

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
		MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(), portrait);
		MyApplication.getImageLoader().displayImage(paramGood.getQueryStr(), goodbg_iv);
	}

	@Override
	public View initView() {
		this.view = UIUtils.inflate(R.layout.listitem_home);
		goodName_tv = (TextView) view.findViewById(R.id.goodName_tv);
		goodbg_iv = (ImageView) view.findViewById(R.id.goodbg_iv);
		goodName_tv = (TextView) view.findViewById(R.id.goodName_tv);
		newLevel_tv = (TextView) view.findViewById(R.id.newLevel_tv);
		price_tv = (TextView) view.findViewById(R.id.price_tv);
		
		portrait = (CircleImageView) view.findViewById(R.id.portrait);
		tv_distance = (TextView) view.findViewById(R.id.tv_distance);
		return this.view;
	}

	
	
	public int getDistance(String latitude,String lontitude){
		if(!StringUtils.isEmpty(latitude)&&!StringUtils.isEmpty(lontitude)&&!StringUtils.isEmpty(Config.getLatitude())&&!StringUtils.isEmpty(Config.getLontitude())){
			LogUtils.i("mlalong::"+Double.parseDouble(latitude)+","+Double.parseDouble(lontitude));
			LogUtils.i("otherlalong::"+Double.parseDouble(Config.getLatitude())+","+Double.parseDouble(Config.getLontitude()));
			LatLng mLocation=new LatLng(Double.parseDouble(latitude),Double.parseDouble(lontitude));
			LatLng otherLocation=new LatLng(Double.parseDouble(Config.getLatitude()),Double.parseDouble(Config.getLontitude()));
			double distance=DistanceUtil.getDistance(mLocation, otherLocation);
			return (int)distance;
		}
		return -1;
		
	}

}
