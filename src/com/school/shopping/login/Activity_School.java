package com.school.shopping.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_City;
import com.school.shopping.entity.User;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.me.Activity_City;
import com.school.shopping.me.Activity_DetailUserInfo;
import com.school.shopping.net.AlterUserInfoProtocal;
import com.school.shopping.net.SchoolProtocal;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.utils.ViewUtils;
import com.school.shopping.view.ElasticListView;
import com.school.shopping.view.LoadingPage;
import com.school.shopping.view.LoadingPage.LoadResult;

public class Activity_School extends BaseActivity {

	private LoadingPage mContentView;
	private List<String> list;
	private Map<String, String> mapSchool=new HashMap<String, String>();
	private List<Map<String, String>> listRequest=new ArrayList<Map<String, String>>();

	@Override
	protected void init() {}

	@Override
	protected void initView() {
		if (mContentView == null) {
			mContentView = new LoadingPage(UIUtils.getContext()) {

				@Override
				public LoadResult load() {
					return Activity_School.this.load();
				}

				@Override
				public View createLoadedView() {
					return Activity_School.this.createLoadedView();
				}
			};
		} else {
			ViewUtils.removeSelfFromParent(mContentView);
		}

		setContentView(mContentView);
		mContentView.show();
	}

	protected View createLoadedView() {
		View view = UIUtils.inflate(R.layout.activity_school);
		ElasticListView lv_school = (ElasticListView) view.findViewById(R.id.lv_school);
		ImageView iv_back=(ImageView)view.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(StringUtils.isEmpty(Config.getCachedToken())){
					finish();
				}else{
					Intent intent=new Intent(Activity_School.this,Activity_DetailUserInfo.class);
					intent.putExtra("from", "Activity_School");
					UIUtils.startActivity(intent);
					finish();
				}
				
			}
		});
		if(list!=null){
			Adapter_City adapter = new Adapter_City(list);
			lv_school.setAdapter(adapter);
			lv_school.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if(StringUtils.isEmpty(Config.getCachedToken())){
						Intent intent = new Intent(Activity_School.this, Activity_Register2.class);
						intent.putExtra("school", list.get(arg2));
						setResult(RESULT_OK, intent);
						finish();
					}else{
						commit(list.get(arg2));
					}
					
				}
			});
		}
		return view;
	}

	protected LoadResult load() {
		SchoolProtocal protocal =SchoolProtocal.getInstance();
		list = protocal.load(-1, -1, true);
		if (list == null || list.size() == 0) {
			return LoadResult.error;
		}
		return LoadResult.success;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(list!=null){
			list.clear();
			list=null;
		}
		if(mapSchool!=null){
			mapSchool.clear();
			mapSchool=null;
		}
		if(listRequest!=null){
			listRequest.clear();
			listRequest=null;
		}
		mContentView=null;
	}
	
	
	protected void commit(final String school) {
		ThreadManager.getLongPool().execute(new Runnable() {
			
			@Override
			public void run() {
				
				mapSchool.put("alterType", "school");
				mapSchool.put("paramType", "String");
				mapSchool.put("value",school);
				listRequest.add(mapSchool);
				final AlterUserInfoProtocal protocal=AlterUserInfoProtocal.getInstance();
				protocal.setData(listRequest);
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
									cacheNewUser(school);
									Intent intent=new Intent(Activity_School.this,Activity_DetailUserInfo.class);
									intent.putExtra("from", "Activity_School");
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
	
	
	public void cacheNewUser(String school){
		User user=Config.getCachedUser();
		user.setSchool(school);
		Config.cacheUser(user);
	}
	

}
