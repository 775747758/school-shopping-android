package com.school.shopping;

import android.app.Service;
import android.os.Vibrator;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public abstract class BaseLocationActivity extends BaseActivity implements BDLocationListener{

	private LocationClient mLocationClient;
	private GeofenceClient mGeofenceClient;
	private Vibrator mVibrator;
	private LocationClientOption option;
	
	@Override
	protected void initView() {
		initViews();
		initLocationClient();
	}

	protected abstract void initViews();

	@Override
	protected void init() {
		initData();
	}
	protected abstract void initData();

	@Override
	public void onReceiveLocation(BDLocation bdLocation) {
		onReceiveLocationCallBack(bdLocation);
	}

	protected abstract void onReceiveLocationCallBack(BDLocation bdLocation);
	
	private void initLocationClient() {
		if(mLocationClient==null){
			mLocationClient = new LocationClient(this.getApplicationContext());
		}
		mLocationClient.registerLocationListener(this);
		if(mGeofenceClient==null){
			mGeofenceClient = new GeofenceClient(getApplicationContext());
		}
		if(mVibrator==null){
			mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		}
		initClientOption();
		mLocationClient.start();
	}
	
	private void initClientOption(){
		if(option==null){
			option = new LocationClientOption();
		}
		option.setLocationMode(LocationMode.Battery_Saving);//设置定位模式
		int span=1000;
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

}
