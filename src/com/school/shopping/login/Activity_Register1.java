package com.school.shopping.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.Register1Protocal;
import com.school.shopping.utils.DialogUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.MD5;
import com.school.shopping.utils.UIUtils;

public class Activity_Register1 extends BaseActivity {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private String username;
	private String password;
	private AlertDialog dialog;
	
	@Override
	protected void initView() {
		setContentView(R.layout.activity_register1);
		usernameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);
	}
	
	public void previous(View view){
		Intent intent=new Intent(Activity_Register1.this,Activity_Login.class);
		startActivity(intent);
		finish();
	}
	
	public void next(View view) {
		username = usernameEditText.getText().toString().trim();
		LogUtils.i("username11:"+username);
		password = passwordEditText.getText().toString().trim();
		if (username.length() < 6 || username.length() > 16) {
			UIUtils.showMsg("您输入的用户名不符合规范，请重新输入！");
			return;
		}
		if (password.length() < 6 || password.length() > 16) {
			UIUtils.showMsg( "您输入的密码不符合规范，请重新输入！");
			return;
		}

		final Register1Protocal protocal = Register1Protocal.getInstance(username);
		//因为采用单列模式，但是参数变化，取得的还是以前创建的protocal，所以为了更新username就要再次调用此方法
		protocal.setUname(username);
		if(dialog==null){
			dialog = DialogUtils.createALoadingDialog("正在验证中...");
		}
		ThreadManager.getLongPool().execute(new Runnable() {

			@Override
			public void run() {
				final String result = protocal.load(-1, -1, false);
				UIUtils.runInMainThread(new Runnable() {
					@Override
					public void run() {
						dialog.dismiss();
						if(protocal.isNetError()){
							UIUtils.showMsg("您没有连接网络，请查看网络！");
						}else{
							if (result != null) {
								Intent intent = new Intent(Activity_Register1.this,
										Activity_Register2.class);
								Config.saveUname(username);
								Config.savePW(MD5.getMD5(password));
								startActivity(intent);
								finish();
							} else {
								UIUtils.showMsg("此账号已被注册！请重新注册！");
							}
						}
						
					}
				});

			}
		});
	}

	@Override
	protected void init() {}
}
