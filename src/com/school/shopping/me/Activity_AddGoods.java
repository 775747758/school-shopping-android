package com.school.shopping.me;

import java.io.File;
import java.io.UnsupportedEncodingException;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.net.UploadUtil;
import com.school.shopping.utils.CompressPicture;
import com.school.shopping.utils.UIUtils;

public class Activity_AddGoods extends Activity {
	
	private TextView room_tv;
	private TextView type_tv;
	private TextView price_tv;
	private TextView level_tv;
	private EditText room_et;
	private EditText type_et;
	private EditText price_et;
	private EditText level_et;
	private EditText goodName_et;
	private AlertDialog alertDialog;
	public static Handler mHandler;
	private File file;
	private ImageView goodbg_iv;
	private MyApplication myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_addgoods);
		myApp = (MyApplication) getApplication();  //获得自定义的应用程序MyApp 
		
		mHandler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				if(msg.what==URLProtocol.STATUS_SUCCESS){
					UIUtils.showMsg("上传成功");
				}
				else {
					UIUtils.showMsg("注册失败");
				}
			};
		};
		
		file = new File(myApp.getCachePath()+ "/good_" + "13-13" + ".jpg");
		
		room_tv=(TextView)findViewById(R.id.room_tv);
		type_tv=(TextView)findViewById(R.id.type_tv);
		price_tv=(TextView)findViewById(R.id.price_tv);
		level_tv=(TextView)findViewById(R.id.level_tv);
		
		room_et=(EditText)findViewById(R.id.room_et);
		type_et=(EditText)findViewById(R.id.type_et);
		price_et=(EditText)findViewById(R.id.price_et);
		level_et=(EditText)findViewById(R.id.level_et);
		goodName_et=(EditText)findViewById(R.id.goodName_et);
		
		goodbg_iv=(ImageView)findViewById(R.id.goodbg_iv);
		
		room_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				room_tv.setText(s.toString());
				
			}
		});
		
		type_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				type_tv.setText(s.toString());
				
			}
		});
		
		price_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				price_tv.setText(s.toString());
				
			}
		});
		
		level_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				level_tv.setText(s.toString());
				
			}
		});
		
		
		
	}
	
	public void submit(View view){
		 //加载用户详细信息
		addAGood(34);
	}
	
	public void selectPicture(View view){
		AlertDialog.Builder builder = new AlertDialog.Builder(Activity_AddGoods.this, AlertDialog.THEME_HOLO_LIGHT);
		// AlertDialog strengthenDialog=new
		// AlertDialog.Builder(Activity_ReciteMain.this,).create();
		builder.setTitle("选择图片来源");
		builder.setItems(new String[] { "相机", "图库" }, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (file.exists()) {
					file.delete();
				}
				if (which == 0) {
					Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					i.putExtra("output", Uri.fromFile(file));
					i.putExtra("outputFormat", "JPEG");
					startActivityForResult(i,myApp.SELECT_CAMERA_RESULT);
				} else {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_PICK);
					intent.setType("image/*");
					intent.putExtra("crop", "true");
					intent.putExtra("output", Uri.fromFile(file));
					intent.putExtra("outputFormat", "JPEG");
					startActivityForResult(Intent.createChooser(intent, "选择图片"), myApp.SELECT_SELECT_PICTURE);
				}
			}
		});

		alertDialog = builder.create();
		alertDialog.show();
	}
	
	public void addAGood(int id){
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		String JSONDateUrl = URLProtocol.ADD_A_GOOD;
		URLParam param=new URLParam(JSONDateUrl);
		
		
			try {
				
				param.addParam("type",type_et.getText().toString().trim());	
				param.addParam("price",price_et.getText().toString().trim());	
				param.addParam("newLevel",level_et.getText().toString().trim());
				param.addParam("goodName",goodName_et.getText().toString().trim());
				//中文要编码
				param.addParam_Encode("introduction","很好的商品哦");
				int isAdjust=1;
				if("是".equals(room_et.getText().toString().trim())){
					isAdjust=1;
				}
				else{
					isAdjust=0;
				}
				param.addParam("isAdjust",isAdjust);
				//param.addParam("id",id);
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
								//UploadUtil.uploadFile("Activity_AddGoods",file.getAbsolutePath().toString(), URLProtocol.UPLOAD_DOOD_PIC);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						UIUtils.showMsg("提交完毕");
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == myApp.SELECT_SELECT_PICTURE) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile", "SD card is not avaiable/writeable right now.");
					return;
				}
				CompressPicture.getimage(file.getAbsolutePath(),file);
			}
			if (requestCode == myApp.SELECT_CAMERA_RESULT) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile", "SD card is not avaiable/writeable right now.");
					return;
				}
				CompressPicture.getimage(file.getAbsolutePath(),file);

			}
			//isSelectPortrait=true;
			Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
			goodbg_iv.setImageBitmap(bitmap);
			goodbg_iv.invalidate();
		}

	}
	
	
	
}
