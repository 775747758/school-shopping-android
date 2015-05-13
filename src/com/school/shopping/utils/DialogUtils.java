package com.school.shopping.utils;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.school.shopping.BaseActivity;
import com.school.shopping.MyApplication;

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

}
