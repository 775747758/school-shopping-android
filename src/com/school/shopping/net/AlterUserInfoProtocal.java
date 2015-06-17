package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.webkit.URLUtil;

import com.school.shopping.Config;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;

public class AlterUserInfoProtocal extends BaseProtocol<Boolean> {
	

	private List<Map<String, String>> data;

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	private static AlterUserInfoProtocal protocal;

	public static AlterUserInfoProtocal getInstance() {
		if (protocal == null)
			protocal = new AlterUserInfoProtocal();
		return protocal;
	}
	
	private AlterUserInfoProtocal() {
		super();
	}

	@Override
	protected String getKey() {
		if (data == null || data.size() == 0) {
			return URLProtocol.ALTER_USER;
		}
		URLParam urlParam = new URLParam(URLProtocol.ALTER_USER);
		try {
			for (int i = 0; i < data.size(); i++) {
				String alterType=data.get(i).get("alterType");
				if ("int".equals(data.get(i).get("paramType"))) {
					urlParam.addParam(data.get(i).get("alterType"), Integer.parseInt(data.get(i).get("value")));
				} else {
					if("realName".equals(alterType)||"city".equals(alterType)||"province".equals(alterType)||"school".equals(alterType)){
						urlParam.addParam_Encode(data.get(i).get("alterType"), data.get(i).get("value"));
					}else{
						urlParam.addParam(data.get(i).get("alterType"), data.get(i).get("value"));
					}
				}
			}
			urlParam.addParam("id", Config.getUID());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		LogUtils.i("AlterUserInfoProtocol：：" + urlParam.getQueryStr());
		setUrl(urlParam.getQueryStr());
		return URLProtocol.ALTER_USER;
	}

	@Override
	protected Boolean parseJson(String json) {
		if (StringUtils.isEmpty(json)) {
			return false;
		} else {
			return true;
		}

	}

}
