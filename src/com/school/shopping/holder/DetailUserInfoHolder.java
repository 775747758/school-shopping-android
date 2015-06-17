package com.school.shopping.holder;

import java.io.File;
import java.io.UnsupportedEncodingException;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meg7.widget.CustomShapeImageView;
import com.school.shopping.BaseActivity;
import com.school.shopping.MainActivity;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.User;
import com.school.shopping.login.Activity_School;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.me.Activity_AlterCity;
import com.school.shopping.me.Activity_AlterUserInfo;
import com.school.shopping.net.AlterUserPortraitProtocal;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.CompressPicture;
import com.school.shopping.utils.DialogUtils;
import com.school.shopping.utils.FileUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;
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
	private File file;
	private RelativeLayout protrait_rl;
	private ImageView iv_back;
	public Bitmap bitmap;
	private LinearLayout ll_realName;
	private LinearLayout ll_gender;
	private LinearLayout ll_area;
	private LinearLayout ll_qq;
	private LinearLayout ll_phone;
	private LinearLayout ll_school;
	public static final int ALTER_NAME=0;
	public static final int ALTER_GENDER=1;
	public static final int ALTER_QQ=2;
	public static final int ALTER_PHONE=3;
	public static final int ALTER_SCHOOL=4;
	
	public DetailUserInfoHolder(View view) {
		super(view);
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
			if(StringUtils.isEmpty(data.getCity().trim())||"null".equals(data.getCity().trim())){
				city_tv.setText(data.getProvince().trim());
			}else{
				city_tv.setText(data.getProvince().trim()+" "+data.getCity());
			}
			
			URLParam paramUser = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
			try {
				paramUser.addParam("filename", "user_" + data.getUname() + ".jpg");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			StringBuilder builderFileName = new StringBuilder();
			builderFileName.append("/user_").append(data.getUname().trim());
			try {
				file = new File(FileUtils.getCacheDir() + builderFileName.toString() + ".jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
			//先将之前的缓存清理掉
			MyApplication.deleteUILCache(paramUser.getQueryStr());
			MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(), portrait,MyApplication.getNoCacheOptions());
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
		gender_tv = (TextView) view.findViewById(R.id.gender_tv);
		qq_tv = (TextView) view.findViewById(R.id.qq_tv);
		phone_tv = (TextView) view.findViewById(R.id.phone_tv);
		school_tv = (TextView) view.findViewById(R.id.school_tv);
		city_tv=(TextView)view.findViewById(R.id.city_tv);
		iv_back=(ImageView)view.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		
		protrait_rl=(RelativeLayout)view.findViewById(R.id.protrait_rl);
		protrait_rl.setOnClickListener(this);
		
		ll_realName=(LinearLayout)view.findViewById(R.id.ll_realName);
		ll_realName.setOnClickListener(this);
		
		ll_gender=(LinearLayout)view.findViewById(R.id.ll_gender);
		ll_gender.setOnClickListener(this);
		
		ll_area=(LinearLayout)view.findViewById(R.id.ll_area);
		ll_area.setOnClickListener(this);
		
		ll_qq=(LinearLayout)view.findViewById(R.id.ll_qq);
		ll_qq.setOnClickListener(this);
		
		ll_phone=(LinearLayout)view.findViewById(R.id.ll_phone);
		ll_phone.setOnClickListener(this);
		
		ll_school=(LinearLayout)view.findViewById(R.id.ll_school);
		ll_school.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_realName:
			Intent intentN=new Intent(BaseActivity.getRunActivity(),Activity_AlterUserInfo.class);
			intentN.putExtra("ALTER_TYPE", ALTER_NAME);
			UIUtils.startActivity(intentN);
			BaseActivity.finishRunActivity();
			break;
		case R.id.ll_gender:
			Intent intentG=new Intent(BaseActivity.getRunActivity(),Activity_AlterUserInfo.class);
			intentG.putExtra("ALTER_TYPE", ALTER_GENDER);
			UIUtils.startActivity(intentG);
			BaseActivity.finishRunActivity();
			break;
		case R.id.ll_qq:
			Intent intentQ=new Intent(BaseActivity.getRunActivity(),Activity_AlterUserInfo.class);
			intentQ.putExtra("ALTER_TYPE", ALTER_QQ);
			UIUtils.startActivity(intentQ);
			BaseActivity.finishRunActivity();
			break;
		case R.id.ll_phone:
			Intent intentP=new Intent(BaseActivity.getRunActivity(),Activity_AlterUserInfo.class);
			intentP.putExtra("ALTER_TYPE", ALTER_PHONE);
			UIUtils.startActivity(intentP);
			BaseActivity.finishRunActivity();
			break;
		case R.id.ll_area:
			Intent intentC=new Intent(BaseActivity.getRunActivity(),Activity_AlterCity.class);
			UIUtils.startActivity(intentC);
			BaseActivity.finishRunActivity();
			break;
		case R.id.ll_school:
			Intent intentS=new Intent(BaseActivity.getRunActivity(),Activity_School.class);
			UIUtils.startActivity(intentS);
			BaseActivity.finishRunActivity();
			break;
		case R.id.protrait_rl:
			DialogUtils.selectPicture(file);
			break;
		case R.id.iv_back:
			Intent intent=new Intent(BaseActivity.getRunActivity(),MainActivity.class);
			UIUtils.startActivity(intent);
			BaseActivity.finishRunActivity();
			break;
		}
		
	}

	/*
	 * 将Activity_DetailUserInfo中的onActivityResult转移到了这里
	 * 处理选择图片之后的回调函数
	 */
	public void onActivityResult(int requestCode) {
		//如果file=null说明网络出现问题
		if(file==null){
			UIUtils.showMsg("网络错误，请检查网络！");
		}else{
			if (requestCode == MyApplication.SELECT_SELECT_PICTURE) {
				CompressPicture.getimage(file.getAbsolutePath(), file);
			}
			if (requestCode == MyApplication.SELECT_CAMERA_RESULT) {
				CompressPicture.getimage(file.getAbsolutePath(), file);
			}
			final AlterUserPortraitProtocal protocal=AlterUserPortraitProtocal.getInstance();
			protocal.setUploadFile(file);
			ThreadManager.getLongPool().execute(new Runnable() {
				
				@Override
				public void run() {
					protocal.load(-1, -1, false);
					UIUtils.runInMainThread(new Runnable() {
						
						@Override
						public void run() {
							//dialog.dismiss();
						}
					});
				}
			});
			portrait.setTag(true);
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			portrait.setImageBitmap(bitmap);
			portrait.invalidate();
		}
	}
}
