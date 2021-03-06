package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.school.shopping.Config;
import com.school.shopping.entity.Good;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.utils.StringUtils;
import com.school.shopping.vo.GoodVo;

public class ShowGoodProtocal extends BaseProtocol<List<GoodVo>> {

	private String sortType;
	private String order;
	private int type = -1;
	private int isAdjust = -1;
	private String city = null;
	private String keyword = null;
	private String province=null;
	
	private ShowGoodProtocal() {
		super();
	}

	private static ShowGoodProtocal protocal;
	public static ShowGoodProtocal getInstance() {
		if (protocal == null)
			protocal = new ShowGoodProtocal();
		return protocal;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}


	public void setOrder(String order) {
		this.order = order;
	}


	public void setSortType(String type) {
		sortType = type;
	}


	public int getIsAdjust() {
		return isAdjust;
	}

	public void setIsAdjust(int isAdjust) {
		this.isAdjust = isAdjust;
	}

	

	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	protected String getKey() {
		URLParam urlParam = new URLParam(URLProtocol.GET_GOODS_BY_TYPE);
		urlParam.addParam("type", type);
		urlParam.addParam("isAdjust", isAdjust);
		try {
			if(sortType.equals("distance")){
				String lontitude=Config.getLontitude();
				String latitude=Config.getLatitude();
				if(!StringUtils.isEmpty(lontitude)&&!StringUtils.isEmpty(latitude)){
					urlParam.addParam("mLocation", latitude+","+lontitude);
				}
			}
			if (city != null && !"".equals(city)) {
				urlParam.addParam_Encode("city", city);
			}
			if (keyword != null && !"".equals(keyword)) {
				urlParam.addParam_Encode("keyword", keyword);
			}
			if(!StringUtils.isEmpty(sortType)){
				urlParam.addParam("sortType", sortType);
			}
			if(!StringUtils.isEmpty(order)){
				urlParam.addParam("order", order);
			}
			if(!StringUtils.isEmpty(province)){
				urlParam.addParam_Encode("province", province);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		LogUtils.i("showGoodURl：："+urlParam.getQueryStr());
		setUrl(urlParam.getQueryStr());
		return URLProtocol.GET_GOODS_BY_TYPE;
	}

	@Override
	protected List<GoodVo> parseJson(String json) {
		List<GoodVo> goodsData = new ArrayList<GoodVo>();
		JSONArray tempArr = null;
		JSONObject jsonObject = null;
		try {
			Log.i("sdf", "showGoor::" + json.length());
			jsonObject = new JSONObject(json);
			tempArr = jsonObject.getJSONArray("goods");
			LogUtils.i("showGoor::" + tempArr.length());
			Log.i("sdf", "showGoor::" + tempArr.length());
			for (int i = 0; i < tempArr.length(); i++) {
				JSONObject jso = new JSONObject(tempArr.get(i).toString());
				GoodVo goodvo = new GoodVo();
				goodvo.setGoodName(jso.getString("goodName"));
				goodvo.setPrice(jso.getString("price"));
				goodvo.setType(jso.getInt("type"));
				goodvo.setIsAdjust(jso.getInt("isAdjust"));
				goodvo.setNewLevel(jso.getInt("newLevel"));
				LogUtils.i("newLevel::"+jso.getInt("newLevel"));
				goodvo.setIntroduction(jso.getString("introduction"));
				goodvo.setUid(jso.getInt("uid"));
				goodvo.setId(jso.getInt("id"));
				goodvo.setLatitude(jso.getString("latitude"));
				goodvo.setLongitude(jso.getString("longitude"));
				goodvo.setUname(jso.getString("uname"));
				goodsData.add(goodvo);
			}
		} catch (JSONException e) {
			LogUtils.i("cuole");
			e.printStackTrace();
		}
		LogUtils.i("pro:test::" + goodsData.size());
		return goodsData;
	}

	
}
