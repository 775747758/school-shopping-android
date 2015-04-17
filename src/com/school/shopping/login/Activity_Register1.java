package com.school.shopping.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.school.shopping.R;
import com.school.shopping.utils.UIUtils;

public class Activity_Register1 extends Activity {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private String username;
	private String password;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register1);

		usernameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
	}

	public void next(View view) {

		username = usernameEditText.getText().toString().trim();
		password = passwordEditText.getText().toString().trim();
		if (username.length() < 6 || username.length() > 16) {
			UIUtils.showMsg("您输入的用户名不符合规范，请重新输入！");
			usernameEditText.setText("");
			return;
		}
		if (password.length() < 6 || password.length() > 16) {
			UIUtils.showMsg( "您输入的密码不符合规范，请重新输入！");
			passwordEditText.setText("");
			return;
		}

		Intent intent = new Intent(Activity_Register1.this,
				Activity_Register3.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		startActivity(intent);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

	}

	
}
