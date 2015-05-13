package com.school.shopping.login;

import java.util.List;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.school.shopping.BaseActivity;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_City;
import com.school.shopping.net.SchoolProtocal;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.utils.ViewUtils;
import com.school.shopping.view.LoadingPage;
import com.school.shopping.view.LoadingPage.LoadResult;

public class Activity_School extends BaseActivity {

	private LoadingPage mContentView;
	private List<String> list;

	@Override
	protected void init() {}

	@Override
	protected void initView() {
		if (mContentView == null) {
			mContentView = new LoadingPage(UIUtils.getContext()) {

				@Override
				public LoadResult load() {
					return Activity_School.this.load();
				}

				@Override
				public View createLoadedView() {
					return Activity_School.this.createLoadedView();
				}
			};
		} else {
			ViewUtils.removeSelfFromParent(mContentView);
		}

		setContentView(mContentView);
		mContentView.show();
	}

	protected View createLoadedView() {
		View view = UIUtils.inflate(R.layout.activity_school);
		ListView lv_school = (ListView) view.findViewById(R.id.lv_school);
		if(list!=null){
			Adapter_City adapter = new Adapter_City(list);
			lv_school.setAdapter(adapter);
			lv_school.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent = new Intent(Activity_School.this, Activity_Register2.class);
					intent.putExtra("school", list.get(arg2));
					setResult(RESULT_OK, intent);
					finish();
				}
			});
		}
		return view;
	}

	protected LoadResult load() {
		SchoolProtocal protocal = new SchoolProtocal();
		list = protocal.load(-1, -1, true);
		if (list == null || list.size() == 0) {
			return LoadResult.error;
		}
		return LoadResult.success;
	}

}
