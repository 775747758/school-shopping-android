package com.school.shopping;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.school.shopping.data.MyPreferences;
import com.school.shopping.entity.User;
import com.school.shopping.login.Activity_Login;
import com.school.shopping.login.Activity_Register1;
import com.school.shopping.login.Activity_Register3;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.DeviceInfo;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

public class MyApplication extends Application {

	public int SELECT_SELECT_PICTURE = 10;
	public int SELECT_CAMERA_RESULT = 11;
	public File cacheDir;
	public ImageLoader imageLoader;
	public static Application application;
	//获取到主线程的id
    private static int mMainTheadId ;
  //获取到主线程的handler
    private static Handler mMainThreadHandler = null;

	@Override
	public void onCreate() {
		setCachePath();// 设置缓存路径
		initUniversalImageLoader();// 初始化UniversalImageLoader
		//链接融云服务器
		connectRY();
		application=this;
		this.mMainTheadId = android.os.Process.myTid();
		super.onCreate();
		
	}
	
	public static Application getApplication() {
		return application;
	}
	
	public static Handler getMainThreadHandler(){
		return mMainThreadHandler;
	}

	public void connectRY() {

		String token = Config.getCachedToken(getApplicationContext());
		//String token="PjeUvoPUf9pWr6RnkgnzZXq9xLh/oMx7amtlqs5tbs5dB2k5apj+lhSbsRBGT9HLiJM0xkkCeSlc1mms+SmncQ==";
		if (token != null) {
			// 初始化融云
			RongIM.init(this);
			try {
				RongIM.connect(token, new RongIMClient.ConnectCallback() {
					@Override
					public void onSuccess(String s) {
						// 此处处理连接成功。
						Log.d("Connect:", "Login successfully.");
					}

					@Override
					public void onError(ErrorCode errorCode) {
						// 此处处理连接错误。
						// 错误的令牌（Token），Token 解析失败，重新向身份认证服务器获取 Token。
						if (errorCode.getValue() == 2004) {
							getTokenFromServer();
						}
						Log.d("Connect:", "Login failed.");
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public DisplayImageOptions getOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_launcher)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.displayer(new RoundedBitmapDisplayer(20)).build();
		return options;
	}

	public DisplayImageOptions getNoCacheOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_launcher)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(false)// 是否緩存都內存中
				.cacheOnDisc(false)// 是否緩存到sd卡上
				.displayer(new RoundedBitmapDisplayer(20)).build();
		return options;
	}

	public File getCachePath() {
		return cacheDir;
	}

	public void setCachePath() {
		cacheDir = new File(Environment.getExternalStorageDirectory()
				.getAbsoluteFile()
				+ "/"
				+ getResources().getString(R.string.app_name));
		if (!cacheDir.exists()) {
			cacheDir.mkdir();
		}
	}

	public void initUniversalImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
				.denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
				.discCacheFileCount(60)// 缓存文件的最大个数
				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
				.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	private void getTokenFromServer() {
		Log.i("code","执行");
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		String JSONDateUrl = URLProtocol.GET_TOKEN;
		URLParam param = new URLParam(JSONDateUrl);
		User user=Config.getCachedUser(getApplicationContext());
		if(user!=null){
			param.addParam("id",user.getId());
			Log.i("ert", param.getQueryStr());
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
					Request.Method.GET, param.getQueryStr(), null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {
							try {
								if(response.getString("code").equals("1")){
									System.out.println(response.getString(Config.KEY_TOKEN));
									Log.i("code", response.getString(Config.KEY_TOKEN));
									Config.cacheToken(getApplicationContext(), response.getString(Config.KEY_TOKEN));
									connectRY();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(
								com.android.volley.VolleyError arg0) {
							Log.i("code","失败");
						}
					});
			requestQueue.add(jsonObjectRequest);
		}
		else{
			Log.i("code","无user");
		}
	}

	public static int getMainThreadId(){
		return mMainTheadId;
	}
	
	

}
