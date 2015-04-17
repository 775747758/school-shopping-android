package com.school.shopping.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_Goods;
import com.school.shopping.entity.Good;
import com.school.shopping.entity.User;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.net.UploadUtil;
import com.school.shopping.utils.UIUtils;

public class Fragment_Home extends BaseFragment {
	
	private static PullToRefreshListView allGoods_lv;
	private static List<Good> goodsData = new ArrayList<Good>();
	private static Adapter_Goods adapter=null;
	private int allGoodCount=0;//数据库中所有的商品的数量
	private int startIndex=0;
	private View view;
	private static MyApplication myApp;
	private static Context context;

	/*
	 *  加载成功的界面
	 */
	@Override
	public View createLoadedView() {
		Log.i("info", "dddddddddddddddd");
		View view=UIUtils.inflate(R.layout.activity_showgoods);
		context=getActivity().getApplicationContext();
		myApp = (MyApplication)getActivity().getApplication();  //获得自定义的应用程序MyApp 
		final User user=Config.getCachedUser(context);
		allGoods_lv=(PullToRefreshListView)view.findViewById(R.id.allGoods_lv);
		allGoods_lv.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					int lastPosition=view.getLastVisiblePosition();
					if(adapter!=null&&lastPosition==adapter.getCount()-1){
						startIndex+=20;
						UploadUtil.getGoods(context,startIndex,startIndex+20);
					}
					break;
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		if(adapter==null){
			adapter=new Adapter_Goods(goodsData,context,myApp);
			allGoods_lv.setAdapter(adapter);
		}
		else{
			adapter.addMoreGoods(goodsData);
		}
		//TextView tv=new TextView(getActivity());
		//tv.setText("jajjajja");
		return view;
	}
	/*
	 *  请求服务器获取数据
	 */
	@Override
	public void load() {
		Log.i("info", "执行loadDDDDDDDDDDDDDDDD");
		UploadUtil.getGoods(getActivity(),startIndex,startIndex+20);
		
	}
	@Override
	public void initHandler() {
		mHandler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				Bundle bundle=msg.getData();
				if(bundle!=null){
					Log.i("info", "收到数据："+bundle.getString("response"));
					state = STATE_SUCCESS;

					Log.i("info", "执行success");
					goodsData.clear();
					String jsonStr=bundle.getString("response");
					if(jsonStr!=null){
						Log.i("SHOP", "手到!");
						try {
							JSONObject jsonObject=new JSONObject(jsonStr);
							String temp=jsonObject.getString("goods");
							if(temp==null||"".equals(temp)){
							}
							else{
								JSONArray tempArr=new JSONArray(temp);
								for(int i=0;i<tempArr.length();i++){
									JSONObject jso=new JSONObject(tempArr.get(i).toString());
									Good good=new Good();
									good.setGoodName(jso.getString("goodName"));
									good.setPrice(jso.getString("price"));
									good.setType(jso.getString("type"));
									good.setIsAdjust(jso.getInt("isAdjust"));
									good.setNewLevel(jso.getString("newLevel"));
									good.setIntroduction(jso.getString("introduction"));
									good.setUid(jso.getInt("uid"));
									good.setId(jso.getInt("id"));
									goodsData.add(good);
							}
								
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					Log.i("info", "knakannannanana");
					show();
				
				}else{
					
				}
				if(msg.what==URLProtocol.STATUS_FAILURE){
					//UIUtils.showMsg("服务器出现问题，我们会尽快解决");
					state=STATE_ERROR;
					show();
				}
				
			};
		};
		
	}

}
