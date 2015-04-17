package com.school.shopping.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class MyPreferences {

	private SharedPreferences myPreferences;
	private final String USERNAME = "username";
	private final String PASSWORD = "password";
	private final String KEY_ID = "uid";
	private final String AUTO_LOGIN = "auto_login";
	private final String SAVE_PW = "save_pw";

	public void init(Context context) {
		if (null == myPreferences) {
			myPreferences = context.getSharedPreferences("user",
					Context.MODE_PRIVATE);
		}
	}

	// 保存用户名
	public void saveName(String name) {
		Editor editor = myPreferences.edit();
		String bname = new String(
				Base64.encode(name.getBytes(), Base64.DEFAULT));
		editor.putString(USERNAME, bname);
		editor.commit();
	}

	// 读取用户名
	public String getName() {
		String bName = myPreferences.getString(USERNAME, "");
		return new String(Base64.decode(bName, Base64.DEFAULT));
	}

	// 保存密码
	public void savePW(String pw) {
		Editor editor = myPreferences.edit();
		String bpw = new String(Base64.encode(pw.getBytes(), Base64.DEFAULT));
		editor.putString(USERNAME, bpw);
		editor.commit();
	}

	// 读取密码
	public String getPW() {
		String bpw = myPreferences.getString(PASSWORD, "");
		return new String(Base64.decode(bpw, Base64.DEFAULT));
	}

	// 保存用户ID
	public void saveUID(String uid) {
		Editor editor = myPreferences.edit();
		editor.putString(KEY_ID, uid);
		editor.commit();
	}

	// 读取密码
	public String getUID() {
		return myPreferences.getString(KEY_ID, "");
	}

	// 设置自动登录
	public void setAutoLogin(boolean autoLogin) {
		Editor editor = myPreferences.edit();
		editor.putBoolean(AUTO_LOGIN, autoLogin);
		editor.commit();
	}

	// 读取自动登录
	public boolean getAutoLogin() {
		return myPreferences.getBoolean(AUTO_LOGIN, true);
	}

	// 设置记住密码
	public void setSavePW(boolean save_pw) {
		Editor editor = myPreferences.edit();
		editor.putBoolean(SAVE_PW, save_pw);
		editor.commit();
	}

	// 读取记住密码
	public boolean getSavePW() {
		return myPreferences.getBoolean(SAVE_PW, true);
	}

}
