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
import com.school.shopping.login.Activity_Register3;
import com.school.shopping.me.Activity_AddGoods;
import com.school.shopping.utils.DeviceInfo;

public class UploadUtil {
    private static final String TAG = "file";
    private static final int TIME_OUT = 10 * 1000; // 超时时间
 
 
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
						//Fragment_Home.mHandler.sendMessage(msg);	
				}}, new Response.ErrorListener() {
					public void onErrorResponse(
							com.android.volley.VolleyError arg0) {
						//Fragment_Home.mHandler.sendEmptyMessage(URLProtocol.STATUS_FAILURE);
						
					}
				});
		requestQueue.add(jsonObjectRequest);
	}

 
}