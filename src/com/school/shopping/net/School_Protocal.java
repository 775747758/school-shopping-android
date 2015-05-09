package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import com.school.shopping.Config;
import com.school.shopping.utils.LogUtils;

public class School_Protocal extends BaseProtocol<List<String>> {

	

	@Override
	protected String getKey() {
		String JSONDateUrl =URLProtocol.GET_UNIVERSITY;
		URLParam param = new URLParam(JSONDateUrl);
		try {
			param.addParam_Encode("query", "大学");
			param.addParam_Encode("region", Config.getCachedUser().getCity());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setUrl(param.getQueryStr());
		return URLProtocol.GET_UNIVERSITY;
	}

	@Override
	protected List<String> parseJson(String json) {
		LogUtils.i("jsonSchool_Pro::"+json );
		 List<String> schoolList=new ArrayList<String>();
		try {
			JSONObject jsonObject=new JSONObject(json);
			int status = jsonObject.getInt("status");
			if (status == 0) {
				String schoolStr=jsonObject.get("results").toString();
				Log.i("School_Protocal::school", schoolStr);
				JSONArray schoolArr=new JSONArray(schoolStr);
				for(int i=0;i<schoolArr.length();i++){
					JSONObject school=schoolArr.getJSONObject(i);
					String name=school.getString("name");
					LogUtils.i("校园名字："+name);
					schoolList.add(name);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return schoolList;
	}

}
