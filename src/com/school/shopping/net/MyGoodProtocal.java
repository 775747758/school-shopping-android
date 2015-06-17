package com.school.shopping.net;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.school.shopping.Config;
import com.school.shopping.entity.Good;
import com.school.shopping.utils.LogUtils;

public class MyGoodProtocal extends BaseProtocol<List<Good>> {
	
	


	private int uid;
	

	public void setUid(int uid) {
		this.uid = uid;
	}

	private MyGoodProtocal(int uid) {
		super();
		this.uid = uid;
	}

	private static MyGoodProtocal protocal;
	public static MyGoodProtocal getInstance(int uid) {
		if (protocal == null)
			protocal = new MyGoodProtocal(uid);
		return protocal;
	}

	@Override
	protected String getKey() {
		String JSONDateUrl = URLProtocol.GET_MY_GOODS;
		URLParam param = new URLParam(JSONDateUrl);
		param.addParam("uid", uid);
		setUrl(param.getQueryStr());
		return URLProtocol.GET_MY_GOODS;
	}

	@Override
	protected List<Good> parseJson(String json) {
		List<Good> goodsData = new ArrayList<Good>();
		JSONArray tempArr = null;
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			tempArr = jsonObject.getJSONArray("goods");
			for (int i = 0; i < tempArr.length(); i++) {
				JSONObject jso = new JSONObject(tempArr.get(i).toString());
				Good good = new Good();
				good.setGoodName(jso.getString("goodName"));
				good.setPrice(jso.getString("price"));
				good.setType(jso.getInt("type"));
				good.setIsAdjust(jso.getInt("isAdjust"));
				good.setNewLevel(jso.getInt("newLevel"));
				good.setIntroduction(jso.getString("introduction"));
				good.setUid(jso.getInt("uid"));
				good.setId(jso.getInt("id"));
				goodsData.add(good);
			}
		} catch (JSONException e) {
			LogUtils.i("cuole");
			e.printStackTrace();
		}
		return goodsData;
	}

}
