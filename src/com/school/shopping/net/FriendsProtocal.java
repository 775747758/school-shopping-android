package com.school.shopping.net;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.school.shopping.utils.LogUtils;
import com.school.shopping.vo.FriendVo;

public class FriendsProtocal extends BaseProtocol<List<FriendVo>> {
	
	private static FriendsProtocal protocal;

	public static FriendsProtocal getInstance(int uid) {
		if (protocal == null)
			protocal = new FriendsProtocal(uid);
		return protocal;
	}

	private int uid;

	public void setUid(int uid) {
		this.uid = uid;
	}

	private FriendsProtocal(int uid) {
		super();
		this.uid = uid;
	}

	@Override
	protected String getKey() {
		URLParam urlParam = new URLParam(URLProtocol.GET_FRIENDS);
		urlParam.addParam("uid", uid);
		LogUtils.i("FriendsURl：："+urlParam.getQueryStr());
		setUrl(urlParam.getQueryStr());
		return URLProtocol.GET_FRIENDS;
	}

	@Override
	protected List<FriendVo> parseJson(String json) {
		List<FriendVo> friendsData = new ArrayList<FriendVo>();
		JSONArray tempArr = null;
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
			tempArr = jsonObject.getJSONArray("friends");
			for (int i = 0; i < tempArr.length(); i++) {
				JSONObject jso = new JSONObject(tempArr.get(i).toString());
				FriendVo friendVo = new FriendVo();
				friendVo.setGender(jso.getInt("gender"));
				friendVo.setRealName(jso.getString("realName"));
				friendVo.setUid(jso.getInt("uid"));
				friendsData.add(friendVo);
			}
		} catch (JSONException e) {
			LogUtils.i("cuole");
			e.printStackTrace();
		}
		LogUtils.i("解析数据数量：："+friendsData.size());
		return friendsData;
	}

}
