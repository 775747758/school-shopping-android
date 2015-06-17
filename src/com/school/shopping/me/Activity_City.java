package com.school.shopping.me;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_Province;
import com.school.shopping.adapter.Adapter_City;
import com.school.shopping.db.AlterCity_DB;
import com.school.shopping.entity.User;
import com.school.shopping.login.Activity_Register2;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.AlterUserInfoProtocal;
import com.school.shopping.utils.ListUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.ElasticListView;

public class Activity_City extends BaseActivity {

	private ElasticListView lv_city;
	private List<String> cities;
	private Adapter_City adapter;
	private int proId;
	private String proName;
	private Map<String, String> mapCity=new HashMap<String, String>();
	private Map<String, String> mapProvince=new HashMap<String, String>();
	private List<Map<String, String>> list=new ArrayList<Map<String, String>>();

	@Override
	protected void initView() {
		setContentView(R.layout.activity_city);
		lv_city=(ElasticListView)findViewById(R.id.lv_city);
		//动到顶部和底部时出现的阴影消除方法
		lv_city.setOverScrollMode(View.OVER_SCROLL_NEVER);
		lv_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Config.saveCity(cities.get(position));
				if(StringUtils.isEmpty(Config.getCachedToken())){
					Intent intent=new Intent(Activity_City.this,Activity_Register2.class);
					startActivity(intent);
					finish();
				}else{
					commit(cities.get(position));
				}
			}
		});
		ThreadManager.getShortPool().execute(new Runnable() {
			
			@Override
			public void run() {
				cities=AlterCity_DB.getCity(proId);
				cities=ListUtils.sortCity(cities);
				UIUtils.runInMainThread(new Runnable() {
					
					@Override
					public void run() {
						if(lv_city!=null){
							adapter=new Adapter_City(cities);
							lv_city.setAdapter(adapter);
						}
					}
				});
			}
		});
	}

	protected void commit(final String cityName) {
		ThreadManager.getLongPool().execute(new Runnable() {
			
			@Override
			public void run() {
				
				mapCity.put("alterType", "city");
				mapCity.put("paramType", "String");
				mapCity.put("value",cityName);
				list.add(mapCity);
				
				mapProvince.put("alterType", "province");
				mapProvince.put("paramType", "String");
				mapProvince.put("value",proName);
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
									cacheNewUser(cityName);
									Intent intent=new Intent(Activity_City.this,Activity_DetailUserInfo.class);
									intent.putExtra("from", "Activity_City");
									UIUtils.startActivity(intent);
									finish();
								}
								
							}
						});
					}
				});
			}
		});
		
	}

	@Override
	protected void init() {
		Intent intent=getIntent();
		if(intent!=null){
			proId=intent.getIntExtra("proId", 0);
			proName=intent.getStringExtra("proName");
		}
		
	}
	
	public void cacheNewUser(String city){
		User user=Config.getCachedUser();
		user.setProvince(proName);
		user.setCity(city);
		Config.cacheUser(user);
	}
	
	public void previous(View view){
		Intent intent=new Intent(Activity_City.this,Activity_AlterCity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(cities!=null){
			cities.clear();
			cities=null;
		}
		if(mapCity!=null){
			mapCity.clear();
			mapCity=null;
		}
		if(mapProvince!=null){
			mapProvince.clear();
			mapProvince=null;
		}
		if(list!=null){
			list.clear();
			list=null;
		}
	}

}
