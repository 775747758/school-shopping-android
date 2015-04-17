package com.school.shopping.login;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.school.shopping.Config;
import com.school.shopping.MainActivity;
import com.school.shopping.R;
import com.school.shopping.data.MyPreferences;
import com.school.shopping.entity.User;
import com.school.shopping.net.URLParam;
import com.school.shopping.net.URLProtocol;
import com.school.shopping.utils.DeviceInfo;
import com.school.shopping.utils.UIUtils;
import com.school.shopping.view.MyProgressPopUpWindow;

public class Activity_Login extends Activity {

	private ToggleButton toggleAutoLogin;
	private MyPreferences myPreferences;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login1);
		
		toggleAutoLogin = (ToggleButton) findViewById(R.id.toggleAutoLogin);
		myPreferences = new MyPreferences();
		myPreferences.init(this);

		usernameEditText = (EditText) findViewById(R.id.username);
		passwordEditText = (EditText) findViewById(R.id.password);

		toggleAutoLogin.setChecked(myPreferences.getAutoLogin());

		toggleAutoLogin
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						myPreferences.setAutoLogin(isChecked);
					}

				});
		
		Intent intent=getIntent();
		if(intent.getBooleanExtra("isNotice", false)){
			UIUtils.showMsg("您的账号已经在其它设备登陆，请重新登陆！");
		}
	}

	public void register(View view) {
		Intent intent = new Intent(Activity_Login.this,
				Activity_Register1.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}

	public void next(View view) {

		String username = usernameEditText.getText().toString().trim();
		String password = passwordEditText.getText().toString().trim();

		if ("".equals(username) || "".equals(password)) {
			UIUtils.showMsg("用户名或密码不能为空！");
			return;
		}
		dialog = new MyProgressPopUpWindow(Activity_Login.this, "正在查询中...").createADialog();
		getJSONVolley(username,password);
	}

	// 登录
	public void getJSONVolley(final String uname, final String password) {
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		String JSONDateUrl = URLProtocol.LOGIN;
		URLParam param = new URLParam(JSONDateUrl);
		try {
			param.addParam("uname", uname);
			param.addParam("password", password);
			param.addParam("deviceId", DeviceInfo.getUniqueID());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("ert", param.getQueryStr());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.POST, param.getQueryStr(), null,
				new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						
						//1:成功，0：失败
						User user=new User();
						JSONObject userJO;
						try {
							userJO = response.getJSONObject("user");
							user.setCity(userJO.getString("city"));
							user.setGender(userJO.getInt("gender"));
							user.setId(userJO.getInt("id"));
							user.setPassword(userJO.getString("password"));
							user.setPhone(userJO.getString("phone"));
							user.setQq(userJO.getString("qq"));
							user.setRank(userJO.getString("rank"));
							user.setRealName(userJO.getString("realName"));
							user.setSchool(userJO.getString("school"));
							user.setUname(userJO.getString("uname"));
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						try {
							if("1".equals(response.getString("code")))
							{
								Config.cacheToken(getApplicationContext(), response.getString(Config.KEY_TOKEN));
								Config.cacheUser(getApplicationContext(), user);
								Intent intent = new Intent(Activity_Login.this,
										MainActivity.class);
								startActivity(intent);
								overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
							}
							else
							{
								UIUtils.showMsg("用户名或密码错误，请重新输入！");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						dialog.dismiss();
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(
							com.android.volley.VolleyError arg0) {
						UIUtils.showMsg("服务器出现问题，我们会尽快解决！");
						dialog.dismiss();
					}
				});
		requestQueue.add(jsonObjectRequest);
	}

}
