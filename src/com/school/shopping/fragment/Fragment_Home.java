package com.school.shopping.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.Outline;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.adapter.Adapter_Home;
import com.school.shopping.entity.User;
import com.school.shopping.me.Activity_AddGood;
import com.school.shopping.net.ShowGoodProtocal;
import com.school.shopping.utils.KeyBoardUtils;
import com.school.shopping.utils.ListUtils;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.LoadingPage;
import com.school.shopping.view.LoadingPage.LoadResult;
import com.school.shopping.vo.GoodVo;

public class Fragment_Home extends BaseFragment implements OnClickListener {

	public  final String SORT_TYPE_DATE = "id";
	public  final String SORT_TYPE_DISTANCE = "distance";
	public  final String SORT_TYPE_PRICE = "price";
	public  final int TYPE_ALL = -1;
	public  final int TYPE_CLOSES = 1;
	public  final int TYPE_BOOKS = 2;
	public  final int TYPE_DIGIT = 3;
	public  final int TYPE_OTHER = 4;
	private String sortType = SORT_TYPE_DATE;
	public  int type = TYPE_ALL;
	private  boolean isFromCache = true;
	private  String keyword;
	private List<GoodVo> goodsData = new ArrayList<GoodVo>();
	private static Adapter_Home adapter;
	private ImageView iv_search;
	private EditText search_et;
	private ShowGoodProtocal showGoodProtocal;
	private TextView tv_null;
	private AlertDialog searchAlert;
	private AlertDialog menuAlert;
	private RelativeLayout rl_type;
	private TextView tv_type;
	private TextView tv_date;
	private TextView tv_distance;
	private TextView tv_price;
	private String DESC = "desc";
	private String ASC = "asc";
	private String order = DESC;
	private PullToRefreshListView goods_lv;
	public  final int BEGIN_SEARCH = 10;
	public  final int CHANGE_TYPE = 11;
	private ButtonFloat buttonFloat;

	public LoadingPage getLoadingPage() {
		return loadingPage;
	}

	@Override
	public View createLoadedView() {
		View view = UIUtils.inflate(R.layout.fragment_home);
		//初始化组件
		init(view);
		//初始化下拉刷新listview
		initRefresh(view);
		//初始化Adapter
		initAdapter();
		return view;

	}

	private void initAdapter() {
		adapter = new Adapter_Home(goodsData);
		if (showGoodProtocal != null) {
			adapter.setProtocal(showGoodProtocal);
			adapter.setListview(goods_lv);
		}
		if (goodsData != null || goodsData.size() > 0) {
			goods_lv.setAdapter(adapter);
		}
	}

	private void init(View view) {
		tv_date = (TextView) view.findViewById(R.id.tv_date);
		tv_date.setTextColor(UIUtils.getColor(R.color.MyThemePink));
		tv_distance = (TextView) view.findViewById(R.id.tv_distance);
		tv_price = (TextView) view.findViewById(R.id.tv_price);

		tv_date.setOnClickListener(this);
		tv_distance.setOnClickListener(this);
		tv_price.setOnClickListener(this);
		iv_search = (ImageView) view.findViewById(R.id.iv_search);
		iv_search.setOnClickListener(this);
		tv_null = (TextView) view.findViewById(R.id.tv_null);
		search_et = (EditText) view.findViewById(R.id.search_et);
		rl_type = (RelativeLayout) view.findViewById(R.id.rl_type);
		tv_type = (TextView) view.findViewById(R.id.tv_type);
		rl_type.setOnClickListener(this);
		tv_type.setText("全部");
		buttonFloat=(ButtonFloat)view.findViewById(R.id.buttonFloat);
		buttonFloat.setOnClickListener(this);
	}

