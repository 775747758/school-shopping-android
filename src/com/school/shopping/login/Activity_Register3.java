package com.school.shopping.login;

import java.io.File;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import com.meg7.widget.CustomShapeImageView;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MainActivity;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.User;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.Register3Protocal;
import com.school.shopping.utils.CompressPicture;
import com.school.shopping.utils.DeviceInfo;
import com.school.shopping.utils.DialogUtils;
import com.school.shopping.utils.FileUtils;
import com.school.shopping.utils.MD5;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.MyProgressPopUpWindow;

public class Activity_Register3 extends BaseActivity {

	private EditText name_et;
	private EditText phone_tv;
	private EditText qq_tv;
	private RadioButton man_radio;
	private RadioButton woman_radio;
	private File file;
	private String username;
	private String password;
	private CustomShapeImageView portrait_imageview;
	private String city;
	private String school;
	public AlertDialog dialog = null;
	private boolean isManChecked = true;
	
	@Override
	protected void init() {
		Intent intent = getIntent();
		if (intent != null) {
			username = intent.getStringExtra("username");
			password = intent.getStringExtra("password");
			school = intent.getStringExtra("school");
		}
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_register3);

		name_et = (EditText) findViewById(R.id.name_et);
		phone_tv = (EditText) findViewById(R.id.phone_tv);
		qq_tv = (EditText) findViewById(R.id.qq_tv);
		man_radio = (RadioButton) findViewById(R.id.man_radio);
		woman_radio = (RadioButton) findViewById(R.id.woman_radio);
		portrait_imageview = (CustomShapeImageView) findViewById(R.id.portrait_imageview);

		man_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isManChecked) {
					man_radio.setChecked(false);
					woman_radio.setChecked(true);
					isManChecked = false;
				} else {
					man_radio.setChecked(true);
					woman_radio.setChecked(false);
					isManChecked = true;
				}

			}
		});
		woman_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isManChecked) {
					man_radio.setChecked(false);
					woman_radio.setChecked(true);
					isManChecked = false;
				} else {
					man_radio.setChecked(true);
					woman_radio.setChecked(false);
					isManChecked = true;
				}
			}
		});
		StringBuilder builderFileName = new StringBuilder();
		builderFileName.append("/user_").append(username.trim());
		try {
			file = new File(FileUtils.getCacheDir() + MD5.getMD5(builderFileName.toString()) + ".jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public void portrait(View view) {
		DialogUtils.selectPicture(file);
	}

	

	public void returnn(View view) {
		Intent intent = new Intent(Activity_Register3.this, Activity_Register2.class);
		startActivity(intent);
	}

	public void next(View view) {
		if (checkData()) {
			if (dialog == null) {
				dialog = MyProgressPopUpWindow.createADialog("正在提交中...");
			}
			User user=new User();
			user.setCity(Config.getCity());
			user.setGender(isManChecked==true?1:0);
			user.setId(-1);
			user.setPassword(password);
			user.setPhone(phone_tv.getText().toString().trim());
			user.setQq(qq_tv.getText().toString().trim());
			user.setRealName(name_et.getText().toString().trim());
			user.setSchool(school);
			user.setUname(username);
			user.setDeviceId(DeviceInfo.getUniqueID());
			user.setLatitude(Config.getLatitude());
			user.setLongitude(Config.getLontitude());
			user.setProvince(Config.getProvince());
			final Register3Protocal protocal=new Register3Protocal();
			protocal.setUser(user);
			protocal.setUploadFile(file);
			ThreadManager.getLongPool().execute(new Runnable() {
				
				@Override
				public void run() {
					final String result=protocal.load(-1, -1, false);
					UIUtils.runInMainThread(new Runnable() {
						
						@Override
						public void run() {
							dialog.dismiss();
							if(protocal.isNetError()){
								UIUtils.showMsg("网络错误，请检查网络！");
							}
							else if(result==null){
								UIUtils.showMsg("注册失败！");
							}else{
								Intent intent = new Intent(Activity_Register3.this,
										MainActivity.class);
								startActivity(intent);
							}
						}
					});
				}
			});
		}
	}

	public boolean checkData() {
		if (name_et.getText().toString().length() < 1 || name_et.getText().toString().length() > 10) {
			UIUtils.showMsg("您输入的昵称不符合规范，请重新输入！");
			return false;
		}
		if (phone_tv.getText().toString().length()!=11) {
			UIUtils.showMsg("您输入的手机号码不符合规范，请重新输入！");
			return false;
		}
		if (qq_tv.getText().toString().length() < 5 || qq_tv.getText().toString().length() > 15) {
			UIUtils.showMsg("您输入的QQ码不符合规范，请重新输入！");
			return false;
		}
		if(portrait_imageview.getTag()==null||!(Boolean) portrait_imageview.getTag())
		 {
			UIUtils.showMsg("请设置头像！");
			return false;
		}
		if(StringUtils.isEmpty(Config.getLontitude())||StringUtils.isEmpty(Config.getLatitude())){
			UIUtils.showMsg("请检查您的GPS是否打开！");
			return false;
		}
		if(StringUtils.isEmpty(school)){
			UIUtils.showMsg("您的学校信息有误，请返回上一页面，重新选择！");
			return false;
		}
		
		return true;
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (!FileUtils.isSDCardAvailable()) {
				Log.i("TestFile", "SD card is not avaiable/writeable right now.");
				return;
			}

			if (requestCode == MyApplication.SELECT_SELECT_PICTURE) {
				CompressPicture.getimage(file.getAbsolutePath(), file);
			}
			if (requestCode == MyApplication.SELECT_CAMERA_RESULT) {
				CompressPicture.getimage(file.getAbsolutePath(), file);
			}
			portrait_imageview.setTag(true);
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			portrait_imageview.setImageBitmap(bitmap);
			portrait_imageview.invalidate();
		}

	}
}
