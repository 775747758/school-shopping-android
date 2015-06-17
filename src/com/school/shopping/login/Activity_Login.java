package com.school.shopping.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MainActivity;
import com.school.shopping.R;
import com.school.shopping.entity.User;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.LoginProtocal;
import com.school.shopping.utils.DialogUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.MD5;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;

public class Activity_Login extends BaseActivity {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private AlertDialog dialog;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_login);
		usernameEditText = (EditText) findViewById(R.id.username);
		if(!StringUtils.isEmpty(Config.getUname())){
			usernameEditText.setText(Config.getUname());
			usernameEditText.setSelection(Config.getUname().length());
		}
		passwordEditText = (EditText) findViewById(R.id.password);
	}

	@Override
	protected void init() {
		Intent intent = getIntent();
		if(intent!=null){
			if (intent.getBooleanExtra("isNotice", false)) {
				UIUtils.showMsg("您的账号已经在其它设备登陆，请重新登陆！");
			}
		}
	}

	public void register(View view) {
		Intent intent = new Intent(Activity_Login.this, Activity_Register1.class);
		startActivity(intent);
		finish();
	}

	public void next(View view) {
		String username = usernameEditText.getText().toString().trim();
		String password = passwordEditText.getText().toString().trim();
		boolean isCorrect = checkLoginInfo(username, password);
		if (isCorrect) {
			if(dialog==null){
				dialog = DialogUtils.createALoadingDialog("正在登陆中...");
			}else{
				dialog.show();
			}
			final LoginProtocal protocal = LoginProtocal.getInstance(username, MD5.getMD5(password));
			protocal.setUname(username);
			protocal.setPassword(MD5.getMD5(password));
			ThreadManager.getLongPool().execute(new Runnable() {

				@Override
				public void run() {
					final User user = protocal.load(-1, -1, false);
					UIUtils.runInMainThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();
							if(protocal.isNetError()){
								UIUtils.showMsg("您没有连接网络，请查看网络！");
							}else{
								if (user != null) {
									
									Config.cacheUser(user);
									Intent intent = new Intent(Activity_Login.this, MainActivity.class);
									UIUtils.startActivity(intent);
									finish();
								} else {
									UIUtils.showMsg("用户名或密码错误，请重新输入！");
								}
							}
							
						}
					});

				}
			});
		}
	}

	public boolean checkLoginInfo(String username, String password) {
		if (StringUtils.isEmpty(username)) {
			UIUtils.showMsg("用户名不能为空！");
			return false;
		}
		if (StringUtils.isEmpty(password)) {
			UIUtils.showMsg("密码不能为空！");
			return false;
		}
		if (username.length() < 6 || username.length() > 12) {
			UIUtils.showMsg("您输入的用户名不符合规范，请重新输入！");
			return false;
		}
		if (password.length() < 6 || username.length() > 12) {
			UIUtils.showMsg("您输入的密码不符合规范，请重新输入！");
			return false;
		}
		return true;
	}

}
