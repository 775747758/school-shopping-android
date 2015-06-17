package com.school.shopping.fragment;

import java.io.UnsupportedEncodingException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.school.shopping.Activity_ShowImage;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.User;
import com.school.shopping.me.Activity_DetailUserInfo;
import com.school.shopping.me.Activity_MyGoods;
import com.school.shopping.me.Activity_Setting;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.CircleImageView;

import de.greenrobot.event.EventBus;

public class Fragment_Me extends Fragment implements OnClickListener{

	
	private boolean isPortraitChanged=false;
	private boolean isRealNameChanged=false;
	private TextView name_tv;
	private CircleImageView portrait;
	private android.support.v4.app.FragmentManager mFragmentManager;
	private User user;
	private RelativeLayout userInfo;
	private RelativeLayout myGoods;
	private RelativeLayout setting;
	private URLParam param;
	private FragmentTransaction fragmentTransaction;
	
	
	//使用universial_image_loader下载图片
	public void init(){
		if(param==null){
			param = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
			try {
				param.addParam("filename", "user_"+user.getUname()+".jpg");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		MyApplication.getImageLoader().displayImage(param.getQueryStr(), portrait); 
	}
	
	@Override
	public void onResume() {
		if(isPortraitChanged){
			MyApplication.getImageLoader().clearMemoryCache();
			MyApplication.getImageLoader().displayImage(param.getQueryStr(), portrait); 
			isPortraitChanged=false;
		}
		if(isRealNameChanged){
			user=Config.getCachedUser();
			if(user!=null){
				LogUtils.i(user.toString());
				name_tv.setText(user.getRealName());
			}
		}
		super.onResume();
	}
	
	
	public void onEvent(String msg){
		LogUtils.i("!!!!!!!!!!!!!!!!!!!");
		if("update".equals(msg)){
			isPortraitChanged=true;
		}
		if("updateRealName".equals(msg)){
			LogUtils.i("!!!!!!!!!!!!!!!!!!!");
			isRealNameChanged=true;
		}
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if(!EventBus.getDefault().isRegistered(this)){
			EventBus.getDefault().register(this);
		}
		/*//为了给Fragment设置转场动画
		if (null == mFragmentManager) {
            mFragmentManager = getFragmentManager();
        }
        fragmentTransaction = mFragmentManager
                .beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.zoomin, R.anim.zoomout);
        fragmentTransaction.addToBackStack(null);
        //commit方法是在Activity的onSaveInstanceState()之后调用的，这样会出错
        if(!getActivity().isDestroyed()){
        	fragmentTransaction.commitAllowingStateLoss();
        }
        */
        
		View view = inflater.inflate(R.layout.fragment_me, null);
		portrait = (CircleImageView) view.findViewById(R.id.portrait);
		portrait.setOnClickListener(this);
		name_tv = (TextView) view.findViewById(R.id.name_tv);
		userInfo=(RelativeLayout) view.findViewById(R.id.userInfo);
		userInfo.setOnClickListener(this);
		myGoods=(RelativeLayout) view.findViewById(R.id.myGoods);
		myGoods.setOnClickListener(this);
		setting=(RelativeLayout) view.findViewById(R.id.setting);
		setting.setOnClickListener(this);
		user=Config.getCachedUser();
		if(user!=null){
			LogUtils.i(user.toString());
			name_tv.setText(user.getRealName());
		}
		init();
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userInfo:
			Intent intent = new Intent(getActivity(),Activity_DetailUserInfo.class);
			startActivity(intent);
			break;
		case R.id.myGoods:
			Intent intentM=new Intent(BaseActivity.getRunActivity(),Activity_MyGoods.class);
			intentM.putExtra("isMyGoods", true);
			UIUtils.startActivity(intentM);
			break;
		case R.id.portrait:
			if(MyApplication.getImageLoader().getDiskCache().get(param.getQueryStr())!=null){
				if(MyApplication.getImageLoader().getDiskCache().get(param.getQueryStr()).exists()){
					Intent intent2 = new Intent(getActivity(),Activity_ShowImage.class);
					intent2.putExtra("filePath", MyApplication.getImageLoader().getDiskCache().get(param.getQueryStr()).getAbsolutePath());
					startActivity(intent2);
				}
			}
			break;
		case R.id.setting:
			Intent intentS = new Intent(getActivity(),Activity_Setting.class);
			startActivity(intentS);
			break;
		}
		
	}
	
	
	@Override
	public void onDestroy() {
		if(EventBus.getDefault().isRegistered(this)){
			EventBus.getDefault().unregister(this);
		}
		if(fragmentTransaction!=null){
			if(!fragmentTransaction.isEmpty()){
				fragmentTransaction.remove(this);
				fragmentTransaction=null;
			}
		}
		user=null;
		param=null;
		super.onDestroy();
	}
}
