package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.school.shopping.Config;
import com.school.shopping.entity.User;
import com.school.shopping.utils.LogUtils;

public class SchoolProtocal extends BaseProtocol<List<String>> {
	
	private static SchoolProtocal protocal;
	public static SchoolProtocal getInstance() {
		if (protocal == null)
			protocal = new SchoolProtocal();
		return protocal;
	}
	
	

	private SchoolProtocal() {
		super();
	}



	@Override
	protected String getKey() {
		URLParam urlParam = new URLParam(URLProtocol.GET_UNIVERSITY);
		try {
			urlParam.addParam("query", "大学");
			urlParam.addParam("location", Config.getLatitude() + "," + Config.getLontitude());
			//urlParam.addParam("location", "40.661788" + "," + "109.85384");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		LogUtils.i("SchoolProtocol：：" + urlParam.getQueryStr());
		setUrl(urlParam.getQueryStr());
		return URLProtocol.GET_UNIVERSITY;
	}

	@Override
	protected List<String> parseJson(String json) {
		if(json!=null){
			List<String> schoolList = new ArrayList<String>();
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(json);
				String schoolStr = jsonObject.get("results").toString();
				JSONArray schoolArr = new JSONArray(schoolStr);
				for (int i = 0; i < schoolArr.length(); i++) {
					JSONObject school = schoolArr.getJSONObject(i);
					String name = school.getString("name");
					schoolList.add(name);
				}
			} catch (JSONException e) {
				LogUtils.i("cuole");
				e.printStackTrace();
			}
			return schoolList;
		}else{
			return null;
		}
		
		
	}

}
