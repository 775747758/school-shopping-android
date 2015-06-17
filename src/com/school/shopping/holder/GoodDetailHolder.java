package com.school.shopping.holder;

import io.rong.imkit.RongIM;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.meg7.widget.CustomShapeImageView;
import com.school.shopping.Activity_ShowImage;
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.me.Activity_AddGood;
import com.school.shopping.me.Activity_MyGoods;
import com.school.shopping.net.MyGoodDetailProtocal;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.vo.GoodVo;

public class GoodDetailHolder extends BaseHolder<GoodVo> implements OnClickListener {

	private ImageView iv_goodpic;
	private CustomShapeImageView portrait;
	private TextView tv_price;
	private TextView tv_introduction;
	private TextView tv_isAdjust;
	private TextView tv_newLevel;
	private TextView tv_goodName;
	private ImageView iv_chat;
	private boolean isMyGoods;
	private ImageView iv_delete;
	private AlertDialog dialog;
	private Activity activity;
	private ImageView iv_edit;
	private ImageView sv_menu_right;
	private GoodVo good;
	private URLParam paramGood;

	public static final String[] newLevels = { "七成新", "八成新", "九成新", "全新" };

	public void setIsMyGoods(boolean isMyGoods) {
		this.isMyGoods = isMyGoods;
		LogUtils.i("isMyGoods::" + isMyGoods);
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void refreshView(final GoodVo good) {

		this.good=good;
		if (isMyGoods) {
			iv_delete.setVisibility(View.VISIBLE);
			iv_edit.setVisibility(View.VISIBLE);
		} else {
			portrait.setVisibility(View.VISIBLE);
			iv_chat.setVisibility(View.VISIBLE);
		}

		sv_menu_right.setOnClickListener(this);
		iv_edit.setOnClickListener(this);
		iv_chat.setOnClickListener(this);
		iv_delete.setOnClickListener(this);
		iv_goodpic.setOnClickListener(this);
		
		tv_goodName.setText(good.getGoodName());
		tv_newLevel.setText(newLevels[good.getNewLevel()-7]);
		tv_introduction.setText(good.getIntroduction());
		tv_price.setText("¥" + good.getPrice());
		tv_introduction.setText("宝贝简介：" + good.getIntroduction());
		tv_isAdjust.setText(good.getIsAdjust() == 1 ? "可以小刀" : "不可小刀");

		paramGood = new URLParam(URLProtocol.DOWNLOAD_DOOD_PIC);
		URLParam paramUser = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			paramGood.addParam("filename", "good_" + good.getUid() + "_" + good.getId() + ".jpg");
			paramUser.addParam("filename", "user_" + good.getUid() + ".jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i("URI", paramUser.getQueryStr());
		Log.i("URI", paramGood.getQueryStr());
		//先将之前的缓存清理掉
		MyApplication.deleteUILCache(paramGood.getQueryStr());
		MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(), portrait);
		MyApplication.getImageLoader().displayImage(paramGood.getQueryStr(), iv_goodpic);
	}

	@Override
	public View initView() {
		this.view = UIUtils.inflate(R.layout.activity_gooddetail);
		iv_goodpic = (ImageView) view.findViewById(R.id.iv_goodpic);
		tv_price = (TextView) view.findViewById(R.id.tv_price);
		tv_introduction = (TextView) view.findViewById(R.id.tv_introduction);
		tv_isAdjust = (TextView) view.findViewById(R.id.tv_isAdjust);
		tv_newLevel = (TextView) view.findViewById(R.id.tv_newLevel);
		tv_goodName = (TextView) view.findViewById(R.id.tv_goodName);
		portrait = (CustomShapeImageView) view.findViewById(R.id.portrait);
		iv_chat = (ImageView) view.findViewById(R.id.iv_chat);
		iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
		iv_edit = (ImageView) view.findViewById(R.id.iv_edit);
		sv_menu_right = (ImageView) view.findViewById(R.id.sv_menu_right);
		return this.view;
	}

	public void createConfirmDialog(final int id) {
		dialog = new AlertDialog.Builder(activity).create();
		dialog.show();
		dialog.getWindow().setContentView(R.layout.dialog_confirm);
		dialog.getWindow().findViewById(R.id.remain).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteGood(id);
				dialog.cancel();

			}
		});
		dialog.getWindow().findViewById(R.id.noremain).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
	}

	private void deleteGood(final int id) {
		ThreadManager.getLongPool().execute(new Runnable() {

			@Override
			public void run() {
				MyGoodDetailProtocal protocal = MyGoodDetailProtocal.getInstance(id);
				protocal.setGoodId(id);
				protocal.load(-1, -1, false);
				UIUtils.runInMainThread(new Runnable() {
					@Override
					public void run() {
						UIUtils.showMsg("删除成功！");
						Config.removeSDCache(URLProtocol.GET_MY_GOODS);
						Intent intent = new Intent(UIUtils.getContext(), Activity_MyGoods.class);
						UIUtils.startActivity(intent);
						BaseActivity.finishRunActivity();
					}
				});
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_edit:
			Intent intent = new Intent(BaseActivity.getRunActivity(), Activity_AddGood.class);
			intent.putExtra("from", "Activity_GoodDetail");
			Bundle bundle = new Bundle();
			bundle.putParcelable("GoodVo", good);
			intent.putExtras(bundle);
			UIUtils.startActivity(intent);
			BaseActivity.getRunActivity().finish();
			break;
		case R.id.iv_chat:
			RongIM.getInstance().startPrivateChat(BaseActivity.getRunActivity(), good.getUname(), "标题");
			break;
		case R.id.iv_delete:
			createConfirmDialog(good.getId());
			break;
		case R.id.sv_menu_right:
			BaseActivity.finishRunActivity();
			break;
		case R.id.iv_goodpic:
			if(MyApplication.getImageLoader().getDiskCache().get(paramGood.getQueryStr())!=null){
				if(MyApplication.getImageLoader().getDiskCache().get(paramGood.getQueryStr()).exists()){
					Intent intent2 = new Intent(BaseActivity.getRunActivity(),Activity_ShowImage.class);
					intent2.putExtra("filePath", MyApplication.getImageLoader().getDiskCache().get(paramGood.getQueryStr()).getAbsolutePath());
					UIUtils.startActivity(intent2);
				}
			}
			break;
		}

	}

}
