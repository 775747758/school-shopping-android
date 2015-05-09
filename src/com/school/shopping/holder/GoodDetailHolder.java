package com.school.shopping.holder;

import io.rong.imkit.RongIM;

import java.io.UnsupportedEncodingException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.meg7.widget.CustomShapeImageView;
import com.school.shopping.BaseActivity;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.entity.Good;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
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
	@Override
	public void refreshView(final GoodVo good) {
		
		
		//Good good = getData();
		
		tv_goodName.setText(good.getGoodName());
		LogUtils.i("tv_goodName::"+tv_goodName.getText().toString());
		tv_isAdjust.setText(good.getIsAdjust()+"");
		tv_newLevel.setText(good.getNewLevel()+"");
		tv_price.setText(good.getPrice());
		tv_introduction.setText(good.getIntroduction());
		
		iv_chat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtils.i("和它聊天：："+good.getUname());
				RongIM.getInstance().startPrivateChat(BaseActivity.getRunActivity(),good.getUname()/*接受者的 userId*/,"标题"/*该聊天的标题*/);
			}
		});
		
		URLParam paramGood = new URLParam(URLProtocol.DOWNLOAD_DOOD_PIC);
		URLParam paramUser = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			paramGood.addParam("filename", "good_"+good.getUid()+"_"+good.getId()+".jpg");
			paramUser.addParam("filename", "user_"+good.getUid()+".jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i("URI", paramUser.getQueryStr());
		Log.i("URI", paramGood.getQueryStr());
		MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(), portrait,MyApplication.getOptions());
		MyApplication.getImageLoader().displayImage(paramGood.getQueryStr(), iv_goodpic,MyApplication.getOptions());
		
		//ImageLoader.getInstance().displayImage(paramGood.getQueryStr(), holder.goodbg_iv, options, animateFirstListener);
		
	}

	@Override
	public View initView() {
		this.view= UIUtils.inflate(R.layout.activity_gooddetail);
		iv_goodpic = (ImageView) view.findViewById(R.id.iv_goodpic);
		tv_price = (TextView) view.findViewById(R.id.tv_price);
		tv_introduction = (TextView) view.findViewById(R.id.tv_introduction);
		tv_isAdjust = (TextView) view.findViewById(R.id.tv_isAdjust);
		tv_newLevel = (TextView) view.findViewById(R.id.tv_newLevel);
		tv_goodName = (TextView) view.findViewById(R.id.tv_goodName);
		portrait=(CustomShapeImageView)view.findViewById(R.id.portrait);
		iv_chat=(ImageView) view.findViewById(R.id.iv_chat);
		
		return this.view;
	}

}
