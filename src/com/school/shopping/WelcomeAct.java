package com.school.shopping;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import com.school.shopping.login.Activity_Login;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.showgoods.Activity_ShowGoods;
import com.school.shopping.utils.FileUtils;


public class WelcomeAct extends Activity {
	
	private boolean isFirstIn = false;
	protected String token;
	private static final int TIME = 2000;
	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GO_HOME:
				token=Config.getCachedToken();
				if(token!=null){
					goHome();
				}
				else{
					goLogin();
				}
				break;

			case GO_GUIDE:
				goGuide();
				break;
			}
			
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		init();
	}
	
	private void init(){
		SharedPreferences perPreferences = getSharedPreferences("Boot_Config", MODE_PRIVATE);
		isFirstIn = perPreferences.getBoolean("isFirstIn", true);
		if (!isFirstIn) {
			mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
		}else{
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
			Editor editor = perPreferences.edit();
			editor.putBoolean("isFirstIn", false);
			editor.commit();
			
			ThreadManager.getShortPool().execute(new Runnable() {
				@Override
				public void run() {
					FileUtils.copyFileFromAssets("city.db");
					
				}
			});
		}
		
	}
	
	private void goHome(){
		Intent i = new Intent(WelcomeAct.this,MainActivity.class);
		//Intent i = new Intent(WelcomeAct.this,Activity_ShowGoods.class);
		i.putExtra(Config.KEY_TOKEN, token);
		startActivity(i);
		finish();
	}
	private void goGuide(){
		Intent i = new Intent(WelcomeAct.this,Activity_Guide.class);
		startActivity(i);
		finish();
	}
	private void goLogin(){
		Intent i = new Intent(WelcomeAct.this,Activity_Login.class);
		startActivity(i);
		finish();
	}
	
}
