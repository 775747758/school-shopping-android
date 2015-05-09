package com.school.shopping.me;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.entity.User;
import com.school.shopping.holder.DetailUserInfoHolder;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.AlterUserInfoProtocal;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public class Activity_AlterUserInfo extends BaseActivity {

	private EditText et_realName;
	private ImageView iv_checkMark_male;
	private ImageView iv_checkMark_famale;
	private LinearLayout ll_name;
	private LinearLayout ll_gender;
	private TextView tv_male;
	private TextView tv_female;
	private int alterType;
	private TextView tv_menu_right;
	private int genderResult=0;
	private User user;
	private TextView title_bar_name;
	private static final int MALE=1;
	private static final int FEMALE=0;
	Map<String, String> map=new HashMap<String, String>(); 

	@Override
	protected void initView() {
		setContentView(R.layout.activity_alteruserinfo);
		
		title_bar_name=(TextView)findViewById(R.id.title_bar_name);
		et_realName=(EditText)findViewById(R.id.et_realName);
		
		
		
		
		iv_checkMark_male=(ImageView)findViewById(R.id.iv_checkMark_male);
		iv_checkMark_famale=(ImageView)findViewById(R.id.iv_checkMark_famale);
		if(user.getGender()==MALE||user.getGender()==FEMALE){
			genderResult=user.getGender();
		}
		if(genderResult==MALE){
			iv_checkMark_male.setVisibility(View.VISIBLE);
			iv_checkMark_famale.setVisibility(View.GONE);
		}
		else {
			iv_checkMark_male.setVisibility(View.GONE);
			iv_checkMark_famale.setVisibility(View.VISIBLE);
		}
		
		ll_name=(LinearLayout)findViewById(R.id.ll_name);
		ll_gender=(LinearLayout)findViewById(R.id.ll_gender);
		tv_male=(TextView)findViewById(R.id.tv_male);
		tv_male.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LogUtils.i("male");
				genderResult=MALE;
				iv_checkMark_male.setVisibility(View.VISIBLE);
				iv_checkMark_famale.setVisibility(View.GONE);
			}
		});
		tv_female=(TextView)findViewById(R.id.tv_female);
		tv_female.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LogUtils.i("female");
				genderResult=FEMALE;
				iv_checkMark_male.setVisibility(View.GONE);
				iv_checkMark_famale.setVisibility(View.VISIBLE);
			}
		});
		
		tv_menu_right=(TextView)findViewById(R.id.tv_menu_right);
		tv_menu_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final AlterUserInfoProtocal protocal=new AlterUserInfoProtocal();
				
				
				switch (alterType) {
				case DetailUserInfoHolder.ALTER_NAME:
					ll_name.setVisibility(View.VISIBLE);
					map.put("alterType", "realName");
					map.put("paramType", "String");
					map.put("value", et_realName.getText().toString().trim());
					
					break;
				case DetailUserInfoHolder.ALTER_GENDER:
					ll_gender.setVisibility(View.VISIBLE);
					map.put("alterType", "gender");
					map.put("paramType", "int");
					map.put("value", genderResult+"");
					break;
				case DetailUserInfoHolder.ALTER_QQ:
					ll_name.setVisibility(View.VISIBLE);
					map.put("alterType", "qq");
					map.put("paramType", "String");
					map.put("value", et_realName.getText().toString().trim());
					break;
				case DetailUserInfoHolder.ALTER_PHONE:
					ll_name.setVisibility(View.VISIBLE);
					map.put("alterType", "phone");
					map.put("paramType", "String");
					map.put("value", et_realName.getText().toString().trim());
					break;

				}
				
				protocal.setMap(map);
				ThreadManager.getLongPool().execute(new Runnable() {
					
					@Override
					public void run() {
						protocal.load(-1, -1, false);
						UIUtils.runInMainThread(new Runnable() {
							
							@Override
							public void run() {
								cacheNewUser();
								LogUtils.i("Activity_AlterUserInfo::修改结果："+map.get("value"));
								Intent intent=new Intent(Activity_AlterUserInfo.this,Activity_DetailUserInfo.class);
								intent.putExtra("from", "Activity_AlterUserInfo");
								UIUtils.startActivity(intent);
							}
						});
					}
				});
				
			}
		});
		showView();
	}

	private void showView() {
		switch (alterType) {
		case DetailUserInfoHolder.ALTER_NAME:
			ll_name.setVisibility(View.VISIBLE);
			title_bar_name.setText("昵称");
			et_realName.setText(user.getRealName());
			et_realName.setSelection(user.getRealName().length());
			break;
		case DetailUserInfoHolder.ALTER_GENDER:
			ll_gender.setVisibility(View.VISIBLE);
			title_bar_name.setText("性别");
			break;
		case DetailUserInfoHolder.ALTER_QQ:
			ll_name.setVisibility(View.VISIBLE);
			title_bar_name.setText("QQ");
			et_realName.setText(user.getQq());
			et_realName.setSelection(user.getRealName().length());
			break;
		case DetailUserInfoHolder.ALTER_PHONE:
			ll_name.setVisibility(View.VISIBLE);
			title_bar_name.setText("手机");
			et_realName.setText(user.getPhone());
			et_realName.setSelection(user.getRealName().length());
			break;

		}
		
	}

	@Override
	protected void init() {
		Intent intent=getIntent();
		if(intent!=null){
			alterType=intent.getIntExtra("ALTER_TYPE", 0);
		}
		user=Config.getCachedUser();
	}
	
	
	public void cacheNewUser(){
		User user=Config.getCachedUser();
		LogUtils.i("alterType::"+map.get("alterType"));
		if("realName".equals(map.get("alterType"))){
			user.setRealName(map.get("value"));
		}else if("gender".equals(map.get("alterType"))){
			user.setGender(Integer.parseInt(map.get("value")));
		}else if("qq".equals(map.get("alterType"))){
			user.setQq(map.get("value"));
		}else if("phone".equals(map.get("alterType"))){
			user.setPhone(map.get("value"));
		}
		Config.cacheUser(user);
	}

}
