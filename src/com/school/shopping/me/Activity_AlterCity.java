package com.school.shopping.me;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.school.shopping.BaseActivity;
import com.school.shopping.BaseLocationActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_Province;
import com.school.shopping.db.AlterCity_DB;
import com.school.shopping.entity.Province;
import com.school.shopping.entity.User;
import com.school.shopping.login.Activity_Register2;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.AlterUserInfoProtocal;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.ElasticListView;

public class Activity_AlterCity extends BaseLocationActivity implements OnItemClickListener {

	private ElasticListView lv_province;
	private List<Province> provinces;
	private Adapter_Province adapter;
	private ImageView iv_loadingok;
	private ProgressBar pb_loading;
	private TextView tv_city;
	private RelativeLayout rl_location;
	private String city;
	private String province;
	private String longtitude;
	private String latitude;
	private Map<String, String> mapCity = new HashMap<String, String>();
	private Map<String, String> mapProvince = new HashMap<String, String>();
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private int[] specialProvinceIds = { 1, 2, 3, 4, 33, 34 };

	@Override
	protected void initViews() {

		setContentView(R.layout.activity_altercity);
		lv_province = (ElasticListView) findViewById(R.id.lv_province);
		lv_province.setOnItemClickListener(this);
		// 动到顶部和底部时出现的阴影消除方法
		lv_province.setOverScrollMode(View.OVER_SCROLL_NEVER);
		iv_loadingok = (ImageView) findViewById(R.id.iv_loadingok);
		pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
		tv_city = (TextView) findViewById(R.id.tv_city);
		rl_location = (RelativeLayout) findViewById(R.id.rl_location);
		rl_location.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!StringUtils.isEmpty(city)) {

					// 注册
					if (StringUtils.isEmpty(Config.getCachedToken())) {
						Config.saveCity(city);
						Config.saveProvince(province);
						Intent intent = null;
						intent = new Intent(Activity_AlterCity.this, Activity_Register2.class);
						setResult(RESULT_OK, intent);
						finish();
					}// 修改用户信息
					else {
						commit();
					}

				}

			}
		});

		ThreadManager.getShortPool().execute(new Runnable() {

			@Override
			public void run() {
				provinces = AlterCity_DB.getProvince();
				Collections.sort(provinces);
				UIUtils.runInMainThread(new Runnable() {

					@Override
					public void run() {
						if (lv_province != null) {
							adapter = new Adapter_Province(provinces, lv_province);
							lv_province.setAdapter(adapter);

						}
					}
				});
			}
		});

	}

	@Override
	protected void initData() {
	}

	@Override
	protected void onReceiveLocationCallBack(BDLocation bdLocation) {
		if (!StringUtils.isEmpty(bdLocation.getCity())) {
			province = bdLocation.getProvince();
			rl_location.setClickable(true);
			city = bdLocation.getCity();
			longtitude = bdLocation.getLongitude() + "";
			latitude = bdLocation.getLatitude() + "";
			Config.saveLatitude(latitude);
			Config.saveLontitude(longtitude);
			tv_city.setText(province + " " + city);
			pb_loading.setVisibility(View.GONE);
			iv_loadingok.setVisibility(View.VISIBLE);
		}
	}

	public void previous(View view) {
		// 注册
		if (StringUtils.isEmpty(Config.getCachedToken())) {
			finish();
		}// 修改用户信息
		else {
			Intent intent = new Intent(Activity_AlterCity.this, Activity_DetailUserInfo.class);
			startActivity(intent);
			finish();
		}

	}

	public void cacheNewUser() {
		User user = Config.getCachedUser();
		user.setProvince(province);
		user.setCity(city);
		Config.cacheUser(user);
	}

	public void commit() {
		ThreadManager.getLongPool().execute(new Runnable() {

			@Override
			public void run() {

				mapCity.put("alterType", "city");
				mapCity.put("paramType", "String");
				mapCity.put("value", city);
				list.add(mapCity);

				mapProvince.put("alterType", "province");
				mapProvince.put("paramType", "String");
				mapProvince.put("value", province);
				list.add(mapProvince);
				final AlterUserInfoProtocal protocal=AlterUserInfoProtocal.getInstance();
				protocal.setData(list);
				ThreadManager.getLongPool().execute(new Runnable() {

					@Override
					public void run() {
						protocal.load(-1, -1, false);
						UIUtils.runInMainThread(new Runnable() {

							@Override
							public void run() {
								if(protocal.isNetError()){
									UIUtils.showMsg("网络错误，请检查网络！");
								}else{
									cacheNewUser();
									Intent intent = new Intent(Activity_AlterCity.this, Activity_DetailUserInfo.class);
									intent.putExtra("from", "Activity_AlterCity");
									startActivity(intent);
									finish();
								}
							}
						});
					}
				});
			}
		});

	}

	public boolean isContains(int id) {
		for (int i = 0; i < specialProvinceIds.length; i++) {
			if (specialProvinceIds[i] == id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		province=provinces.get(position).getProName();
		Config.saveProvince(province);
		int provinceId = provinces.get(position).getId();
		if (!isContains(provinceId)) {
			Config.setOneCity(false);
			Intent intent = new Intent(UIUtils.getContext(), Activity_City.class);
			intent.putExtra("proId", provinceId);
			intent.putExtra("proName", provinces.get(position).getProName());
			UIUtils.startActivity(intent);
			BaseActivity.finishRunActivity();
		} else {
			Config.setOneCity(true);
			//服务器数据库会判断
			city="null";
			if(StringUtils.isEmpty(Config.getCachedToken())){
				Intent intent = null;
				intent = new Intent(BaseActivity.getRunActivity(), Activity_Register2.class);
				BaseActivity.getRunActivity().setResult(RESULT_OK, intent);
				BaseActivity.finishRunActivity();
			}else{
				commit();
			}
			
		}
		
	}
	
	@Override
	protected void onDestroy() {
		if(provinces!=null){
			provinces.clear();
			provinces=null;
		}
		if(mapCity!=null){
			mapCity.clear();
			mapCity=null;
		}
		if(mapProvince!=null){
			mapProvince.clear();
			mapProvince=null;
		}
		specialProvinceIds=null;
		super.onDestroy();
	}

}
