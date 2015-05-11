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
import com.school.shopping.BaseActivity;
import com.school.shopping.Config;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.Good;
import com.school.shopping.manager.ThreadManager;
import com.school.shopping.me.Activity_AddGood;
import com.school.shopping.me.Activity_MyGoods;
import com.school.shopping.net.MyGoodDetailProtocal;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.showgoods.Activity_GoodDetail;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.MyProgressPopUpWindow;
import com.school.shopping.vo.GoodVo;

public class GoodDetailHolder extends BaseHolder<GoodVo> {

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

	public void setIsMyGoods(boolean isMyGoods) {
		this.isMyGoods = isMyGoods;
		LogUtils.i("isMyGoods::"+isMyGoods);
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void refreshView(final GoodVo good) {

		
		if (isMyGoods) {
			iv_delete.setVisibility(View.VISIBLE);
			iv_edit.setVisibility(View.VISIBLE);
		} else {
			portrait.setVisibility(View.VISIBLE);
			iv_chat.setVisibility(View.VISIBLE);
		}
		
		iv_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(BaseActivity.getRunActivity(),Activity_AddGood.class);
				intent.putExtra("from", "Activity_GoodDetail");
				Bundle bundle=new Bundle();
				bundle.putParcelable("GoodVo", good);
				intent.putExtras(bundle);
				UIUtils.startActivity(intent);
				BaseActivity.getRunActivity().finish();
			}
		});

		tv_goodName.setText(good.getGoodName());
		tv_goodName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*MyProgressPopUpWindow dialog=new MyProgressPopUpWindow(BaseActivity.getRunActivity(), good.getGoodName());
				dialog.createADialog();*/
			}
		});
		LogUtils.i("tv_goodName::" + tv_goodName.getText().toString());
		tv_isAdjust.setText(good.getIsAdjust() + "");
		tv_newLevel.setText(good.getNewLevel() + "");
		tv_price.setText(good.getPrice());
		tv_introduction.setText(good.getIntroduction());

		iv_chat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtils.i("和它聊天：：" + good.getUname());
				RongIM.getInstance()
						.startPrivateChat(BaseActivity.getRunActivity(), good.getUname()/*
																						 * 接受者的
																						 * userId
																						 */, "标题"/* 该聊天的标题 */);
			}
		});
		
		
		iv_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				createConfirmDialog(good.getId());
			}
		});

		URLParam paramGood = new URLParam(URLProtocol.DOWNLOAD_DOOD_PIC);
		URLParam paramUser = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			paramGood.addParam("filename", "good_" + good.getUid() + "_" + good.getId() + ".jpg");
			paramUser.addParam("filename", "user_" + good.getUid() + ".jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i("URI", paramUser.getQueryStr());
		Log.i("URI", paramGood.getQueryStr());
		MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(), portrait, MyApplication.getOptions());
		MyApplication.getImageLoader().displayImage(paramGood.getQueryStr(), iv_goodpic, MyApplication.getOptions());

		// ImageLoader.getInstance().displayImage(paramGood.getQueryStr(),
		// holder.goodbg_iv, options, animateFirstListener);

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
		iv_edit=(ImageView) view.findViewById(R.id.iv_edit);
		
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
				MyGoodDetailProtocal protocal=new MyGoodDetailProtocal();
				protocal.setGoodId(id);
				protocal.load(-1, -1, false);
				UIUtils.runInMainThread(new Runnable() {
					@Override
					public void run() {
						UIUtils.showMsg("删除成功！");
						Config.removeSDCache(URLProtocol.GET_MY_GOODS);
						Intent intent=new Intent(UIUtils.getContext(),Activity_MyGoods.class);
						UIUtils.startActivity(intent);
						BaseActivity.finishRunActivity();
					}
				});
			}
		});
		
	}

}
