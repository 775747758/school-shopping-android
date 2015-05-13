package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
public class Register1Protocal extends BaseProtocol<String> {

	
	private String uname;
	
	public void setUname(String uname) {
		this.uname = uname;
	}

	@Override
	protected String getKey() {
		URLParam urlParam = new URLParam(URLProtocol.CHECK_USER_EXIST);
		try {
			urlParam.addParam("uname", uname);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setUrl(urlParam.getQueryStr());
		return URLProtocol.CHECK_USER_EXIST;
	}

	@Override
	protected String parseJson(String json) {
		return json;
	}

}
