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
import com.school.shopping.BaseActivity;
import com.school.shopping.BaseLocationActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.me.Activity_AlterCity;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;

public class Activity_Register2 extends BaseActivity {

	private TextView city_et;
	private TextView school_et;
	List<String> schoolList = new ArrayList<String>();
	private Builder builder;
	private AlertDialog RangeAlert;
	private ListView range_lv;
	private String username;
	private String password;
	private static final int SELECT_SCHOOL=0;
	private static final int SELECT_CITY=1;

	@Override
	protected void init() {
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		password = intent.getStringExtra("password");
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_register2);
		city_et = (TextView) findViewById(R.id.city_et);
		school_et = (TextView) findViewById(R.id.school_et);
		String province=Config.getProvince();
		String city=Config.getCity();
		if(!StringUtils.isEmpty(province)&&!StringUtils.isEmpty(city)){
			city_et.setText(province+" "+city);
		}
	}

	public void selectschool(View view) {
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
				city_et.setText(Config.getProvince()+" "+Config.getCity());
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
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		intent.putExtra("city", city_et.getText().toString().trim());
		intent.putExtra("school", school_et.getText().toString().trim());
		startActivity(intent);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

	}

}
