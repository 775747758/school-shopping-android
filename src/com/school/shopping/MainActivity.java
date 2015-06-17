package com.school.shopping;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.school.shopping.fragment.BaseFragment;
import com.school.shopping.fragment.FragmentFactory;
import com.school.shopping.login.Activity_Login;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.DeviceInfo;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.ChangeColorIconWithText;
import com.school.shopping.view.ViewPagerCompat;
import com.school.shopping.view.ViewPagerCompat.OnPageChangeListener;

public class MainActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {

	private ViewPagerCompat mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();
	private RelativeLayout.LayoutParams lp45;
	private RelativeLayout.LayoutParams lp0;

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if (positionOffset > 0) {
			ChangeColorIconWithText left = mTabIndicators.get(position);
			ChangeColorIconWithText right = mTabIndicators.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageSelected(int position) {
		mViewPager.setCurrentItem(position, false);
		mViewPager.setLayoutParams(position==2?lp45:lp0);
		if(position!=2&&position!=3){
			BaseFragment createFragment = (BaseFragment) FragmentFactory.createFragment(position);
			createFragment.show();
		}
	}

	@Override
	public void onClick(View v) {
		clickTab(v);
	}

	private void clickTab(View v) {
		resetOtherTabs();
		
		switch (v.getId()) {
		case R.id.id_indicator_one:
			mTabIndicators.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0,false);
			break;
		case R.id.id_indicator_two:
			mTabIndicators.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1,false);
			break;
		case R.id.id_indicator_three:
			mTabIndicators.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2,false);
			break;
		case R.id.id_indicator_four:
			mTabIndicators.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3,false);
			break;
		}
		
	}

	@Override
	protected void init() {
		//FragmentFactory.initFragments();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);
		lp45 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  // , 1是可选写的
		lp45.setMargins(0, UIUtils.dip2px(45), 0, UIUtils.dip2px(60)); 
		
		lp0 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  // , 1是可选写的
		lp0.setMargins(0,0, 0, UIUtils.dip2px(60)); 
		initViews();
		initDatas();
		//加上过度动画会导致有的时候无法显示fragment
		//mViewPager.setPageTransformer(true, new DepthPageTransformer());
		mViewPager.setLayoutParams(lp0);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(this);
		checkLoginState();
	}

	private void initViews() {
		mViewPager = (ViewPagerCompat) findViewById(R.id.id_viewpager);

		ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
		mTabIndicators.add(one);
		ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
		mTabIndicators.add(two);
		ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
		mTabIndicators.add(three);
		ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
		mTabIndicators.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		one.setIconAlpha(1.0f);

	}


	private void initDatas() {
		mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
	}
	

	class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return FragmentFactory.createFragment(position);
		}

		@Override
		public int getCount() {
			return 4;
		}
		
		


	}

	private void checkLoginState() {
		if (Config.getCachedToken() != null) {
			RequestQueue requestQueue = Volley.newRequestQueue(this);
			String JSONDateUrl = URLProtocol.CHECK_LOGIN_STATE;
			URLParam param = new URLParam(JSONDateUrl);
			try {
				param.addParam("id", Config.getUID());
				param.addParam("deviceId", DeviceInfo.getUniqueID());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Log.i("ert", param.getQueryStr());
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, param.getQueryStr(), null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {
							try {
								if (response != null) {
									if ("0".equals(response.getString("code"))) {
										exist();
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(com.android.volley.VolleyError arg0) {
							Log.i("info", "验证失败，服务器出现问题");
						}
					});
			requestQueue.add(jsonObjectRequest);
		}
	}

	public void exist() {
		Config.removeAppCache();
		//FragmentFactory.destoryFragments();
		Intent intent = new Intent(MainActivity.this, Activity_Login.class);
		intent.putExtra("isNotice", true);
		startActivity(intent);
	}

	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicators.size(); i++) {
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}
	
	
	@Override
	protected void onDestroy() {
		if(mTabIndicators!=null){
			mTabIndicators.clear();
			mTabIndicators=null;
		}
		super.onDestroy();
	}
	

}
