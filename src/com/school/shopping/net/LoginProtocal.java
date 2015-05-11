package com.school.shopping.net;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.school.shopping.Config;
import com.school.shopping.entity.User;
import com.school.shopping.utils.DeviceInfo;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;

public class LoginProtocal extends BaseProtocol<User> {
	
	private String uname;
	
	private String password;
	

	public void setUname(String uname) {
		this.uname = uname;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	@Override
	protected String getKey() {
		URLParam urlParam = new URLParam(URLProtocol.LOGIN);
		try {
			urlParam.addParam("uname", uname);
			urlParam.addParam("password", password);
			urlParam.addParam("deviceId", DeviceInfo.getUniqueID());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		LogUtils.i(urlParam.getQueryStr());
		setUrl(urlParam.getQueryStr());
		return URLProtocol.LOGIN;
	}

	@Override
	protected User parseJson(String json) {
		if(!StringUtils.isEmpty(json)){
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
				//因为user里没有token这个字段，所以就在这里存储起来
				Config.cacheToken(new JSONObject(json).getString(Config.KEY_TOKEN));
			} catch (JSONException e) {
				LogUtils.i("cuole");
				e.printStackTrace();
			}
			return user;
		}
		else{
			return null;
		}
	}

}
