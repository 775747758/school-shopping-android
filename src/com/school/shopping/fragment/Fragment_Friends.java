package com.school.shopping.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_Friend;
import com.school.shopping.net.FriendsProtocal;
import com.school.shopping.utils.LayoutAnimUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.ElasticListView;
import com.school.shopping.view.LoadingPage.LoadResult;
import com.school.shopping.view.QuickIndexBar;
import com.school.shopping.view.QuickIndexBar.OnTouchIndexListener;
import com.school.shopping.vo.FriendVo;

public class Fragment_Friends extends BaseFragment {
	
	List<FriendVo> list = new ArrayList<FriendVo>();
	private FriendsProtocal protocal;
	private TextView tv_index;
	private static Adapter_Friend adapter=null;
	private String[] indexArr={};
	private QuickIndexBar quickIndexBar;
	private ElasticListView lv;
	
	/*
	 *  加载成功的界面
	 */
	@Override
	public View createLoadedView() {
		View view=UIUtils.inflate(R.layout.fragment_friends);
		lv=(ElasticListView)view.findViewById(R.id.lv_friends);
        lv.setLayoutAnimation(LayoutAnimUtils.getListViewAnimationController());   //ListView 设置动画效果
		tv_index=(TextView)view.findViewById(R.id.tv_index);
		quickIndexBar=(QuickIndexBar)view.findViewById(R.id.quickIndexBar);
		quickIndexBar.setIndexArr(getIndexArr());
		quickIndexBar.setOnTouchIndexListener(new OnTouchIndexListener() {
			@Override
			public void OnTouchIndex(String word) {
				for(int i=0;i<list.size();i++){
					if(list.get(i).getPingYin().substring(0,1).toUpperCase().equals(word)){
						lv.setSelection(i);
						break;
					}
				}
			}
		});
		adapter=new Adapter_Friend(list);
		adapter.setTv_index(tv_index);
		adapter.setElasticListView(lv);
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
			protocal=FriendsProtocal.getInstance(Config.getUID());
			protocal.setUid(Config.getUID());
		}
		list=protocal.load(0, 200,true);
		if(list==null||list.isEmpty()){
			LogUtils.i("数据数据：：："+"为0");
			return LoadResult.error;
		}
		else{
			Collections.sort(list);
			LogUtils.i("数据数据：：："+list.size());
			return LoadResult.success;
		}
		
	}
	
	
	public String[] getIndexArr(){
		Set<String> setFirstWord=new HashSet<String>();
		if(list!=null||!list.isEmpty()){
			for(FriendVo vo:list){
				setFirstWord.add(vo.getPingYin().substring(0,1).toUpperCase());
			}
		}
		String[] arr = new String[setFirstWord.size()];
		return StringUtils.sortArr(setFirstWord.toArray(arr));
	}
	
	
}
