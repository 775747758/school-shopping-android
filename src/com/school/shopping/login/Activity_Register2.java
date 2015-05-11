package com.school.shopping.login;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.meg7.widget.CustomShapeImageView;
import com.school.shopping.Config;
import com.school.shopping.MainActivity;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.net.UploadUtil;
import com.school.shopping.utils.CompressPicture;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.MyProgressPopUpWindow;

public class Activity_Register2 extends Activity {

	private EditText name_et;
	private EditText phone_tv;
	private EditText qq_tv;
	private RadioButton man_radio;
	private RadioButton woman_radio;
	private boolean isManChecked = true;
	private File dir;
	private File file;
	private String username;
	private String password;
	private boolean isSelectPortrait;
	private CustomShapeImageView portrait_imageview;
	private int gender;
	private AlertDialog alertDialog;
	private String city;
	private String school;
	public AlertDialog dialog = null;
	private MyApplication myApp;
	public static Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register2);


		mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if(msg.what==URLProtocol.STATUS_SUCCESS){
					dialog.dismiss();
					Intent intent = new Intent(Activity_Register2.this,
							MainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				}
				else {
					 dialog.dismiss();
					 UIUtils.showMsg("注册失败");
				}
			};
		};

		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		password = intent.getStringExtra("password");
		city = intent.getStringExtra("city");
		school = intent.getStringExtra("school");

		name_et = (EditText) findViewById(R.id.name_et);
		phone_tv = (EditText) findViewById(R.id.phone_tv);
		qq_tv = (EditText) findViewById(R.id.qq_tv);
		man_radio = (RadioButton) findViewById(R.id.man_radio);
		woman_radio = (RadioButton) findViewById(R.id.woman_radio);
		portrait_imageview = (CustomShapeImageView) findViewById(R.id.portrait_imageview);

		man_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
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
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

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
		myApp = (MyApplication) getApplication(); // 获得自定义的应用程序MyApp
		file = new File(myApp.getCachePath() + "/user_" + username.trim()
				+ ".jpg");
	}

	public void portrait(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Activity_Register2.this, AlertDialog.THEME_HOLO_LIGHT);
		// AlertDialog strengthenDialog=new
		// AlertDialog.Builder(Activity_ReciteMain.this,).create();
		builder.setTitle("选择图片来源");
		builder.setItems(new String[] { "相机", "图库" },
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (file.exists()) {
							file.delete();
						}
						if (which == 0) {
							Intent i = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
							i.putExtra("output", Uri.fromFile(file));
							i.putExtra("outputFormat", "JPEG");
							startActivityForResult(i,
									myApp.SELECT_CAMERA_RESULT);
						} else {
							Intent intent = new Intent();
							intent.setAction(Intent.ACTION_PICK);
							intent.setType("image/*");
							intent.putExtra("crop", "true");
							intent.putExtra("output", Uri.fromFile(file));
							intent.putExtra("outputFormat", "JPEG");
							startActivityForResult(
									Intent.createChooser(intent, "选择图片"),
									myApp.SELECT_SELECT_PICTURE);
						}
					}
				});

		alertDialog = builder.create();
		alertDialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == myApp.SELECT_SELECT_PICTURE) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				CompressPicture.getimage(file.getAbsolutePath(), file);
			}
			if (requestCode == myApp.SELECT_CAMERA_RESULT) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				CompressPicture.getimage(file.getAbsolutePath(), file);

			}
			isSelectPortrait = true;
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			portrait_imageview.setImageBitmap(bitmap);
			portrait_imageview.invalidate();
		}

	}

	public void returnn(View view) {
		Intent intent = new Intent(Activity_Register2.this,
				Activity_Register1.class);
		startActivity(intent);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}

	public void next(View view) {

		if (name_et.getText().toString().length() < 1
				|| name_et.getText().toString().length() > 10) {
			UIUtils.showMsg("您输入的昵称不符合规范，请重新输入！");
			return;
		}
		if (phone_tv.getText().toString().length() < 1
				|| phone_tv.getText().toString().length() > 11) {
			UIUtils.showMsg("您输入的手机号码不符合规范，请重新输入！");
			return;
		}
		if (qq_tv.getText().toString().length() < 1
				|| qq_tv.getText().toString().length() > 15) {
			UIUtils.showMsg("您输入的QQ码不符合规范，请重新输入！");
			return;
		}
		if (!isSelectPortrait) {
			UIUtils.showMsg("请设置头像！");
			return;
		}
		// 男：1；女：0
		if (isManChecked) {
			gender = 1;
		} else {
			gender = 0;
		}
		// dialog=new
		dialog=MyProgressPopUpWindow.createADialog("正在提交中...");
		UploadUtil.register(getApplicationContext(),"Activity_Register2", file.getAbsolutePath()
				.toString(), URLProtocol.REGISTER, username,
				password, city, school, name_et.getText().toString(), phone_tv.getText().toString(), qq_tv.getText().toString(), gender);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

	}
}
