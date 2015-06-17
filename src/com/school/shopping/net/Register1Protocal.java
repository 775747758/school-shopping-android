package com.school.shopping.net;

import java.io.UnsupportedEncodingException;

import com.school.shopping.utils.LogUtils;
public class Register1Protocal extends BaseProtocol<String> {

	
	private String uname;

	
	public void setUname(String uname) {
		this.uname = uname;
	}

	private Register1Protocal(String uname) {
		super();
		this.uname = uname;
	}

	private static Register1Protocal protocal;
	public static Register1Protocal getInstance(String uname) {
		if (protocal == null){
			protocal = new Register1Protocal(uname);
		}
		return protocal;
	}

	@Override
	protected String getKey() {
		LogUtils.i("uname::"+uname);
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
