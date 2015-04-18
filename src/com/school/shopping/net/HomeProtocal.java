package com.school.shopping.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.school.shopping.entity.Good;

import android.os.Bundle;
import android.os.Handler;

	public class HomeProtocal extends BaseProtocol<List<Good>>{
	
	
	@Override
	protected String getKey() {
		return URLProtocol.GET_All_GOODS;
	}
	@Override
	protected List<Good> parseJson(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			String temp = jsonObject.getString("goods");
			if (temp == null || "".equals(temp)) {
				return null;
			} else {
				JSONArray tempArr = new JSONArray(temp);
				List<Good> goodsData = new ArrayList<Good>();
				for (int i = 0; i < tempArr.length(); i++) {
					JSONObject jso = new JSONObject(tempArr.get(i).toString());
					Good good = new Good();
					good.setGoodName(jso.getString("goodName"));
					good.setPrice(jso.getString("price"));
					good.setType(jso.getString("type"));
					good.setIsAdjust(jso.getInt("isAdjust"));
					good.setNewLevel(jso.getString("newLevel"));
					good.setIntroduction(jso.getString("introduction"));
					good.setUid(jso.getInt("uid"));
					good.setId(jso.getInt("id"));
					goodsData.add(good);

				}
				return goodsData;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;

		}
	}

}
