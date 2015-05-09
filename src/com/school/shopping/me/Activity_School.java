package com.school.shopping.me;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_city;
import com.school.shopping.entity.User;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.AlterUserInfoProtocal;
import com.school.shopping.net.School_Protocal;
import com.school.shopping.utils.UIUtils;

public class Activity_School extends BaseActivity {

	private ListView lv_school;
	private List<String> schools;
	private Adapter_city adapter;
	Map<String, String> map=new HashMap<String, String>(); 
	
	@Override
	protected void initView() {
		setContentView(R.layout.activity_school);
		lv_school=(ListView)findViewById(R.id.lv_school);
		lv_school.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				commit(schools.get(position));
			}
		});
		ThreadManager.getShortPool().execute(new Runnable() {
			
			@Override
			public void run() {
				School_Protocal protocal=new School_Protocal();
				schools=protocal.load(-1, -1, true);
				UIUtils.runInMainThread(new Runnable() {
					
					@Override
					public void run() {
						if(lv_school!=null){
							adapter=new Adapter_city(schools,lv_school);
							lv_school.setAdapter(adapter);
						}
					}
				});
			}
		});
	}
	
	
	protected void commit(final String school) {
		ThreadManager.getLongPool().execute(new Runnable() {
			
			@Override
			public void run() {
				final AlterUserInfoProtocal protocal=new AlterUserInfoProtocal();
				map.put("alterType", "school");
				map.put("paramType", "String");
				map.put("value",school);
				protocal.setMap(map);
				
				ThreadManager.getLongPool().execute(new Runnable() {
					
					@Override
					public void run() {
						protocal.load(-1, -1, false);
						UIUtils.runInMainThread(new Runnable() {
							
							@Override
							public void run() {
								cacheNewUser(school);
								Intent intent=new Intent(Activity_School.this,Activity_DetailUserInfo.class);
								intent.putExtra("from", "Activity_School");
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
		// TODO Auto-generated method stub

	}

	public void cacheNewUser(String school){
		User user=Config.getCachedUser();
		user.setSchool(school);
		Config.cacheUser(user);
	}
}