	@Override
	public LoadResult load() {
		LogUtils.i("GGzhixing");
		showGoodProtocal=ShowGoodProtocal.getInstance();
		User user=Config.getCachedUser();
		showGoodProtocal.setCity(user.getCity());
		showGoodProtocal.setProvince(user.getProvince());
		showGoodProtocal.setSortType(sortType);
		showGoodProtocal.setOrder(order);
		showGoodProtocal.setType(type);
		showGoodProtocal.setKeyword(keyword);
		if(goods_lv!=null){
			if(goods_lv.isRefreshing()){
				LogUtils.i("HH：在刷新");
				goodsData = showGoodProtocal.load(0, 19, false);
			}else{
				LogUtils.i("HH：不在刷新");
				showGoodProtocal.setForceFromCache(true);
				goodsData = showGoodProtocal.load(0, 19, true);
			}
		}else{
			showGoodProtocal.setForceFromCache(true);
			LogUtils.i("HH：第一次加载");
			goodsData = showGoodProtocal.load(0, 19, true);
		}
		if (showGoodProtocal.isNetError()) {
			LogUtils.i("网络错误");
			return LoadResult.error;
		}
		if (null == goodsData||goodsData.size()==0) {
			LogUtils.i("HH：数据为空");
			UIUtils.runInMainThread(new Runnable() {

				@Override
				public void run() {
					if (goods_lv != null) {
						goods_lv.onRefreshComplete();
					}
					tv_null.setVisibility(View.VISIBLE);
				}
			});
			LogUtils.i("数据为空");
			return LoadResult.empty;
		} else {
			LogUtils.i("HH：数据不为空"+goodsData.size());
			UIUtils.runInMainThread(new Runnable() {
				@Override
				public void run() {
					if (goods_lv != null) {
						goods_lv.onRefreshComplete();
					}
					if (adapter != null) {
						LogUtils.i("refresh");
						if (tv_null != null) {
							tv_null.setVisibility(View.GONE);
						}
						adapter.setData(goodsData);
					}
				}
			});
			LogUtils.i("有数据");
			return LoadResult.success;
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_date:
			LogUtils.i("TYPE_DATE");
			showTopView(tv_date);
			sortType = SORT_TYPE_DATE;
			order = order.equals(DESC) ? ASC : DESC;
			goods_lv.setRefreshing();
			break;
		case R.id.tv_distance:
			showTopView(tv_distance);
			LogUtils.i("TYPE_DISTANCE");
			sortType = SORT_TYPE_DISTANCE;
			order = order.equals(DESC) ? ASC : DESC;
			goods_lv.setRefreshing();
			break;
		case R.id.tv_price:
			LogUtils.i("TYPE_PRICE");
			showTopView(tv_price);
			sortType = SORT_TYPE_PRICE;
			order = order.equals(DESC) ? ASC : DESC;
			goods_lv.setRefreshing();
			break;
		case R.id.iv_search:
			createASearchDialog();
			break;
		case R.id.rl_type:
			createABottomDialog();
			break;
		case R.id.buttonFloat:
			Intent intent=new Intent(BaseActivity.getRunActivity(),Activity_AddGood.class);
			UIUtils.startActivity(intent);
			break;
		}

	}

	// 因为要调用非静态变量和方法，所以就没有抽取到其他文件
	public void createASearchDialog() {
		if (searchAlert != null) {
			LogUtils.i("直接返回");
			searchAlert.show();
			KeyBoardUtils.openKeybord(search_et);
			return;
		}
		KeyBoardUtils.openKeybord(search_et);
		AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.getRunActivity(),
				AlertDialog.THEME_HOLO_LIGHT);
		builder.setInverseBackgroundForced(true);
		View serachView = UIUtils.inflate(R.layout.dialog_search);
		final EditText search_et = (EditText) serachView.findViewById(R.id.search_et);
		
