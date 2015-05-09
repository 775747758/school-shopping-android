package com.school.shopping.fragment;

import java.io.File;
import java.io.UnsupportedEncodingException;

import com.meg7.widget.CustomShapeImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.User;
import com.school.shopping.me.Activity_DetailUserInfo;
import com.school.shopping.me.Activity_MyGoods;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.LoadingPage.LoadResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Me extends BaseFragment implements OnClickListener{

	private TextView name_tv;
	private String portraitUri;
	private boolean isLogin;
	private SharedPreferences sp;
	private AlertDialog dialog;
	private String userName;
	private String picName;
	private CustomShapeImageView portrait;
	private android.support.v4.app.FragmentManager mFragmentManager;
	private MyApplication myApp;
	private User user;
	private RelativeLayout userInfo;
	private RelativeLayout myGoods;
	
	
	//使用universial_image_loader下载图片
	public void init(){
    	URLParam param = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			param.addParam("filename", "user_"+user.getUname()+".png");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		myApp.getImageLoader().displayImage(param.getQueryStr(), portrait,myApp.getOptions()); 
    }
	public void myCollection(View view) {
		
	}

	public void login(View view) {
		

	}

	public void myStall(View view) {
		
	}

	public void myGoods(View view) {
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userInfo:
			Intent intent = new Intent(getActivity(),Activity_DetailUserInfo.class);
			startActivity(intent);
			break;
		case R.id.myGoods:
			Intent intent1 = new Intent(getActivity(),Activity_MyGoods.class);
			intent1.putExtra("userName", user.getUname());
			startActivity(intent1);
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public View createLoadedView() {

		
		myApp = (MyApplication)getActivity().getApplication();  //获得自定义的应用程序MyApp 
		
		//为了给Fragment设置转场动画
		if (null == mFragmentManager) {
            mFragmentManager = getFragmentManager();
        }
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.zoomin, R.anim.zoomout);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
		View view = UIUtils.inflate(R.layout.activity_me);

		portrait = (CustomShapeImageView) view.findViewById(R.id.portrait);
		name_tv = (TextView) view.findViewById(R.id.name_tv);
		userInfo=(RelativeLayout) view.findViewById(R.id.userInfo);
		userInfo.setOnClickListener(this);
		
		myGoods=(RelativeLayout) view.findViewById(R.id.myGoods);
		myGoods.setOnClickListener(this);
		user=Config.getCachedUser();
		if(user!=null){
			name_tv.setText(user.getRealName());
		}
		init();
		return view;
	}
	@Override
	public LoadResult load() {
		// TODO Auto-generated method stub
		return LoadResult.error;
	}

	
	


}
