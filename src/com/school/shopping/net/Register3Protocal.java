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

	private Register3Protocal(User user) {
		super();
		this.user = user;
	}

	private static Register3Protocal protocal;
	public static Register3Protocal getInstance(User user) {
		if (protocal == null)
			protocal = new Register3Protocal(user);
		return protocal;
	}

	@Override
	protected String getKey() {
		URLParam urlParam = new URLParam(URLProtocol.REGISTER);
		
		try {
			LogUtils.i("xkan:::"+user.getUname());
			urlParam.addParam("uname",user.getUname());
			urlParam.addParam("password",user.getPassword());
			if(!Config.isOneCity()){
				urlParam.addParam_Encode("city",user.getCity());
			}
			urlParam.addParam_Encode("school",user.getSchool());
			urlParam.addParam_Encode("realName",user.getRealName());
			urlParam.addParam("phone",user.getPhone());
			urlParam.addParam("qq",user.getQq());
			urlParam.addParam("gender",user.getGender());
			urlParam.addParam("deviceId", user.getDeviceId());
			urlParam.addParam("longitude", user.getLongitude());
			urlParam.addParam("latitude", user.getLatitude());
			//urlParam.addParam("longitude", "109.85384");
			//urlParam.addParam("latitude", "40.661788");
			urlParam.addParam_Encode("province", user.getProvince());
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
