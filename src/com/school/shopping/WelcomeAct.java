package com.school.shopping;

import android.content.Intent;
import android.os.Handler;
import com.school.shopping.login.Activity_Login;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.showgoods.Activity_ShowGoods;
import com.school.shopping.utils.FileUtils;
import com.school.shopping.utils.LogUtils;

public class WelcomeAct extends BaseActivity {

	protected String token;
	private static final int TIME = 2000;
	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	private boolean isAutoLogin;

	@Override
	protected void initView() {
		setContentView(R.layout.welcome);
		initAfter();
	}

	private void initAfter() {
		if (!Config.isFirstIn()) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
			Config.setFirstIn(false);
			ThreadManager.getShortPool().execute(new Runnable() {
				@Override
				public void run() {
					FileUtils.copyFileFromAssets("city.db");
				}
			});
		}

	}

	@Override
	protected void init() {

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GO_HOME:
				token = Config.getCachedToken();
				isAutoLogin = Config.getAutoLogin();
				if (isAutoLogin) {
					if (token != null) {
						goHome();
					} else {
						LogUtils.i("token null");
						goLogin();
					}
				} else {
					LogUtils.i("no  isAutoLogin");
					goLogin();
				}
				break;

			case GO_GUIDE:
				goGuide();
				break;
			}

		};
	};

	private void goHome() {
		Intent i = new Intent(WelcomeAct.this, Activity_ShowGoods.class);
		//Intent i = new Intent(WelcomeAct.this, MainActivity.class);
		i.putExtra(Config.KEY_TOKEN, token);
		startActivity(i);
		finish();
	}

	private void goGuide() {
		Intent i = new Intent(WelcomeAct.this, Activity_Guide.class);
		startActivity(i);
		finish();
	}

	private void goLogin() {
		Intent i = new Intent(WelcomeAct.this, Activity_Login.class);
		startActivity(i);
		finish();
	}

}
