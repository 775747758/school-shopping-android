package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
import com.school.shopping.entity.Good;
import com.school.shopping.utils.StringUtils;

public class UpdateGoodProtocal extends BaseProtocol<Boolean> {
	
	
	private Good good;



	public void setGood(Good good) {
		this.good = good;
	}


	private UpdateGoodProtocal(Good good) {
		super();
		this.good = good;
	}

	private static UpdateGoodProtocal protocal;
	public static UpdateGoodProtocal getInstance(Good good) {
		if (protocal == null)
			protocal = new UpdateGoodProtocal(good);
		return protocal;
	}
	
	
	@Override
	protected String getKey() {
		
		URLParam urlParam = new URLParam(URLProtocol.UPDATE_GOOD);
		try {
			urlParam.addParam("type",good.getType());
			urlParam.addParam("price",good.getPrice());
			urlParam.addParam("newLevel",good.getNewLevel());
			urlParam.addParam_Encode("goodName",good.getGoodName());
			urlParam.addParam("isAdjust", good.getIsAdjust());
			urlParam.addParam_Encode("introduction", good.getIntroduction());
			urlParam.addParam("uid", good.getUid());
			urlParam.addParam("id", good.getId());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		setUrl(urlParam.getQueryStr());
		return URLProtocol.UPDATE_GOOD;
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
