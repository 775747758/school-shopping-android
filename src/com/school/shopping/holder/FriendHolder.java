package com.school.shopping.holder;

import java.io.UnsupportedEncodingException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.CircleImageView;
import com.school.shopping.vo.FriendVo;

public class FriendHolder extends BaseHolder<FriendVo> {

	private CircleImageView portrait;
	private TextView name;
	private TextView tv_index;
	
	private String currentFirstWord;
	private String lastFirstWord;
	
	

	public void setCurrentFirstWord(String currentFirstWord) {
		this.currentFirstWord = currentFirstWord;
	}

	public void setLastFirstWord(String lastFirstWord) {
		this.lastFirstWord = lastFirstWord;
	}

	public TextView getTv_index() {
		return tv_index;
	}

	@Override
	public void refreshView(FriendVo friend) {
		if(lastFirstWord==null){
			tv_index.setVisibility(View.VISIBLE);
			tv_index.setText(currentFirstWord.toUpperCase());
		}else{
			if(currentFirstWord.equalsIgnoreCase(lastFirstWord)){
				tv_index.setVisibility(View.GONE);
			}else{
				tv_index.setVisibility(View.VISIBLE);
				tv_index.setText(currentFirstWord.toUpperCase());
			}
		}
		LogUtils.i("tv_index:::::"+tv_index.getText().toString());
		
		//tv_index.setText(friend.getPingYin().substring(0, 1).toUpperCase());
		name.setText(friend.getRealName());
		URLParam paramUser = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			paramUser.addParam("filename", "user_"+friend.getUid()+".jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(), portrait, MyApplication.getOptions());
	}

	@Override
	public View initView() {
		this.view= UIUtils.inflate(R.layout.listitem_friends);
		name = (TextView) view.findViewById(R.id.name);
		portrait=(CircleImageView)view.findViewById(R.id.portrait);
		tv_index=(TextView) view.findViewById(R.id.tv_index);
		return this.view;
	}

}
