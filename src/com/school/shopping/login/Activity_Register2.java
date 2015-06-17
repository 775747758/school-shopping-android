package com.school.shopping.login;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.me.Activity_AlterCity;
import com.school.shopping.utils.DialogUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.NetUtils;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;

import de.greenrobot.event.EventBus;

public class Activity_Register2 extends BaseActivity {

	private TextView city_et;
	private TextView school_et;
	private final int SELECT_SCHOOL=0;
	private final int SELECT_CITY=1;

	@Override
	protected void init() {
		/*
		 * 用于检查网络状态
		 
		ThreadManager.getShortPool().execute(new Runnable() {
			
			@Override
			public void run() {
				if(!NetUtils.isConnected()){
					UIUtils.runInMainThread(new Runnable() {
						
						@Override
						public void run() {
							DialogUtils.checkNetState();
						}
					});
				}
				
			}
		});*/
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_register2);
		city_et = (TextView) findViewById(R.id.city_et);
		school_et = (TextView) findViewById(R.id.school_et);
		String province=Config.getProvince();
		String city=Config.getCity();
		/*if(Config.isOneCity()){
			if(!StringUtils.isEmpty(province)){
				city_et.setText(province);
			}
		}else{
			if(!StringUtils.isEmpty(province)&&!StringUtils.isEmpty(city)){
				city_et.setText(province+" "+city);
			}
		}*/
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
	}

	public void selectschool(View view) {
		if(StringUtils.isEmpty(city_et.getText().toString().trim())){
			UIUtils.showMsg("请先选择你所在的城市,再选择学校！");
			return;
		}
		if(StringUtils.isEmpty(Config.getLatitude())||StringUtils.isEmpty(Config.getLontitude())){
			UIUtils.showMsg("您的城市信息有误，请重新选择");
			return;
		}
		Intent intent=new Intent(Activity_Register2.this,Activity_School.class);
		startActivityForResult(intent,SELECT_SCHOOL);
	}

	public void selectcity(View view) {
		Intent intent = new Intent(Activity_Register2.this, Activity_AlterCity.class);
		startActivityForResult(intent,SELECT_CITY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			switch (requestCode) {
			case SELECT_SCHOOL:
				school_et.setText(data.getStringExtra("school"));
				break;
			case SELECT_CITY:
				updateUI();
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	public void next(View view) {
		if (school_et.getText().toString().length() < 1) {
			UIUtils.showMsg("请选择您所在的学校！");
			return;
		}
		Intent intent = new Intent(Activity_Register2.this, Activity_Register3.class);
		intent.putExtra("school", school_et.getText().toString().trim());
		startActivity(intent);
		finish();
	}
	
	public void previous(View view){
		Intent intent=new Intent(Activity_Register2.this,Activity_Register1.class);
		startActivity(intent);
		finish();
	}
	
	
	
	private void updateUI(){
		String province=Config.getProvince();
		String city=Config.getCity();
		if(Config.isOneCity()){
			if(!StringUtils.isEmpty(province)){
				city_et.setText(province);
			}
		}else{
			if(!StringUtils.isEmpty(province)&&!StringUtils.isEmpty(city)){
				city_et.setText(province+" "+city);
			}
		}
	}
	

}
