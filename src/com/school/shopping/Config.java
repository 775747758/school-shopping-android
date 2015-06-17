package com.school.shopping;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.school.shopping.entity.User;
import com.school.shopping.utils.FileUtils;
import com.school.shopping.utils.MD5;
import com.school.shopping.utils.UIUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Base64;
import android.widget.Toast;

public class Config {
	
	//缓存文件名
	public static final String APP_ID = "com.school.shopping";
	public static final String USER_INFO = "user_info";
	

	public static final String KEY_TOKEN = "token";
	public static final String USERNAME = "uname";
	public static final String PASSWORD = "password";
	public static final String KEY_ID = "id";
	public static final String AUTO_LOGIN = "auto_login";
	public static final String SAVE_PW = "save_pw";
	public static final String FIRST_IN = "isFirstIn";
	public static final String PROVINCE = "province";
	public static final String CITY = "city";
	public static final String LONGTITUDE = "longtitude";
	public static final String LATITUDE = "latitude";
	public static final String ONECITY = "onecity";
	
	public static final String NET_ERROR = "net_error";
	
	public static final Context context = UIUtils.getContext();
	
	
	public static boolean isFirstIn() {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getBoolean(FIRST_IN, true);
	}
	
	public static void setFirstIn(boolean isFirstIn){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putBoolean(FIRST_IN, isFirstIn);
		e.commit();
	}
	
	public static boolean isOneCity() {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getBoolean(ONECITY, false);
	}
	
	public static void setOneCity(boolean isOneCity){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putBoolean(ONECITY, isOneCity);
		e.commit();
	}
	
	public static String getProvince() {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(PROVINCE, null);
	}
	
	public static void saveProvince(String province){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(PROVINCE, province);
		e.commit();
	}
	
	public static String getCity() {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(CITY, null);
	}
	
	public static void saveCity(String city){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(CITY, city);
		e.commit();
	}
	
	public static String getLontitude() {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(LONGTITUDE, null);
	}
	
	public static void saveLontitude(String longtitude){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(LONGTITUDE, longtitude);
		e.commit();
	}
	
	public static String getLatitude() {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(LATITUDE, null);
	}
	
	public static void saveLatitude(String latitude){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(LATITUDE, latitude);
		e.commit();
	}

	public static String getCachedToken() {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_TOKEN, null);
	}

	public static void cacheToken(String token) {
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(KEY_TOKEN, token);
		e.commit();
	}

	public static void cacheUser(User user) {
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
		e.putString("latitude", user.getLatitude());
		e.putString("longitude", user.getLongitude());
		e.putString("province", user.getProvince());
		e.putInt("isShowPhone", user.getIsShowPhone());
		e.putInt("isShowQq", user.getIsShowQq());
		e.commit();
	}

	public static User getCachedUser() {
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
		user.setLatitude(sp.getString("latitude", ""));
		user.setLongitude(sp.getString("longitude", ""));
		user.setProvince(sp.getString("province", ""));
		user.setIsShowPhone(sp.getInt("isShowPhone",0));
		user.setIsShowQq(sp.getInt("isShowQq",0));
		/*if (sp.getInt("id", -1) == -1) {
			return null;
		}*/
		return user;
	}

	// 保存用户名
	public static void saveUname(String uname) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putString(USERNAME, uname);
		editor.commit();
	}

	// 读取用户名
	public static String getUname() {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		String bName = myPreferences.getString(USERNAME, "");
		return bName;
	}

	// 保存密码
	public static void savePW(String pw) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putString(PASSWORD, pw);
		editor.commit();
	}

	// 读取密码
	public static String getPW() {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		String bpw = myPreferences.getString(PASSWORD, "");
		return bpw;
	}

	// 保存用户ID
	public static void saveUID(int uid) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putInt(KEY_ID, uid);
		editor.commit();
	}

	// 读取密码
	public static int getUID() {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		return myPreferences.getInt(KEY_ID, -1);
	}

	// 设置自动登录
	public static void setAutoLogin(boolean autoLogin) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putBoolean(AUTO_LOGIN, autoLogin);
		editor.commit();
	}

	// 读取自动登录
	public static boolean getAutoLogin() {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		return myPreferences.getBoolean(AUTO_LOGIN, true);
	}

	// 设置记住密码
	public static void setSavePW(boolean save_pw) {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		Editor editor = myPreferences.edit();
		editor.putBoolean(SAVE_PW, save_pw);
		editor.commit();
	}

	// 读取记住密码
	public static boolean getSavePW() {
		SharedPreferences myPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		return myPreferences.getBoolean(SAVE_PW, true);
	}
	
	public static void removeCache(String cacheName){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(cacheName, null);
		e.commit();
	}
	
	public static void removeAppCache(){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(KEY_TOKEN, null);
		e.putString(PROVINCE, null);
		e.putString(CITY, null);
		e.putBoolean(ONECITY, false);
		e.commit();
	}
	
	public static void removeSDCache(String cacheName){
		File file;
		try {
			file = new File(FileUtils.getCacheDir()+MD5.getMD5(cacheName));
			if(FileUtils.isSDCardAvailable()){
				if(file.exists()){
					if(file.isFile()){
						file.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
    
}
