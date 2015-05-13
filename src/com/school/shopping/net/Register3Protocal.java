package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.school.shopping.Config;
import com.school.shopping.entity.User;
import com.school.shopping.utils.DeviceInfo;
import com.school.shopping.utils.LogUtils;

public class Register3Protocal extends BaseProtocol<String> {
	
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	protected String getKey() {
		URLParam urlParam = new URLParam(URLProtocol.REGISTER);
		
		try {
			urlParam.addParam("uname",user.getUname());
			urlParam.addParam("password",user.getPassword());
			urlParam.addParam("city",user.getCity());
			urlParam.addParam("school",user.getSchool());
			urlParam.addParam("realName",user.getRealName());
			urlParam.addParam("phone",user.getPhone());
			urlParam.addParam("qq",user.getQq());
			urlParam.addParam("gender",user.getGender());
			urlParam.addParam("deviceId", user.getDeviceId());
			urlParam.addParam("school", user.getSchool());
			//urlParam.addParam("longitude", user.getLongitude());
			//urlParam.addParam("latitude", user.getLatitude());
			urlParam.addParam("longitude", "109.85384");
			urlParam.addParam("latitude", "40.661788");
			urlParam.addParam("province", user.getProvince());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setUrl(urlParam.getQueryStr());
		return URLProtocol.REGISTER;
	}

	@Override
	protected String parseJson(String json) {
		try {
			JSONObject jsonObject=new JSONObject(json);
			user.setId(jsonObject.getInt("uid"));
			Config.cacheToken(jsonObject.getString(Config.KEY_TOKEN));
			Config.cacheUser(user);
		} catch (JSONException e) {
			LogUtils.i("register3pr::cuole");
			e.printStackTrace();
		}
		return json;
	}

}
