package com.school.shopping;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.school.shopping.adapter.ViewPagerAdapter_Guide;
import com.school.shopping.login.Activity_Login;
import com.school.shopping.login.Activity_Register1;
import com.school.shopping.utils.UIUtils;

public class Activity_Guide extends BaseActivity implements OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter_Guide vpAdapter;
	private List<View> views;
	private ImageView[] dots;
	private int[] ids = { R.id.iv1, R.id.iv2, R.id.iv3 };
	private TextView login_btn;
	private TextView register_btn;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_guide);
		initViews();
		initDots();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	private void initViews() {
		views = new ArrayList<View>();
		views.add(UIUtils.inflate(R.layout.boot_one));
		views.add(UIUtils.inflate(R.layout.boot_two));
		views.add(UIUtils.inflate(R.layout.boot_three));
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
				Intent intent = new Intent(Activity_Guide.this, Activity_Login.class);
				startActivity(intent);
				finish();
			}
		});
		register_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity_Guide.this, Activity_Register1.class);
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
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
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
