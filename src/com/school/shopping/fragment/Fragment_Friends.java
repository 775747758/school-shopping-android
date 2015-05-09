package com.school.shopping.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.TtsSpan.ElectronicBuilder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_Friends;
import com.school.shopping.adapter.Adapter_Home;
import com.school.shopping.entity.Good;
import com.school.shopping.entity.User;
import com.school.shopping.net.FriendsProtocal;
import com.school.shopping.net.HomeProtocal;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.net.UploadUtil;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.ElasticListView;
import com.school.shopping.view.LoadingPage.LoadResult;
import com.school.shopping.view.NonScrollGridView;
import com.school.shopping.vo.FriendVo;

public class Fragment_Friends extends BaseFragment {
	
	List<FriendVo> list = new ArrayList<FriendVo>();
	private FriendsProtocal protocal;
	private static Adapter_Friends adapter=null;

	/*
	 *  加载成功的界面
	 */
	@Override
	public View createLoadedView() {
		View view=UIUtils.inflate(R.layout.fragment_friends);
		ElasticListView lv=(ElasticListView)view.findViewById(R.id.lv_friends);
		adapter=new Adapter_Friends(list);
		adapter.setProtocal(protocal);
		if(list!=null){
			lv.setAdapter(adapter);
		}
		return view;
	}
	/*
	 *  请求服务器获取数据
	 */
	@Override
	public LoadResult load() {
		if(protocal==null){
			protocal=new FriendsProtocal();
		}
		protocal.setUid(Config.getUID());
		list=protocal.load(0, 20,true);
		if(list==null||list.size()==0){
			LogUtils.i("数据数据：：："+"为0");
		}
		else{
			LogUtils.i("数据数据：：："+list.size());
		}
		return LoadResult.success;
	}
	
	
}
