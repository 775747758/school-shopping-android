package com.school.shopping.me;


import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_MyGoods;
import com.school.shopping.entity.Good;
import com.school.shopping.entity.User;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.UIUtils;

public class Activity_MyGoods extends Activity{
	
	private PullToRefreshListView myGoodsLV;
	List<Good> goodsData = new ArrayList<Good>();
	private MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mygoods);
		myGoodsLV=(PullToRefreshListView)findViewById(R.id.myGoods_lv);
		myApp = (MyApplication)getApplication();  //获得自定义的应用程序MyApp 
		User user=Config.getCachedUser();
		getJSONVolley(user.getId());
	}
	
	//加载用户详细信息
		public void getJSONVolley(final int uid) {
			RequestQueue requestQueue = Volley.newRequestQueue(this);
			String JSONDateUrl = URLProtocol.GET_MY_GOODS;
			URLParam param=new URLParam(JSONDateUrl);
			param.addParam("uid",uid);	
			Log.i("ert", param.getQueryStr());
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
					Request.Method.GET, param.getQueryStr(), null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {
							try {
								String temp=response.getString("goods");
								JSONArray tempArr=new JSONArray(temp);
								for(int i=0;i<tempArr.length();i++){
									JSONObject jso=new JSONObject(tempArr.get(i).toString());
									Good good=new Good();
									good.setGoodName(jso.getString("goodName"));
									good.setPrice(jso.getString("price"));
									good.setType(jso.getInt("type"));
									good.setIsAdjust(jso.getInt("isAdjust"));
									good.setNewLevel(jso.getInt("newLevel"));
									good.setIntroduction(jso.getString("introduction"));
									good.setUid(jso.getInt("uid"));
									good.setId(jso.getInt("id"));
									goodsData.add(good);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							myGoodsLV.setAdapter(new Adapter_MyGoods(goodsData));
							
					}}, new Response.ErrorListener() {
						public void onErrorResponse(
								com.android.volley.VolleyError arg0) {
							//dialog.dismiss();
							UIUtils.showMsg("服务器出现问题，我们会尽快解决");
						}
					});
			requestQueue.add(jsonObjectRequest);
		}

}
