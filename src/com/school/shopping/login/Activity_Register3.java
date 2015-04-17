package com.school.shopping.login;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.school.shopping.R;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.UIUtils;

public class Activity_Register3 extends Activity {

	private TextView city_et;
	private TextView school_et;
	List<String> schoolList=new ArrayList<String>();
	private Builder builder;
	private LayoutInflater inflater;
	private AlertDialog RangeAlert;
	private ListView range_lv;
	private LocationClient mLocationClient;
	private MyLocationListener mMyLocationListener;
	private GeofenceClient mGeofenceClient;
	private Vibrator mVibrator;
	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register3);
		
		
		Intent intent=getIntent();
		username=intent.getStringExtra("username");
		password=intent.getStringExtra("password");

		city_et = (TextView) findViewById(R.id.city_et);
		school_et = (TextView) findViewById(R.id.school_et);
		school_et.setText("浙江工业大学");
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		InitLocation();
		mLocationClient.start();
	}
	
	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);//设置定位模式
		int span=1000;
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	public void selectschool(View view) {
		try {
			getJSONVolley();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取json字符串
	public void getJSONVolley() throws UnsupportedEncodingException {
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		String JSONDateUrl =URLProtocol.GET_UNIVERSITY;
		URLParam param = new URLParam(JSONDateUrl);
		param.addParam_Encode("query", "大学");
		param.addParam_Encode("region", "包头");
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET,param.getQueryStr(), null,
				new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						Log.i("dddd", response.toString());
						int status = 0;
						try {
							status = response.getInt("status");
							if (status == 0) {
								String schoolStr=response.get("results").toString();
								Log.i("school", schoolStr);
								JSONArray schoolArr=new JSONArray(schoolStr);
								for(int i=0;i<schoolArr.length();i++){
									JSONObject school=schoolArr.getJSONObject(i);
									String name=school.getString("name");
									schoolList.add(name);
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						spinner();
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(
							com.android.volley.VolleyError arg0) {
						UIUtils.showMsg(URLProtocol.MSG_SERVER_ERROR);
					}
				});
		
		
		
		requestQueue.add(jsonObjectRequest);
	}
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void spinner() {
			builder = new AlertDialog.Builder(
					Activity_Register3.this, R.style.Dialog);
			builder.setInverseBackgroundForced(true);
			inflater = LayoutInflater.from(getApplicationContext());
			View view1 = inflater.inflate(R.layout.dialog_range, null);
			RangeAlert = builder.create();
			range_lv = (ListView) view1.findViewById(R.id.range_lv);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listitem_search_range, R.id.item_tv, schoolList.toArray(new String[schoolList.size()]));
			range_lv.setAdapter(adapter);
			range_lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

					school_et.setText(schoolList.get(arg2));
					RangeAlert.dismiss();
				}
			});
			RangeAlert.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					
				}
			});
			RangeAlert.show();
			RangeAlert.setContentView(view1);
			RangeAlert.getWindow().setLayout(2 * UIUtils.getWindowHeight(Activity_Register3.this) / 3,UIUtils.getWIndowWidth(Activity_Register3.this)-200 );
	}
	
	
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			city_et.setText(location.getCity());
			
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				
			}
			
		}


	}
	
	public void next(View view) {

		/*if (city_et.getText().toString().length()<1 ) {
			Toast.makeText(getApplicationContext(), "您请等待定位您所在的城市！",
					Toast.LENGTH_SHORT).show();
			return;
		}*/
		if (school_et.getText().toString().length()<1) {
			UIUtils.showMsg("请选择您所在的学校！");
			return;
		}

		Intent intent = new Intent(Activity_Register3.this,
				Activity_Register2.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		intent.putExtra("city", "包头");
		intent.putExtra("school", school_et.getText().toString());
		startActivity(intent);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

	}
	
}
