package com.school.shopping.utils;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.showgoods.Activity_ShowGoods;

public class DialogUtils {
	public static AlertDialog selectPicture(final File file) {
		AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.getRunActivity(),
				AlertDialog.THEME_HOLO_LIGHT);
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
					BaseActivity.getRunActivity().startActivityForResult(i, MyApplication.SELECT_CAMERA_RESULT);
				} else {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_PICK);
					intent.setType("image/*");
					intent.putExtra("crop", "true");
					intent.putExtra("output", Uri.fromFile(file));
					intent.putExtra("outputFormat", "JPEG");
					BaseActivity.getRunActivity().startActivityForResult(Intent.createChooser(intent, "选择图片"),
							MyApplication.SELECT_SELECT_PICTURE);
				}
			}
		});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();
		return alertDialog;
	}

	
	
	public static AlertDialog createASearchDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(
				BaseActivity.getRunActivity(), AlertDialog.THEME_HOLO_LIGHT);
		builder.setInverseBackgroundForced(true);
		LayoutInflater inflater = LayoutInflater.from(BaseActivity.getRunActivity());
		View view = inflater.inflate(R.layout.dialog_search, null);
		final EditText search_et=(EditText)view.findViewById(R.id.search_et);
		TextView beginSearch_tv=(TextView) view.findViewById(R.id.beginSearch_tv);
		beginSearch_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword=search_et.getText().toString();
				if(StringUtils.isEmpty(keyword)){
					UIUtils.showMsg("请输入您要搜索的内容！");
				}else{
					Message message=new Message();
					message.what=Activity_ShowGoods.BEGIN_SEARCH;
					message.obj=keyword;
					Activity_ShowGoods.mHandler.sendMessage(message);
				}
			}
		});
		//messageTv.setText(message);
		AlertDialog fluentAlert = builder.create();
		Window window =  fluentAlert.getWindow();
		window.setWindowAnimations(R.style.anim_search);
		WindowManager.LayoutParams wl = window.getAttributes();
		window.setGravity(Gravity.TOP);
		fluentAlert.setCanceledOnTouchOutside(true);
		fluentAlert.show();
		fluentAlert.setContentView(view);
		fluentAlert.getWindow().setLayout(LayoutParams.FILL_PARENT, 100);
		return fluentAlert;
	}
}
