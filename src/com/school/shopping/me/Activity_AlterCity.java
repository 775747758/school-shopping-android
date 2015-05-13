package com.school.shopping.me;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.school.shopping.login.Activity_Register2;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.utils.ListUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;

public class Activity_AlterCity extends BaseLocationActivity {

	private ListView lv_province;
	private List<Province> provinces;
	private Adapter_Province adapter;
	private ImageView iv_loadingok;
	private ProgressBar pb_loading;
	private TextView tv_city;
	private RelativeLayout rl_location;
	private String from;
	private boolean isRegister;
	private String city;
	private String province;
	private String longtitude;
	private String latitude;
	

	@Override
	protected void initViews() {

		setContentView(R.layout.activity_altercity);
		lv_province=(ListView)findViewById(R.id.lv_province);
		iv_loadingok=(ImageView)findViewById(R.id.iv_loadingok);
		pb_loading=(ProgressBar)findViewById(R.id.pb_loading);
		tv_city=(TextView)findViewById(R.id.tv_city);
		rl_location=(RelativeLayout)findViewById(R.id.rl_location);
		rl_location.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!StringUtils.isEmpty(province)){
					Intent intent=null;
					intent=new Intent(Activity_AlterCity.this,Activity_Register2.class);
					Config.saveCity(city);
					Config.saveProvince(province);
					setResult(RESULT_OK,intent);
					finish();
				}
				
			}
		});
		
		ThreadManager.getShortPool().execute(new Runnable() {
			
			@Override
			public void run() {
				provinces=AlterCity_DB.getProvince();
				provinces=ListUtils.sortProvince(provinces);
				UIUtils.runInMainThread(new Runnable() {
					
					@Override
					public void run() {
						if(lv_province!=null){
							adapter=new Adapter_Province(provinces,lv_province);
							adapter.setFrom(from);
							lv_province.setAdapter(adapter);
						}
					}
				});
			}
		});
	
		
	}

	@Override
	protected void initData() {
		Intent intent=getIntent();
		if(intent!=null){
			from=intent.getStringExtra("from");
			isRegister="Activity_Register2".equals(from)?true:false;
		}
	}

	@Override
	protected void onReceiveLocationCallBack(BDLocation bdLocation) {
		if(!StringUtils.isEmpty(bdLocation.getCity())){
			province=bdLocation.getProvince();
			rl_location.setClickable(true);
			city=bdLocation.getCity();
			longtitude=bdLocation.getLongitude()+"";
			latitude=bdLocation.getLatitude()+"";
			Config.saveLatitude(latitude);
			Config.saveLontitude(longtitude);
			tv_city.setText(province+" "+city);
			pb_loading.setVisibility(View.GONE);
			iv_loadingok.setVisibility(View.VISIBLE);
		}
	}
		

}
