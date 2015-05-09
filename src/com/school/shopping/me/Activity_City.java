package com.school.shopping.me;

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
import com.school.shopping.adapter.Adapter_city;
import com.school.shopping.db.AlterCity_DB;
import com.school.shopping.entity.User;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.AlterUserInfoProtocal;
import com.school.shopping.utils.ListUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public class Activity_City extends BaseActivity {

	private ListView lv_city;
	private List<String> cities;
	private Adapter_city adapter;
	private int proId;
	private String proName;
	Map<String, String> map=new HashMap<String, String>(); 

	@Override
	protected void initView() {
		setContentView(R.layout.activity_city);
		lv_city=(ListView)findViewById(R.id.lv_city);
		
		lv_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				commit(cities.get(position));
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
							adapter=new Adapter_city(cities,lv_city);
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
				final AlterUserInfoProtocal protocal=new AlterUserInfoProtocal();
				map.put("alterType", "city");
				map.put("paramType", "String");
				//这里有问题，试试post请求
				map.put("value",proName+" "+cityName);
				protocal.setMap(map);
				
				ThreadManager.getLongPool().execute(new Runnable() {
					
					@Override
					public void run() {
						protocal.load(-1, -1, false);
						UIUtils.runInMainThread(new Runnable() {
							
							@Override
							public void run() {
								cacheNewUser(proName+" "+cityName);
								Intent intent=new Intent(Activity_City.this,Activity_DetailUserInfo.class);
								intent.putExtra("from", "Activity_City");
								UIUtils.startActivity(intent);
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
		proId=intent.getIntExtra("proId", 0);
		proName=intent.getStringExtra("proName");
	}
	
	public void cacheNewUser(String city){
		User user=Config.getCachedUser();
		user.setCity(city);
		Config.cacheUser(user);
	}

}
