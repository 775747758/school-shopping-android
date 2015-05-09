package com.school.shopping.holder;

import java.io.UnsupportedEncodingException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.school.shopping.MyApplication;
import com.school.shopping.R;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.CircleImageView;
import com.school.shopping.vo.FriendVo;

public class FriendHolder extends BaseHolder<FriendVo> {

	private CircleImageView portrait;
	private TextView name;
	@Override
	public void refreshView(FriendVo friend) {
		name.setText(friend.getRealName());
		URLParam paramUser = new URLParam(URLProtocol.DOWNLOAD_USER_PORTRAIT);
		try {
			paramUser.addParam("filename", "user_"+friend.getUid()+".jpg");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i("URI", paramUser.getQueryStr());
		//MyApplication.getImageLoader().displayImage(paramUser.getQueryStr(), portrait,MyApplication.getOptions());
	}

	@Override
	public View initView() {
		this.view= UIUtils.inflate(R.layout.listitem_friends);
		name = (TextView) view.findViewById(R.id.name);
		portrait=(CircleImageView)view.findViewById(R.id.portrait);
		return this.view;
	}

}
