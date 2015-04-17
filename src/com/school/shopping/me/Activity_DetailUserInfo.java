package com.school.shopping.me;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.meg7.widget.CustomShapeImageView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.login.Activity_Login;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.net.UploadUtil;
import com.school.shopping.utils.CompressPicture;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.MyProgressPopUpWindow;

public class Activity_DetailUserInfo extends Activity {

	private TextView name_tv;
	private TextView qq_tv;
	private SharedPreferences sp;
	private String userName = "";
	private File file;
	private AlertDialog alertDialog;
	private TextView sv_menu_right;
	private Editor editor;
	protected boolean isSelectBirthday;
	private String gender = "";
	private int width;
	private int height;
	private AlertDialog.Builder builder;
	protected String birthday = "";
	private AlertDialog dialog;
	private CustomShapeImageView portrait;
	private TextView gender_tv;
	private TextView school_tv;
	private TextView phone_tv;
	private boolean isUpdateProtrait;
	private MyApplication myApp;
	public static Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailuserinfo);
		
		myApp = (MyApplication) getApplication();  //获得自定义的应用程序MyApp 
		file = new File(myApp.getCachePath() + "/user_" + userName.trim() + ".jpg");
		

		Intent intent = getIntent();
		userName = intent.getStringExtra("userName");
		

		sv_menu_right = (TextView) findViewById(R.id.sv_menu_right);

		portrait = (CustomShapeImageView) findViewById(R.id.portrait);
		name_tv = (TextView) findViewById(R.id.name_tv);
		gender_tv = (TextView) findViewById(R.id.gender_tv);
		qq_tv = (TextView) findViewById(R.id.qq_tv);
		phone_tv = (TextView) findViewById(R.id.phone_tv);
		school_tv = (TextView) findViewById(R.id.school_tv);

		//dialog= new MyProgressPopUpWindow(Activity_DetailUserInfo.this, "正在查询中...").createADialog();
		//UploadUtil.loadImageVolley(this, "user_1234.jpg", portrait,dir);

		init();
		getJSONVolley(userName);
	}

	public void protrait(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Activity_DetailUserInfo.this, AlertDialog.THEME_HOLO_LIGHT);
		// AlertDialog strengthenDialog=new
		// AlertDialog.Builder(Activity_ReciteMain.this,).create();
		builder.setTitle("选择图片来源");
		builder.setItems(new String[] {"相机", "图库" },
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
							startActivityForResult(i, myApp.SELECT_CAMERA_RESULT);
						} else {
							Intent intent = new Intent();
							intent.setAction(Intent.ACTION_PICK);
							intent.setType("image/*");
							intent.putExtra("crop", "true");
							intent.putExtra("output", Uri.fromFile(file));
							intent.putExtra("outputFormat", "JPEG");
							startActivityForResult(
									Intent.createChooser(intent, "ѡ��ͼƬ"),
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
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { 
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				CompressPicture.getimage(file.getAbsolutePath(), file);
			}
			if (requestCode == myApp.SELECT_CAMERA_RESULT) {

				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { 
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				CompressPicture.getimage(file.getAbsolutePath(), file);

			}
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			portrait.setImageBitmap(bitmap);
			portrait.invalidate();
			sv_menu_right.setVisibility(View.VISIBLE);
			sv_menu_right.setClickable(true);
			isUpdateProtrait = true;
		}

	}

	/*
	 * public void name(View view) {
	 * 
	 * builder.setInverseBackgroundForced(true); LayoutInflater inflater =
	 * LayoutInflater.from(getApplicationContext()); View view1 =
	 * inflater.inflate(R.layout.dialog_newbookmark, null); TextView
	 * title=(TextView)view1.findViewById(R.id.title); title.setText("�ǳ�");
	 * Button cancel = (Button) view1.findViewById(R.id.cancel); Button certain
	 * = (Button) view1.findViewById(R.id.certain); final EditText et =
	 * (EditText) view1.findViewById(R.id.newbookmark_et);
	 * et.setHint("�������ǳ�");
	 * 
	 * cancel.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { alertDialog.cancel(); } });
	 * certain.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { alertDialog.cancel();
	 * name_tv.setText(et.getText().toString().trim());
	 * sv_menu_right.setClickable(true);
	 * sv_menu_right.setVisibility(View.VISIBLE); } });
	 * 
	 * alertDialog = builder.create(); alertDialog.getWindow().clearFlags(
	 * WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); alertDialog.show();
	 * alertDialog.setContentView(view1); alertDialog.getWindow().setLayout(7 *
	 * width / 8, 500); }
	 * 
	 * public void age(View view) { final Calendar c = Calendar.getInstance();
	 * mYear = c.get(Calendar.YEAR); mMonth = c.get(Calendar.MONTH); mDay =
	 * c.get(Calendar.DAY_OF_MONTH); View view2 =
	 * LayoutInflater.from(this).inflate( R.layout.activity_dialog, null); final
	 * AlertDialog.Builder builder = new AlertDialog.Builder(
	 * Activity_DetailUserInfo.this, AlertDialog.THEME_HOLO_LIGHT);
	 * builder.setView(view2); // builder.setTitle(mYear+"-"+"mMonth"+"-"+mDay);
	 * final AlertDialog datePickerDialog = builder.create(); DatePicker
	 * datePicker = (DatePicker) view2 .findViewById(R.id.datePicker); TextView
	 * certain = (TextView) view2.findViewById(R.id.certain);
	 * certain.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { if (isSelectBirthday) {
	 * age_tv.setText(mYear - bir_year + "");
	 * sv_menu_right.setVisibility(View.VISIBLE);
	 * sv_menu_right.setClickable(true); } datePickerDialog.dismiss(); } });
	 * datePicker.setCalendarViewShown(false); datePicker.init(mYear, mMonth,
	 * mDay, new OnDateChangedListener() {
	 * 
	 * @Override public void onDateChanged(DatePicker view, int year, int
	 * monthOfYear, int dayOfMonth) { int month=monthOfYear+1;
	 * birthday=year+"-"+month+"-"+dayOfMonth; bir_year = year; isSelectBirthday
	 * = true; } }); datePickerDialog.show(); }
	 * 
	 * public void gender(View view) {
	 * 
	 * AlertDialog.Builder builder = new
	 * AlertDialog.Builder(Activity_DetailUserInfo.this,
	 * AlertDialog.THEME_HOLO_LIGHT);
	 * 
	 * builder.setTitle("ѡ��̻�����"); builder.setItems(new String[] { "��", "Ů"
	 * }, new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) {
	 * 
	 * if (which == 0) { gender = "��"; agender_tv.setText(gender);
	 * alertDialog.dismiss(); sv_menu_right.setVisibility(View.VISIBLE);
	 * sv_menu_right.setClickable(true); } else if (which == 1) { gender = "Ů";
	 * agender_tv.setText(gender); alertDialog.dismiss();
	 * sv_menu_right.setVisibility(View.VISIBLE);
	 * sv_menu_right.setClickable(true); } } });
	 * 
	 * alertDialog = builder.create(); alertDialog.show();
	 * 
	 * 
	 * 
	 * }
	 * 
	 * public void qq(View view) { builder.setInverseBackgroundForced(true);
	 * LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
	 * View view1 = inflater.inflate(R.layout.dialog_newbookmark, null); Button
	 * cancel = (Button) view1.findViewById(R.id.cancel); Button certain =
	 * (Button) view1.findViewById(R.id.certain); TextView
	 * title=(TextView)view1.findViewById(R.id.title); title.setText("QQ�˺�");
	 * final EditText et = (EditText) view1.findViewById(R.id.newbookmark_et);
	 * et.setHint("������QQ�˺�"); cancel.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { alertDialog.cancel(); } });
	 * certain.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { alertDialog.cancel();
	 * qq_tv.setText(et.getText().toString().trim());
	 * sv_menu_right.setClickable(true);
	 * sv_menu_right.setVisibility(View.VISIBLE); } });
	 * 
	 * alertDialog = builder.create(); alertDialog.getWindow().clearFlags(
	 * WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); alertDialog.show();
	 * alertDialog.setContentView(view1); alertDialog.getWindow().setLayout(7 *
	 * width / 8, 500); }
	 */

/*	public void sv_menu_right(View view) {
		String churchName = sp.getString("churchName", "");
		String portraitUri = sp.getString("portraitUri", "");
		String password = sp.getString("password", "");
		String name = name_tv.getText().toString().trim();
		String qq = qq_tv.getText().toString().trim();
		if (birthday.equals("")) {
			birthday = sp.getString("birthday", "");
		}
		if (gender.equals("")) {
			gender = sp.getString("gender", "��");
		}
		dialog.show();
		// upload.updateData(churchName, portraitUri, userName, password, name,
		// birthday, gender, qq, isUpdateProtrait,sv_menu_right);
	}*/

	public void back(View view) {
		/*
		 * Intent intent = new Intent(Activity_DetailUserInfo.this,
		 * MainActivity.class); intent.putExtra("from",2);
		 * startActivity(intent); finish();
		 * overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		 */
	}
    public void init(){
    	URLParam param = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			param.addParam("filename", "user_"+userName+".png");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		myApp.getImageLoader().displayImage(param.getQueryStr(), portrait,myApp.getNoCacheOptions());
    }
  //加载用户详细信息
  		public void getJSONVolley(final String uname) {
  			RequestQueue requestQueue = Volley.newRequestQueue(this);
  			String JSONDateUrl = URLProtocol.LOAD_USER_INFO;
  			URLParam param=new URLParam(JSONDateUrl);
  			try {
  				param.addParam("uname",uname);	
  			} catch (UnsupportedEncodingException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  			Log.i("ert", param.getQueryStr());
  			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
  					Request.Method.GET, param.getQueryStr(), null,
  					new Response.Listener<JSONObject>() {
  						public void onResponse(JSONObject response) {
  							

  							try {
								name_tv.setText(response.getString("realName"));
								if(response.getInt("gender")==1){
									gender_tv.setText("男");
								}
								else{
									gender_tv.setText("女");
								}
								qq_tv.setText(response.getString("qq"));
								phone_tv.setText(response.getString("phone"));
								school_tv.setText(response.getString("school"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
  							UIUtils.showMsg("加载完毕");
  						}
  					}, new Response.ErrorListener() {
  						public void onErrorResponse(
  								com.android.volley.VolleyError arg0) {
  							//dialog.dismiss();
  							UIUtils.showMsg("服务器出现问题，我们会尽快解决");
  						}
  					});
  			requestQueue.add(jsonObjectRequest);
  		}
  		
    
	

}
