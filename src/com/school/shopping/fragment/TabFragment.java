package com.school.shopping.fragment;

import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.R.layout;
import com.school.shopping.entity.User;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.LoadingPage.LoadResult;

import io.rong.imkit.RongIM;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends BaseFragment
{
	private String mTitle = "Default";

	private Handler mHandler;

	public static final String TITLE = "title";

	
	@Override
	public View createLoadedView() {
		View view=UIUtils.inflate(R.layout.activity_register1);
		/* mHandler = new Handler();
		if (getArguments() != null)
		{
			mTitle = getArguments().getString(TITLE);
		}

		TextView tv = new TextView(getActivity());
		tv.setTextSize(20);
		tv.setBackgroundColor(Color.parseColor("#ffffffff"));
		tv.setText(mTitle);
		tv.setGravity(Gravity.CENTER);
		tv.setClickable(true);
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       String userId = "2";
                       String title = "摆摊儿";
                       User user=Config.getCachedUser(getActivity());
                       RongIM.getInstance().startPrivateChat(getActivity(), userId, title);
                   }
               }
			);
			}
		});*/
		return view;
	}


	@Override
	public LoadResult load() {
		// TODO Auto-generated method stub
		return LoadResult.error;
	}


}
