package com.school.shopping.view;

import com.school.shopping.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;


public class MyProgressPopUpWindow {
	
	private Activity context;
	private String message;
	private View popView;
	private PopupWindow popWin;
	
	
	
	public MyProgressPopUpWindow(Activity context, String message) {
		super();
		this.context = context;
		this.message = message;
	}



	public AlertDialog createADialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setInverseBackgroundForced(true);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view1 = inflater.inflate(R.layout.dialog_progress, null);
		TextView messageTv=(TextView)view1.findViewById(R.id.message);
		
		messageTv.setText(message);
		AlertDialog fluentAlert = builder.create();
		Window window =  fluentAlert.getWindow();
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		//fluentAlert.onWindowAttributesChanged(wl);//���õ����Χ��ɢ
		fluentAlert.setCanceledOnTouchOutside(false);
		fluentAlert.show();
		fluentAlert.setContentView(view1);
		fluentAlert.getWindow().setLayout(LayoutParams.FILL_PARENT, 100);
		return fluentAlert;
	}

}
