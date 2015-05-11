package com.school.shopping.net;

import com.school.shopping.Config;

public class MyGoodDetailProtocal extends BaseProtocol<Boolean> {
	
	private int goodId;
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	@Override
	protected String getKey() {
		String JSONDateUrl = URLProtocol.DELETE_GOOD;
		URLParam param=new URLParam(JSONDateUrl);
		param.addParam("uid",Config.getUID());
		param.addParam("id",goodId);
		setUrl(param.getQueryStr());
		return URLProtocol.DELETE_GOOD;
	}

	@Override
	protected Boolean parseJson(String json) {
		return true;
	}

}
