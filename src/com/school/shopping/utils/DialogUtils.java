package com.school.shopping.utils;

import java.io.File;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.fragment.Fragment_Home;

public class DialogUtils {
	
	public static AlertDialog selectPicture(final File file) {
		AlertDialog.Builder builderSelectPic = new AlertDialog.Builder(BaseActivity.getRunActivity(), AlertDialog.THEME_HOLO_LIGHT);
		builderSelectPic.setTitle("选择图片来源");
		builderSelectPic.setItems(new String[] { "相机", "图库" }, new DialogInterface.OnClickListener() {

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
		AlertDialog selectDialog = builderSelectPic.create();
		selectDialog.show();
		return selectDialog;
	}

	public static AlertDialog savePic(final String filePath) {
		AlertDialog.Builder builderSavePic = new AlertDialog.Builder(BaseActivity.getRunActivity(), AlertDialog.THEME_HOLO_LIGHT);
		builderSavePic.setItems(new String[] { "保存到手机" }, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == 0) {
					FileUtils.copyFile(
							filePath,
							FileUtils.getExternalStoragePath()
									+ filePath.substring(filePath.lastIndexOf(File.separator) + 1) + ".jpg", false);
				}
			}
		});

		AlertDialog alertDialog = builderSavePic.create();
		alertDialog.show();
		return alertDialog;
	}

	public static AlertDialog createALoadingDialog(String message) {
		AlertDialog.Builder	builderLoading = new AlertDialog.Builder(BaseActivity.getRunActivity(),
					AlertDialog.THEME_HOLO_LIGHT);
		builderLoading.setInverseBackgroundForced(true);
		View view=UIUtils.inflate(R.layout.dialog_progress);
		((TextView)view.findViewById(R.id.message)).setText(message);
		AlertDialog loadingDialog = builderLoading.create();
		Window window = loadingDialog.getWindow();
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.show();
		loadingDialog.setContentView(view);
		loadingDialog.getWindow().setLayout(LayoutParams.FILL_PARENT, 100);
		return loadingDialog;
	}
	
	public static AlertDialog checkNetState(){

		final AlertDialog dialog = new AlertDialog.Builder(BaseActivity.getRunActivity()).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		dialog.getWindow().setContentView(R.layout.dialog_neterror);
		dialog.getWindow().findViewById(R.id.remain).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NetUtils.openSetting();
				dialog.cancel();
			}
		});
		dialog.getWindow().findViewById(R.id.noremain).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		return dialog;
	
	}

}