		TextView beginSearch_tv = (TextView) serachView.findViewById(R.id.beginSearch_tv);
		beginSearch_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String key = search_et.getText().toString();
				if (StringUtils.isEmpty(key)) {
					UIUtils.showMsg("请输入您要搜索的内容！");
				} else {
					keyword = key;
					isFromCache = false;
					goods_lv.setRefreshing();
				}
				searchAlert.dismiss();
				KeyBoardUtils.closeKeybord(search_et);
			}
		});

		searchAlert = builder.create();
		searchAlert.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				KeyBoardUtils.closeKeybord(search_et);
			}
		});
		
		Window searchWindow = searchAlert.getWindow();
		searchWindow.setWindowAnimations(R.style.anim_search);
		WindowManager.LayoutParams wl = searchWindow.getAttributes();
		searchWindow.setGravity(Gravity.TOP);
		
		
		searchAlert.setCanceledOnTouchOutside(true);
		searchAlert.show();
		searchAlert.setContentView(serachView);
		searchAlert.getWindow().setLayout(LayoutParams.FILL_PARENT, 100);
		//在最后调用此方法，目的是弹出软键盘，因为默认情况下alertDialog禁掉了软键盘，为了让弹出的软键盘不要被遮罩层遮住，要在manifest中配置android:windowSoftInputMode="adjustPan"
		searchAlert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
	}

	public void createABottomDialog() {

		if (menuAlert != null) {
			LogUtils.i("直接返回");
			menuAlert.show();
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.getRunActivity(),
				AlertDialog.THEME_HOLO_LIGHT);
		builder.setInverseBackgroundForced(true);
		LayoutInflater inflater = LayoutInflater.from(BaseActivity.getRunActivity());
		View view = inflater.inflate(R.layout.dialog_bottom_menu, null);
		final ListView menuList = (ListView) view.findViewById(R.id.menu);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		final String[] types = new String[] { "全部", "衣服", "书籍", "数码", "其它" };
		menuList.setAdapter(new ArrayAdapter<String>(UIUtils.getContext(), R.layout.listitem_bottommenu, types));

		menuAlert = builder.create();
		Window window = menuAlert.getWindow();
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		menuAlert.setCanceledOnTouchOutside(true);
		menuAlert.show();
		menuAlert.setContentView(view);
		menuAlert.getWindow().setLayout(LayoutParams.FILL_PARENT, UIUtils.dip2px(235));

		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuAlert.cancel();
			}
		});

		menuList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == 0) {
					type = TYPE_ALL;
				} else {
					type = arg2;
				}
				keyword = null;
				tv_type.setText(types[arg2]);
				goods_lv.setRefreshing();
				menuAlert.cancel();
			}
		});

	}

	/*
	 * 传入点击的textview
	 */
	public void showTopView(TextView tv) {
		switch (tv.getId()) {
		case R.id.tv_date:
			tv_date.setTextColor(UIUtils.getColor(R.color.MyThemePink));
			tv_distance.setTextColor(Color.WHITE);
			tv_price.setTextColor(Color.WHITE);
			break;
		case R.id.tv_distance:
			tv_distance.setTextColor(UIUtils.getColor(R.color.MyThemePink));
			tv_date.setTextColor(Color.WHITE);
			tv_price.setTextColor(Color.WHITE);
			break;
		case R.id.tv_price:
			tv_price.setTextColor(UIUtils.getColor(R.color.MyThemePink));
			tv_date.setTextColor(Color.WHITE);
			tv_distance.setTextColor(Color.WHITE);
			break;
		}
	}

	public void initRefresh(View view) {
		goods_lv = (PullToRefreshListView) view.findViewById(R.id.goods_lv);
		goods_lv.setEmptyView(tv_null);
		//当快速滑动时，不加载图片
		goods_lv.setOnScrollListener(new PauseOnScrollListener(MyApplication.getImageLoader(), false, true));    
		goods_lv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				getLoadingPage().show();
			}
		});
		ILoadingLayout startLabels = goods_lv.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在载入...");// 刷新时
		startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
	}
	
	
	@Override
	public void onDestroy() {
		if(goodsData!=null){
			goodsData.clear();
			goodsData=null;
		}
		super.onDestroy();
	}

}
