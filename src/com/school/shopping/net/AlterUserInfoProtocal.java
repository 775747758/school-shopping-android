package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.webkit.URLUtil;

import com.school.shopping.Config;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;

public class AlterUserInfoProtocal extends BaseProtocol<Boolean> {
	
	private Map<String, String> map;
	

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	@Override
	protected String getKey() {
		
		URLParam urlParam = new URLParam(URLProtocol.ALTER_USER);
		try {
			if("int".equals(map.get("paramType"))){
				urlParam.addParam(map.get("alterType"),Integer.parseInt(map.get("value")));
			}
			else{
				urlParam.addParam(map.get("alterType"),map.get("value"));
			}
			urlParam.addParam("id", Config.getUID());
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		LogUtils.i("AlterUserInfoProtocol：："+urlParam.getQueryStr());
		setUrl(urlParam.getQueryStr());
		return URLProtocol.ALTER_USER;
	}

	@Override
	protected Boolean parseJson(String json) {
		if(StringUtils.isEmpty(json)){
			return false;
		}
		else{
			return true;
		}
		
	}

}
