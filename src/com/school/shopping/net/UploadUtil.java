package com.school.shopping.net;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.school.shopping.Config;
import com.school.shopping.entity.User;
import com.school.shopping.fragment.Fragment_Home;
import com.school.shopping.login.Activity_Register2;
import com.school.shopping.me.Activity_AddGoods;
import com.school.shopping.utils.DeviceInfo;

public class UploadUtil {
    private static final String TAG = "file";
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    /**
 * @param path
 *            要上传的文件路径
 * @param url
 *            服务端接收URL
 * @throws Exception
 */
 public static void register(final Context context,final String activity,String path, String url,final String uname,final String password,final String city,final String school,final String name,final String phone,final String qq,final int gender){
	File file = new File(path);
	if (file.exists() && file.length() > 0) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(TIME_OUT);
		RequestParams params = new RequestParams();
		try {
			params.put(TAG, file);
			params.put("uname",uname);
			params.put("password",password);
			params.put("city",city);
			params.put("school",school);
			params.put("realName",name);
			params.put("phone",phone);
			params.put("qq",qq);
			params.put("gender",gender);
			params.put("deviceId", DeviceInfo.getUniqueID());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		// 上传文件及用户信息
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				
				String response=new String(responseBody);
				Log.i("info",response+"dddd");
				System.out.println(response);
				if(response!=null&&!"".equals(response)){
					
					JSONObject json=null;
					try {
						json=new JSONObject(response);
						if("1".equals(json.getString("code"))){
							if("Activity_Register2".equals(activity)){
								Config.cacheToken(context,json.getString(Config.KEY_TOKEN));
								User user=new User(json.getInt("uid"), uname, password, phone, name, school, "0", qq, gender, city, DeviceInfo.getUniqueID());
								Config.cacheUser(context, user);
								Activity_Register2.mHandler.sendEmptyMessage(URLProtocol.STATUS_SUCCESS);
							}
							else if("Activity_AddGoods".equals(activity)){
								Activity_AddGoods.mHandler.sendEmptyMessage(URLProtocol.STATUS_SUCCESS);
							}
						}
						else{
							Activity_Register2.mHandler.sendEmptyMessage(URLProtocol.STATUS_FAILURE);
							Log.i("info", "注册失败");
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
				else{
					Activity_Register2.mHandler.sendEmptyMessage(URLProtocol.STATUS_FAILURE);
					Log.i("info", "注册失败，返回值为空");
				}
				
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				Log.i("info", "注册失败");
				Activity_Register2.mHandler.sendEmptyMessage(URLProtocol.STATUS_FAILURE);
			}
		});
	} else {
		Log.i("info", "文件不存在或者文件为空！");
	}

}
 
 
 public static void getGoods(final Context context,final int startIndex,final int lastIndex) {
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String JSONDateUrl = URLProtocol.GET_All_GOODS;
		URLParam param=new URLParam(JSONDateUrl);
		param.addParam("startIndex",startIndex);//读取数据的开始位置
		param.addParam("lastIndex",lastIndex);//读取数据的最末位置
		Log.i("ert", "getGoods:"+param.getQueryStr());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, param.getQueryStr(), null,
				new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						Log.i("SHOP", "success!");
						Message msg=new Message();
						Bundle bundle=new Bundle();
						bundle.putString("response", response.toString());
						msg.setData(bundle);
						Fragment_Home.mHandler.sendMessage(msg);	
				}}, new Response.ErrorListener() {
					public void onErrorResponse(
							com.android.volley.VolleyError arg0) {
						Fragment_Home.mHandler.sendEmptyMessage(URLProtocol.STATUS_FAILURE);
						
					}
				});
		requestQueue.add(jsonObjectRequest);
	}

 
}