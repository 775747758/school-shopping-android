package com.school.shopping.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.school.shopping.entity.Good;

import android.os.Bundle;
import android.os.Handler;

public class HomeProtocal extends BaseProtocol<Map<String, Object>> {

	String[] typeAry = { "衣服", "书籍", "数码", "杂货铺" };
	
	
	
	private HomeProtocal() {
		super();
	}

	private static HomeProtocal protocal;

	public static HomeProtocal getInstance() {
		if (protocal == null)
			protocal = new HomeProtocal();
		return protocal;
	}

	@Override
	protected String getKey() {
		return URLProtocol.GET_HOME_GOODS;
	}

	@Override
	protected Map<String, Object> parseJson(String json) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		try {
			JSONObject jsonObject = new JSONObject(json);
			for (int i = 0; i < typeAry.length; i++) {
				String temp = jsonObject.getString(typeAry[i]);
				map.put(typeAry[i], getListFromJson(temp));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}

	public List<Good> getListFromJson(String json) {
		List<Good> goodsData = new ArrayList<Good>();
		JSONArray tempArr = null;
		try {
			tempArr = new JSONArray(json);

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
			e.printStackTrace();
			return null;
		}
		return goodsData;
	}

}
