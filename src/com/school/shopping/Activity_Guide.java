package com.school.shopping;


import java.util.ArrayList;
import java.util.List;
import com.school.shopping.adapter.ViewPagerAdapter_Guide;
import com.school.shopping.login.Activity_Login;
import com.school.shopping.login.Activity_Register1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Guide extends Activity implements OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter_Guide vpAdapter;
	private List<View> views;
	private ImageView[] dots;
	private int[] ids = { R.id.iv1, R.id.iv2, R.id.iv3 };
	private Button start_btn;
	private TextView login_btn;
	private TextView register_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initViews();
		initDots();
	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.boot_one, null));
		views.add(inflater.inflate(R.layout.boot_two, null));
		views.add(inflater.inflate(R.layout.boot_three, null));

		vpAdapter = new ViewPagerAdapter_Guide(views, this);
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		login_btn = (TextView) views.get(2).findViewById(R.id.login_btn);
		register_btn = (TextView) views.get(2).findViewById(R.id.register_btn);

		
		login_btn.setClickable(true);
		login_btn.setSelected(false);
		register_btn.setClickable(true);
		register_btn.setSelected(false);
		 login_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("haola", "click");
				Intent intent = new Intent(Activity_Guide.this,
						Activity_Login.class);
				startActivity(intent);
				finish();
			}
		});
		 register_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity_Guide.this,
						Activity_Register1.class);
				intent.putExtra("from", "GuideActivity");
				startActivity(intent);
				finish();
			}
		});

		vp.setOnPageChangeListener(this);
	}

	private void initDots() {
		dots = new ImageView[views.size()];
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) findViewById(ids[i]);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < ids.length; i++) {
			if (arg0 == i) {
				dots[i].setImageResource(R.drawable.login_point_selected);
			} else {
				dots[i].setImageResource(R.drawable.login_point);
			}
		}
	}

}
