package com.school.shopping;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.school.shopping.entity.User;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.UIUtils;

public class MyApplication extends Application {

	public static final int SELECT_SELECT_PICTURE = 10;
	public static final int SELECT_CAMERA_RESULT = 11;
	public static File cacheDir;
	public static ImageLoader imageLoader;
	public static Application application;
	//获取到主线程的id
    private static int mMainTheadId ;
  //获取到主线程的handler
    private static Handler mMainThreadHandler = null;

	@Override
	public void onCreate() {
		this.mMainTheadId = android.os.Process.myTid();
		application=this;
		this.mMainThreadHandler = new Handler();
		setCachePath();// 设置缓存路径
		initUniversalImageLoader();// 初始化UniversalImageLoader
		//链接融云服务器
		connectRY();
		
		
		super.onCreate();
		
	}
	
	public static Application getApplication() {
		return application;
	}
	
	public static Handler getMainThreadHandler(){
		return mMainThreadHandler;
	}

	private  void connectRY() {

		String token = Config.getCachedToken();
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

	public static ImageLoader getImageLoader() {
		return imageLoader;
	}

	public static DisplayImageOptions getOptions() {
		DisplayImageOptions.Builder builder=new DisplayImageOptions.Builder()
		.imageScaleType(ImageScaleType.EXACTLY)
		.showStubImage(R.drawable.ic_launcher)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_launcher)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(500));
		DisplayImageOptions options = builder.build();
		return options;
	}

	public static DisplayImageOptions getNoCacheOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_launcher)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(false)// 是否緩存都內存中
				.cacheOnDisc(false)// 是否緩存到sd卡上
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				.resetViewBeforeLoading(false)// default 设置图片在加载前是否重置、复位
				.displayer(new FadeInBitmapDisplayer(500)).build();
		return options;
	}

	public static File getCachePath() {
		return cacheDir;
	}

	public static void setCachePath() {
		cacheDir = new File(Environment.getExternalStorageDirectory()
				.getAbsoluteFile()
				+ "/"
				+ UIUtils.getString(R.string.app_name));
		if (!cacheDir.exists()) {
			cacheDir.mkdir();
		}
	}

	private void initUniversalImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)// 设置当前线程的优先级
				.denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
				.diskCacheFileCount(60)// 缓存文件的最大个数
				.defaultDisplayImageOptions(getOptions())
				.threadPoolSize(3)//线程池内加载的数量
				.writeDebugLogs()
				.memoryCacheSize(2 * 1024 * 1024)// 内存缓存的最大值
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
		User user=Config.getCachedUser();
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
									Config.cacheToken(response.getString(Config.KEY_TOKEN));
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
	
	
	
	public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500); // 设置image隐藏动画500ms
					displayedImages.add(imageUri); // 将图片uri添加到集合中
				}
			}
		}
	}

}
