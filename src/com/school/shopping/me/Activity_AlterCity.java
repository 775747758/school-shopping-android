package com.school.shopping.me;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.school.shopping.BaseActivity;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_Province;
import com.school.shopping.db.AlterCity_DB;
import com.school.shopping.entity.Province;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.utils.ListUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public class Activity_AlterCity extends BaseActivity {

	private ListView lv_province;
	private List<Province> provinces;
	private Adapter_Province adapter;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_altercity);
		lv_province=(ListView)findViewById(R.id.lv_province);
		
		
		
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
							lv_province.setAdapter(adapter);
							//adapter.setData(provinces);
						}
					}
				});
			}
		});
	}

	@Override
	protected void init() {}
		

}
