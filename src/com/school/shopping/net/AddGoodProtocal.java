package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
import com.school.shopping.entity.Good;
import com.school.shopping.utils.StringUtils;

public class AddGoodProtocal extends BaseProtocol<Boolean> {
	
	
	private Good good;

	public void setGood(Good good) {
		this.good = good;
	}

	@Override
	protected String getKey() {
		
		URLParam urlParam = new URLParam(URLProtocol.ADD_A_GOOD);
		try {
			urlParam.addParam("type",good.getType());
			urlParam.addParam("price",good.getPrice());
			urlParam.addParam("newLevel",good.getNewLevel());
			urlParam.addParam("goodName",good.getGoodName());
			urlParam.addParam("isAdjust", good.getIsAdjust());
			urlParam.addParam("introduction", good.getIntroduction());
			urlParam.addParam("uid", good.getUid());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		setUrl(urlParam.getQueryStr());
		return URLProtocol.ADD_A_GOOD;
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
