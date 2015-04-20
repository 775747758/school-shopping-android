package com.school.shopping.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.cache.MD5FileNameGenerator;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.school.shopping.entity.Good;
import com.school.shopping.fragment.Fragment_Home;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.utils.FileUtils;
import com.school.shopping.utils.IOUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.MD5;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

public abstract class BaseProtocol<T> {
	public static final String cachePath = "";
	private RequestQueue requestQueue;
	
	private boolean isFromCache;
	/** 加载协议 
	 * @param isFromCache */
	public T load(int startIndex, int lastIndex, boolean isFromCache) {
		
		this.isFromCache=isFromCache;
		
		SystemClock.sleep(1000);// 休息1秒，防止加载过快，看不到界面变化效果
		
		String json = null;
		//如果是加载更多直接从网络获取
		if(!isFromCache){
			LogUtils.i("加载更多，所以直接从网络获取");
			json = loadFromNet(startIndex, lastIndex);
		}else{
			// 1.从本地缓存读取数据，查看缓存时间
			json = loadFromLocal();
			// 2.如果缓存时间过期，从网络加载
			if (TextUtils.isEmpty(json)) {
				
				json = loadFromNet(startIndex, lastIndex);
				if (!TextUtils.isEmpty(json)) {
					// 3.把数据保存到本地保存到本地
					saveToLocal(json);
				} else {
					// 网络出错
				}
			}
		}
		
		if (!TextUtils.isEmpty(json)) {
			T list = parseJson(json);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

	/** 从本地加载协议 */
	protected String loadFromLocal() {
		
		LogUtils.i("从缓冲加载");
		String path = FileUtils.getCacheDir();
		File file=null;
		try {
			file = new File(path
					+ MD5.getMD5(getKey()));
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();// 第一行是时间
			Long time = Long.valueOf(line);
			if (time > System.currentTimeMillis()) {// 如果时间未过期
				StringBuilder sb = new StringBuilder();
				String result;
				while ((result = reader.readLine()) != null) {
					sb.append(result);
				}
				return sb.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			LogUtils.e(e);
			return null;
		} finally {
			IOUtils.close(reader);
		}
	}

	/** 从网络加载协议 */
	protected String loadFromNet(int startIndex, int lastIndex) {
		LogUtils.i("从网络加载");
		String json = null;
		String JSONDateUrl = getKey();
		// RequestParams requestParams=new RequestParams();
		URLParam param = new URLParam(JSONDateUrl);
		param.addParam("startIndex", startIndex);// 读取数据的开始位置
		param.addParam("lastIndex", lastIndex);// 读取数据的最末位置
		HttpUtils httpUtils = new HttpUtils();
		try {
			ResponseStream sendSync = httpUtils.sendSync(HttpMethod.GET,
					param.getQueryStr());
			json = sendSync.readString();
			if(json!=null){
				LogUtils.i(json + "json");
				JSONObject jsonObject=new JSONObject(json);
				String code = jsonObject.getString("code");
				if ("0".equals(code)) {
					LogUtils.i("网络访问数据为空");
					return null;
				}
			}
			sendSync.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/** 保存到本地 */
	protected void saveToLocal(String json) {
		
		String path = FileUtils.getCacheDir();
		BufferedWriter writer = null;
		try {
			//打开程序从网络第一次加载，所以直接覆盖缓冲文件
			if(isFromCache){
				writer = new BufferedWriter(new FileWriter(path
						+ MD5.getMD5(getKey())));
				long time = System.currentTimeMillis() + 1000 * 60;// 先计算出过期时间，写入第一行
				writer.write(time + "\r\n");
				
			}
			//加载更多从网络获取的数据，应该追加到文件末尾
			else{
				writer = new BufferedWriter(new FileWriter(path
						+ MD5.getMD5(getKey()),true));
			}
			writer.write(json.toCharArray());
			writer.flush();
			
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			IOUtils.close(writer);
		}
	}

	/** 需要增加的额外参数 */
	protected String getParames() {
		return "";
	}
	

	/** 该协议的访问地址 */
	protected abstract String getKey();


	/*
	 * 解析json
	 */
	protected abstract T parseJson(String json);
}