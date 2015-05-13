package com.school.shopping.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.meg7.widget.CustomShapeImageView;
import com.school.shopping.R;
import com.school.shopping.entity.User;
import com.school.shopping.login.Activity_School;
import com.school.shopping.me.Activity_AlterCity;
import com.school.shopping.me.Activity_AlterUserInfo;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;

public class DetailUserInfoHolder extends BaseHolder<User> implements OnClickListener{

	private CustomShapeImageView portrait;
	private TextView name_tv;
	private TextView gender_tv;
	private TextView qq_tv;
	private TextView phone_tv;
	private TextView school_tv;
	private TextView city_tv;
	private View view;
	public static final int ALTER_NAME=0;
	public static final int ALTER_GENDER=1;
	public static final int ALTER_QQ=2;
	public static final int ALTER_PHONE=3;
	public static final int ALTER_SCHOOL=4;
	
	public DetailUserInfoHolder(View view,Activity activity) {
		super(view,activity);
	}

	@Override
	public void refreshView(User data) {
		LogUtils.i("DetailUserInfo::refress");
		if(data!=null){
			name_tv.setText(data.getRealName().trim());
			gender_tv.setText(data.getGender()==0?"女":"男");
			qq_tv.setText(data.getQq().trim());
			phone_tv.setText(data.getPhone().trim());
			school_tv.setText(data.getSchool().trim());
			city_tv.setText(data.getCity().trim());
		}
	}

	@Override
	public View initView() {
		view=getRootView();
		if(view==null){
			LogUtils.i("DetailUserInfo::view为空");
			view=UIUtils.inflate(R.layout.activity_detailuserinfo);
		}
		portrait = (CustomShapeImageView)view.findViewById(R.id.portrait);
		name_tv = (TextView) view.findViewById(R.id.name_tv);
		name_tv.setOnClickListener(this);
		gender_tv = (TextView) view.findViewById(R.id.gender_tv);
		gender_tv.setOnClickListener(this);
		qq_tv = (TextView) view.findViewById(R.id.qq_tv);
		qq_tv.setOnClickListener(this);
		phone_tv = (TextView) view.findViewById(R.id.phone_tv);
		phone_tv.setOnClickListener(this);
		school_tv = (TextView) view.findViewById(R.id.school_tv);
		school_tv.setOnClickListener(this);
		city_tv=(TextView)view.findViewById(R.id.city_tv);
		city_tv.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.name_tv:
			Intent intentN=new Intent(getCurrentActivity(),Activity_AlterUserInfo.class);
			intentN.putExtra("ALTER_TYPE", ALTER_NAME);
			UIUtils.startActivity(intentN);
			break;
		case R.id.gender_tv:
			Intent intentG=new Intent(getCurrentActivity(),Activity_AlterUserInfo.class);
			intentG.putExtra("ALTER_TYPE", ALTER_GENDER);
			UIUtils.startActivity(intentG);
			break;
		case R.id.qq_tv:
			Intent intentQ=new Intent(getCurrentActivity(),Activity_AlterUserInfo.class);
			intentQ.putExtra("ALTER_TYPE", ALTER_QQ);
			UIUtils.startActivity(intentQ);
			break;
		case R.id.phone_tv:
			Intent intentP=new Intent(getCurrentActivity(),Activity_AlterUserInfo.class);
			intentP.putExtra("ALTER_TYPE", ALTER_PHONE);
			UIUtils.startActivity(intentP);
			break;
		case R.id.city_tv:
			Intent intentC=new Intent(getCurrentActivity(),Activity_AlterCity.class);
			intentC.putExtra("from", "Activity_AlertUserInfo");
			UIUtils.startActivity(intentC);
			break;
		case R.id.school_tv:
			Intent intentS=new Intent(getCurrentActivity(),Activity_School.class);
			UIUtils.startActivity(intentS);
			break;

		}
		getCurrentActivity().finish();
	}
	
	

}
