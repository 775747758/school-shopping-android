package com.school.shopping;

import com.school.shopping.entity.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class Config {
	
	//缓存文件名
	public static final String APP_ID = "com.school.shopping";
	public static final String USER_INFO = "user_info";
	

	public static final String KEY_TOKEN = "token";
	private static final String USERNAME = "uname";
	private static final String PASSWORD = "password";
	private static final String KEY_ID = "id";
	private static final String AUTO_LOGIN = "auto_login";
	private static final String SAVE_PW = "save_pw";

	public static String getCachedToken(Context context) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_TOKEN, null);
	}

	public static void cacheToken(Context context, String token) {
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(KEY_TOKEN, token);
		e.commit();
	}

	public static void cacheUser(Context context, User user) {
		Editor e = context
				.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit();
		e.putInt("id", user.getId());
		e.putString("uname", user.getUname());
		e.putString("password", user.getPassword());
		e.putString("phone", user.getPhone());
		e.putString("realName", user.getRealName());
		e.putString("rank", user.getRank());
		e.putString("qq", user.getQq());
		e.putString("school", user.getSchool());
		e.putString("city", user.getCity());
		e.putInt("gender", user.getGender());
		e.commit();
	}

	public static User getCachedUser(Context context) {
		User user = new User();
		SharedPreferences sp = context.getSharedPreferences(USER_INFO,
				Context.MODE_PRIVATE);
		user.setGender(sp.getInt("gender", -1));
		user.setId(sp.getInt("id", -1));
		user.setPassword(sp.getString("password", ""));
		user.setPhone(sp.getString("phone", ""));
		user.setQq(sp.getString("qq", ""));
		user.setRank(sp.getString("rank", ""));
		user.setRealName(sp.getString("realName", ""));
		user.setCity(sp.getString("city", ""));
		user.setSchool(sp.getString("school", ""));
		user.setUname(sp.getString("uname", ""));
		if (sp.getInt("id", -1) == -1) {
			return null;
		}
		return user;
	}

	// 保存用户名
	public static void saveUname(Context context, String uname) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putString(USERNAME, uname);
		editor.commit();
	}

	// 读取用户名
	public static String getUname(Context context) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		String bName = myPreferences.getString(USERNAME, "");
		return bName;
	}

	// 保存密码
	public static void savePW(Context context, String pw) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putString(USERNAME, pw);
		editor.commit();
	}

	// 读取密码
	public static String getPW(Context context) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		String bpw = myPreferences.getString(PASSWORD, "");
		return bpw;
	}

	// 保存用户ID
	public static void saveUID(Context context, int uid) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putInt(KEY_ID, uid);
		editor.commit();
	}

	// 读取密码
	public static int getUID(Context context) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		return myPreferences.getInt(KEY_ID, -1);
	}

	// 设置自动登录
	public static void setAutoLogin(Context context, boolean autoLogin) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putBoolean(AUTO_LOGIN, autoLogin);
		editor.commit();
	}

	// 读取自动登录
	public static boolean getAutoLogin(Context context) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		return myPreferences.getBoolean(AUTO_LOGIN, true);
	}

	// 设置记住密码
	public static void setSavePW(Context context, boolean save_pw) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putBoolean(SAVE_PW, save_pw);
		editor.commit();
	}

	// 读取记住密码
	public static boolean getSavePW(Context context) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		return myPreferences.getBoolean(SAVE_PW, true);
	}
	
	public static void remoceCache(Context context){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(KEY_TOKEN, null);
		e.commit();
		
		
	}
}
