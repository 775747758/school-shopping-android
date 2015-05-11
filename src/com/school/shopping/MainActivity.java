package com.school.shopping;

import io.rong.imkit.fragment.ConversationListFragment;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.school.shopping.fragment.BaseFragment;
import com.school.shopping.fragment.FragmentFactory;
import com.school.shopping.fragment.Fragment_Friends;
import com.school.shopping.fragment.Fragment_Home;
import com.school.shopping.fragment.Fragment_Me;
import com.school.shopping.fragment.TabFragment;
import com.school.shopping.login.Activity_Login;
import com.school.shopping.login.Activity_Register3;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.ActionBarUtils;
import com.school.shopping.utils.DeviceInfo;
import com.school.shopping.view.ChangeColorIconWithText;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

public class MainActivity extends FragmentActivity implements OnClickListener,OnPageChangeListener{

	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentStatePagerAdapter mAdapter;
	private boolean isFirstShow=false;

	private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initDatas();
		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
		initEvent();
		
		checkLoginState();
		
		
	}
	
	public static Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle bundle=msg.getData();
			String jsonStr=bundle.getString("jsonStr");
		};
	};
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home://操作home按钮点击事件
			
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initEvent()
	{

		mViewPager.setOnPageChangeListener(this);

	}

	private void initDatas()
	{
		Fragment_Home showGoods=new Fragment_Home();
		mTabs.add(showGoods);
		mTabs.add(new Fragment_Friends());
		mTabs.add(new ConversationListFragment());
		Fragment_Me fraMe=new Fragment_Me();
		mTabs.add(fraMe);
		
		mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager())
		{

			//isFirstShow=false;

			@Override
			public int getCount()
			{
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int position)
			{
				return mTabs.get(position);
			}
			
			@Override
			public void setPrimaryItem(View container, int position,
					Object object) {
				Log.i("info", "setPrimaryItem");
				super.setPrimaryItem(container, position, object);
				if(position==0){
					Log.i("info", "创建："+position);
					BaseFragment createFragment=FragmentFactory.createFragment(position);
					createFragment.show();
					//isFirstShow=true;
				}
				
			}
		};
	}

	private void initView()
	{
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

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

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels)
	{
		// Log.e("TAG", "position = " + position + " ,positionOffset =  "
		// + positionOffset);
		if (positionOffset > 0)
		{
			ChangeColorIconWithText left = mTabIndicators.get(position);
			ChangeColorIconWithText right = mTabIndicators.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageSelected(int position)
	{
		BaseFragment createFragment=FragmentFactory.createFragment(position);
		createFragment.show();
 	}
	
	

	@Override
	public void onPageScrollStateChanged(int state)
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void onClick(View v) {
		clickTab(v);
		
	}
	private void clickTab(View v)
	{
		resetOtherTabs();

		switch (v.getId())
		{
		case R.id.id_indicator_one:
			mTabIndicators.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mTabIndicators.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicators.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			mTabIndicators.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;
		}
	}
	private void resetOtherTabs()
	{
		for (int i = 0; i < mTabIndicators.size(); i++)
		{
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}
	
	private void checkLoginState() {
		if(Config.getCachedToken()!=null){
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
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
					Request.Method.POST, param.getQueryStr(), null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {
							try {
								if(response!=null){
									if("0".equals(response.getString("code"))){
										exist();
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(
								com.android.volley.VolleyError arg0) {
							Log.i("info", "验证失败，服务器出现问题");
						}
					});
			requestQueue.add(jsonObjectRequest);
		}
	}

	
	public void exist(){
		Config.removeCache("token");
		Intent intent = new Intent(MainActivity.this,
				Activity_Login.class);
		intent.putExtra("isNotice", true);
		startActivity(intent);
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//isFirstShow=false;
	}

}
