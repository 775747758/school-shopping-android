package com.school.shopping.view;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.school.shopping.BaseActivity;
import com.school.shopping.R;


public class MyProgressPopUpWindow {
	

	public static AlertDialog createADialog(String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(
				BaseActivity.getRunActivity(), AlertDialog.THEME_HOLO_LIGHT);
		builder.setInverseBackgroundForced(true);
		LayoutInflater inflater = LayoutInflater.from(BaseActivity.getRunActivity());
		View view1 = inflater.inflate(R.layout.dialog_progress, null);
		TextView messageTv=(TextView)view1.findViewById(R.id.message);
		messageTv.setText(message);
		AlertDialog fluentAlert = builder.create();
		Window window =  fluentAlert.getWindow();
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		fluentAlert.setCanceledOnTouchOutside(false);
		fluentAlert.show();
		fluentAlert.setContentView(view1);
		fluentAlert.getWindow().setLayout(LayoutParams.FILL_PARENT, 100);
		return fluentAlert;
	}

}
