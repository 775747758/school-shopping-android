package com.school.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.util.TypedValue;
import android.widget.Toast;

import com.school.shopping.login.Activity_Login;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.utils.FileUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;

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
					if (!StringUtils.isEmpty(token)) {
						goHome();
					} else {
						LogUtils.i("token null");
						goLogin();
					}
				} else {
					//保证在进入系统前，token为空，因为在注册的时候会判断token
					Config.cacheToken(null);
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
		//Intent i = new Intent(WelcomeAct.this, Activity_ShowGoods.class);
		Intent i = new Intent(WelcomeAct.this, MainActivity.class);
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
