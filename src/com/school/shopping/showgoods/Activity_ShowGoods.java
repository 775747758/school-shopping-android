package com.school.shopping.showgoods;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_ShowGood;
import com.school.shopping.entity.Good;
import com.school.shopping.net.ShowGoodProtocal;
import com.school.shopping.utils.DialogUtils;
import com.school.shopping.utils.ListUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.utils.ViewUtils;
import com.school.shopping.view.LoadingPage;
import com.school.shopping.view.LoadingPage.LoadResult;
import com.school.shopping.vo.GoodVo;

public class Activity_ShowGoods extends BaseActivity implements OnClickListener{

	private static LoadingPage mContentView;
	public static final int SORT_TYPE_DATE=0;
	public static final int SORT_TYPE_DISTANCE=1;
	public static final int SORT_TYPE_PRICE=2;
	
	public static final int TYPE_ALL=-1;
	public static final int TYPE_CLOSES=1;
	public static final int TYPE_BOOKS=2;
	public static final int TYPE_DIGIT=3;
	public static final int TYPE_OTHER=4;
	private int sortType=SORT_TYPE_DATE;
	private static int type=TYPE_ALL;
	private static boolean isFromCache=true;
	private static String keyword;
	List<GoodVo> goodsData = new ArrayList<GoodVo>();
	private Adapter_ShowGood adapter;
	private ImageView iv_search;
	private EditText search_et;
	private ShowGoodProtocal showGoodProtocal;
	private TextView tv_null;
	public static final int BEGIN_SEARCH=10;
	
	
	@Override
	protected void initView() {

		if(mContentView == null){
			mContentView = new LoadingPage(UIUtils.getContext()){

				@Override
				public View createLoadedView() {
					return Activity_ShowGoods.this.createLoadedView();
				}

				@Override
				public LoadResult load() {
					LoadResult loadResult=Activity_ShowGoods.this.load();
					return loadResult;
				}
			};
		}else{
			ViewUtils.removeSelfFromParent(mContentView);
		}
		
		setContentView(mContentView);
		mContentView.show();
	}

	@Override
	protected void init() {}

	protected LoadResult load() {
		if(showGoodProtocal==null){
			showGoodProtocal=new ShowGoodProtocal();
		}
		showGoodProtocal.setSortType(sortType);
		showGoodProtocal.setType(type);
		showGoodProtocal.setKeyword(keyword);
		goodsData=showGoodProtocal.load(0,20,false);
		if(showGoodProtocal.isNetError()){
			LogUtils.i("网络错误");
			return LoadResult.error;
		}
		if(null != goodsData){
			
			UIUtils.runInMainThread(new Runnable() {
				@Override
				public void run() {
					
					if( Activity_ShowGoods.this.adapter!=null){
						LogUtils.i("refresh");
						if(tv_null!=null){
							tv_null.setVisibility(View.GONE);
						}
						adapter.setData(goodsData);
						sortData(sortType);
					}
				}
			});
			LogUtils.i("有数据");
			return LoadResult.success;
		}else{
			UIUtils.runInMainThread(new Runnable() {
				
				@Override
				public void run() {
					tv_null.setVisibility(View.VISIBLE);
				}
			});
			LogUtils.i("数据为空");
			return LoadResult.error;
		}
		
		/*UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				if( Activity_ShowGoods.this.adapter!=null){
					LogUtils.i("refresh");
					if(tv_null!=null){
						tv_null.setVisibility(View.GONE);
					}
					adapter.setData(goodsData);
					sortData(sortType);
				}
			}
		});*/
	}

