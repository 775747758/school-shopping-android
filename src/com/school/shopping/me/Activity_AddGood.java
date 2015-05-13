package com.school.shopping.me;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.Good;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.AddGoodProtocal;
import com.school.shopping.net.UpdateGoodProtocal;
import com.school.shopping.utils.CompressPicture;
import com.school.shopping.utils.FileUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.MD5;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.vo.GoodVo;

public class Activity_AddGood extends BaseActivity implements OnClickListener{

	private ImageView iv_goodpic;
	private EditText et_goodName;
	private CheckBox cb_isAdjust;
	private EditText et_price;
	private EditText et_introduction;
	private RelativeLayout rl_type;
	private TextView tv_type;
	private RelativeLayout rr_newLevel;
	private TextView tv_newLevel;
	private AlertDialog goodPicAlertDialog;
	private File file;
	private TextView num;
	private int type=-1;
	private int newlevel=-1;
	private TextView tv_menu_right;
	private GoodVo goodVo;
	private boolean isEdit;
	private final String[] types={"衣服","书籍","数码","其它"};
	private final String[] newLevels={"七成新","八成新","九成新","全新"};

	@Override
	protected void initView() {
		setContentView(R.layout.activity_addgood);
		tv_menu_right=(TextView)findViewById(R.id.tv_menu_right);
		
		tv_menu_right.setOnClickListener(this);
		iv_goodpic=(ImageView)findViewById(R.id.iv_goodpic);
		iv_goodpic.setOnClickListener(this);
		et_goodName=(EditText)findViewById(R.id.et_goodName);
		cb_isAdjust=(CheckBox)findViewById(R.id.cb_isAdjust);
		et_price=(EditText)findViewById(R.id.et_price);
		et_introduction=(EditText)findViewById(R.id.et_introduction);
		
		rl_type=(RelativeLayout)findViewById(R.id.rl_type);
		rl_type.setOnClickListener(this);
		tv_type=(TextView)findViewById(R.id.tv_type);
		rr_newLevel=(RelativeLayout)findViewById(R.id.rr_newLevel);
		rr_newLevel.setOnClickListener(this);
		tv_newLevel=(TextView)findViewById(R.id.tv_newLevel);
		num=(TextView)findViewById(R.id.num);
		
		et_introduction.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				num.setText(s.toString().length()+"/80");
			}
		});
		
		if(isEdit){
			tv_menu_right.setText("保存");
			et_goodName.setText(goodVo.getGoodName());
			if(StringUtils.isEmpty(goodVo.getIntroduction())){
				et_introduction.setText("暂时无任何简介");
			}
			else{
				et_introduction.setText(goodVo.getIntroduction());
			}
			cb_isAdjust.setChecked(goodVo.getIsAdjust()==1?true:false);
			newlevel=goodVo.getNewLevel();
			tv_newLevel.setText(newLevels[newlevel-7]);
			et_price.setText(goodVo.getPrice());
			type=goodVo.getType();
			tv_type.setText(types[type-1]);
		}
		//cb_isAdjust.isChecked();
	}

	@Override
	protected void init() {
		//intent.putExtra("from", "Activity_GoodDetail");
		Intent intent=getIntent();
		if(intent!=null){
			if("Activity_GoodDetail".equals(intent.getStringExtra("from"))){
		        Bundle bundle = intent.getExtras(); 
		        goodVo = (GoodVo)bundle.getParcelable("GoodVo");
		        isEdit=true;
			}
		}
		StringBuilder builderFileName=new StringBuilder();
		builderFileName.append("/temp").append(".jpg");
		try {
			file=new File(FileUtils.getCacheDir()+MD5.getMD5(builderFileName.toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_goodpic:
			selectPicture();
			break;
		case R.id.rl_type:
			selectType();
			break;
		case R.id.rr_newLevel:
			selectNewLevel();
			break;
		case R.id.tv_menu_right:
			if(checkGood()){
				publish();
			}
			break;
		}
	}
	

	private void publish() {
		final UpdateGoodProtocal updateGoodProtocal=new UpdateGoodProtocal();
		final AddGoodProtocal addGoodProtocal=new AddGoodProtocal();
		Good good=new Good();
		good.setGoodName(et_goodName.getText().toString().trim());
		good.setIntroduction(et_introduction.getText().toString().trim());
		good.setIsAdjust(cb_isAdjust.isChecked()==true?1:0);
		good.setNewLevel(newlevel);
		good.setPrice(et_price.getText().toString().trim());
		good.setType(type);
		good.setUid(Config.getUID());
		good.setId(goodVo.getId());
		LogUtils.i("id:::"+goodVo.getId());
		if(isEdit){
			updateGoodProtocal.setGood(good);
		}else{
			addGoodProtocal.setGood(good);
		}
		ThreadManager.getLongPool().execute(new Runnable() {
			@Override
			public void run() {
				
				boolean isSuccess=false;
				if(isEdit){
					isSuccess=updateGoodProtocal.load(-1, -1, false)==null?false:true;
				}else{
					isSuccess=addGoodProtocal.load(-1, -1, false)==null?false:true;
				}
				if(isSuccess){
					UIUtils.runInMainThread(new Runnable() {
						
						@Override
						public void run() {
							if(!isEdit){
								UIUtils.showMsg("发布成功！");
							}
							Intent intent=new Intent(Activity_AddGood.this,Activity_MyGoods.class);
							UIUtils.startActivity(intent);
						}
					});
					
				}
			}
		});
		
	}

	public void selectPicture(){
		
		try {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(Activity_AddGood.this, AlertDialog.THEME_HOLO_LIGHT);
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
						startActivityForResult(i,MyApplication.SELECT_CAMERA_RESULT);
					} else {
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_PICK);
						intent.setType("image/*");
						intent.putExtra("crop", "true");
						intent.putExtra("output", Uri.fromFile(file));
						intent.putExtra("outputFormat", "JPEG");
						startActivityForResult(Intent.createChooser(intent, "选择图片"), MyApplication.SELECT_SELECT_PICTURE);
					}
				}
			});
			goodPicAlertDialog = builder.create();
			goodPicAlertDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == MyApplication.SELECT_SELECT_PICTURE) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile", "SD card is not avaiable/writeable right now.");
					return;
				}
				CompressPicture.getimage(file.getAbsolutePath(),file);
			}
			if (requestCode == MyApplication.SELECT_CAMERA_RESULT) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile", "SD card is not avaiable/writeable right now.");
					return;
				}
				CompressPicture.getimage(file.getAbsolutePath(),file);

			}
			//isSelectPortrait=true;
			Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
			iv_goodpic.setImageBitmap(bitmap);
			iv_goodpic.invalidate();
		}

	}
	
	
	public void selectType(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Activity_AddGood.this, R.style.Dialog);
		builder.setInverseBackgroundForced(true);
		View view1 = UIUtils.inflate(R.layout.dialog_range);
		TextView tv_title=(TextView)view1.findViewById(R.id.tv_title);
		tv_title.setText("宝贝类别");
		final AlertDialog RangeAlert = builder.create();
		ListView range_lv = (ListView) view1.findViewById(R.id.range_lv);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listitem_search_range, R.id.item_tv, types);
		range_lv.setAdapter(adapter);
		range_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				tv_type.setText(types[arg2]);
				type=arg2+1;
				RangeAlert.dismiss();
			}
		});
		RangeAlert.show();
		RangeAlert.setContentView(view1);
		int width=UIUtils.getWindowWidth();
		int height=UIUtils.getWindowHeight();
		RangeAlert.getWindow().setLayout(2 * width / 3,LayoutParams.WRAP_CONTENT);
	}
	
	public void selectNewLevel(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				Activity_AddGood.this, R.style.Dialog);
		builder.setInverseBackgroundForced(true);
		View view1 = UIUtils.inflate(R.layout.dialog_range);
		TextView tv_title=(TextView)view1.findViewById(R.id.tv_title);
		tv_title.setText("新旧程度");
		final AlertDialog RangeAlert = builder.create();
		ListView range_lv = (ListView) view1.findViewById(R.id.range_lv);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listitem_search_range, R.id.item_tv, newLevels);
		range_lv.setAdapter(adapter);
		range_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				tv_newLevel.setText(types[arg2]);
				newlevel=arg2+7;
				RangeAlert.dismiss();
			}
		});
		RangeAlert.show();
		RangeAlert.setContentView(view1);
		int width=UIUtils.getWindowWidth();
		int height=UIUtils.getWindowHeight();
		RangeAlert.getWindow().setLayout(2 * width / 3,LayoutParams.WRAP_CONTENT);
	}
	
	
	public boolean checkGood(){
		String price=et_price.getText().toString().trim();
		if(StringUtils.isEmpty(price)){
			UIUtils.showMsg("您还没有设置宝贝的价格！");
			return false;
		}
		if(price.length()>1&&price.substring(0, 1).equals("0")){
			UIUtils.showMsg("请您设置正确的价格！");
			return false;
		}
		if(StringUtils.isEmpty(et_goodName.getText().toString().trim())){
			UIUtils.showMsg("您还没有设置宝贝的名称！");
			return false;
		}
		if(type==-1){
			UIUtils.showMsg("您还没有设置宝贝的类别！");
			return false;
		}
		if(newlevel==-1){
			UIUtils.showMsg("您还没有设置宝贝的新旧程度！");
			return false;
		}
		return true;
	}

}
