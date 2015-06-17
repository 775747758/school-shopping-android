package com.school.shopping.net;

import com.school.shopping.Config;

public class MyGoodDetailProtocal extends BaseProtocol<Boolean> {
	
	private static MyGoodDetailProtocal protocal;

	public static MyGoodDetailProtocal getInstance(int goodId) {
		if (protocal == null)
			protocal = new MyGoodDetailProtocal(goodId);
		return protocal;
	}
	
	private int goodId;
	

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	private MyGoodDetailProtocal(int goodId) {
		super();
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