	//加载成功显示的界面
	protected View createLoadedView() {
		
		View view = UIUtils.inflate(R.layout.activity_showgoods);
		
		//排序方式
		TextView  tv_date = (TextView) view.findViewById(R.id.tv_date);
		TextView  tv_distance = (TextView) view.findViewById(R.id.tv_distance);
		TextView  tv_price = (TextView) view.findViewById(R.id.tv_price);
		//类别
		TextView  tv_closes = (TextView) view.findViewById(R.id.tv_closes);
		TextView  tv_books = (TextView) view.findViewById(R.id.tv_books);
		TextView  tv_digit = (TextView) view.findViewById(R.id.tv_digit);
		TextView  tv_other = (TextView) view.findViewById(R.id.tv_other);
		tv_date.setOnClickListener(this);
		tv_distance.setOnClickListener(this);
		tv_price.setOnClickListener(this);
		tv_closes.setOnClickListener(this);
		tv_books.setOnClickListener(this);
		tv_digit.setOnClickListener(this);
		tv_other.setOnClickListener(this);
		iv_search=(ImageView) view.findViewById(R.id.iv_search);
		iv_search.setOnClickListener(this);
		tv_null=(TextView)view.findViewById(R.id.tv_null);
		/*//搜索按钮
		beginSearch_tv=(TextView)view.findViewById(R.id.beginSearch_tv);
		
		//tv_null.setVisibility(View.VISIBLE);
		beginSearch_tv.setOnClickListener(this);
		search_et=(EditText)view.findViewById(R.id.search_et);*/
		search_et=(EditText)view.findViewById(R.id.search_et);
		PullToRefreshListView goods_lv=(PullToRefreshListView) view.findViewById(R.id.goods_lv);
		LogUtils.i("size::"+goodsData.size());
		
		adapter=new Adapter_ShowGood(goodsData);
		if(showGoodProtocal!=null){
			adapter.setProtocal(showGoodProtocal);
			adapter.setListview(goods_lv);
		}
		if(goodsData!=null||goodsData.size()>0){
			goods_lv.setAdapter(adapter);
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_date:
			LogUtils.i("TYPE_DATE");
			sortType=SORT_TYPE_DATE;
			sortData(sortType);
			break;
		case R.id.tv_distance:
			LogUtils.i("TYPE_DISTANCE");
			sortType=SORT_TYPE_DISTANCE;
			sortData(sortType);
			break;
		case R.id.tv_price:
			LogUtils.i("TYPE_PRICE");
			sortType=SORT_TYPE_PRICE;
			sortData(sortType);
			break;
		case R.id.tv_closes:
			type=TYPE_CLOSES;
			isFromCache=false;
			mContentView.state=LoadingPage.STATE_LOADING;
			mContentView.show();
			break;
		case R.id.tv_books:
			type=TYPE_BOOKS;
			isFromCache=false;
			mContentView.state=LoadingPage.STATE_LOADING;
			mContentView.show();
			break;
		case R.id.tv_digit:
			type=TYPE_DIGIT;
			isFromCache=false;
			mContentView.state=LoadingPage.STATE_LOADING;
			mContentView.show();
			break;
		case R.id.tv_other:
			type=TYPE_OTHER;
			isFromCache=false;
			mContentView.state=LoadingPage.STATE_LOADING;
			mContentView.show();
			break;
		case R.id.beginSearch_tv:
			LogUtils.i("搜索");
			//已经在布局文件中限制字数20
			//keyword=search_et.getText().toString();
			type=TYPE_ALL;
			isFromCache=false;
			mContentView.state=LoadingPage.STATE_LOADING;
			mContentView.show();
			break;
		case R.id.iv_search:
			DialogUtils.createASearchDialog();
			break;
		}
		
	}
	
	
	private void sortData(int type){
		switch (type) {
		case SORT_TYPE_DATE:
			List<GoodVo> list= ListUtils.sortGood(goodsData, SORT_TYPE_DATE, true);
			adapter.setData(list);
			break;
		case SORT_TYPE_DISTANCE:
			List<GoodVo> list1= ListUtils.sortGood(goodsData, SORT_TYPE_DISTANCE, true);
			adapter.setData(list1);
			break;
		case SORT_TYPE_PRICE:
			List<GoodVo> list2= ListUtils.sortGood(goodsData, SORT_TYPE_PRICE, true);
			adapter.setData(list2);
			break;

		}
		
	
	}
	
	
	public static Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what==BEGIN_SEARCH){
				keyword=(String)msg.obj;
				type=TYPE_ALL;
				isFromCache=false;
				mContentView.state=LoadingPage.STATE_LOADING;
				mContentView.show();
			}

		};
	};
}
