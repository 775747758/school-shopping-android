package com.school.shopping.net;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.school.shopping.Config;
import com.school.shopping.entity.User;
import com.school.shopping.utils.LogUtils;

public class DetailUserInfoProtocol extends BaseProtocol<User> {

	@Override
	protected String getKey() {
		URLParam urlParam = new URLParam(URLProtocol.LOAD_USER_INFO);
		try {
			urlParam.addParam("uname", Config.getUname());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		LogUtils.i("DetailUserInfoProtocol：："+urlParam.getQueryStr());
		setUrl(urlParam.getQueryStr());
		return URLProtocol.LOAD_USER_INFO;
	}

	@Override
	protected User parseJson(String json) {
		User user=new User();
		JSONObject jsonObject = null;
		try {
			jsonObject =new JSONObject(json).getJSONObject("user");
			user.setCity(jsonObject.getString("city"));
			user.setGender(jsonObject.getInt("gender"));
			user.setId(jsonObject.getInt("id"));
			user.setPassword(jsonObject.getString("password"));
			user.setPhone(jsonObject.getString("phone"));
			user.setQq(jsonObject.getString("qq"));
			user.setRealName(jsonObject.getString("realName"));
			user.setSchool(jsonObject.getString("school"));
			user.setUname(jsonObject.getString("uname"));
		} catch (JSONException e) {
			LogUtils.i("cuole");
			e.printStackTrace();
		}
		return user;
	}

}
