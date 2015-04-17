package com.school.shopping.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.school.shopping.Config;
import com.school.shopping.R;
import com.school.shopping.entity.User;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Chat extends Activity {
	
	private TextView text;
	private EditText editText;
	public static String ip="192.168.0.105";
	public static int port=12347;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_chat);
		
		editText = (EditText) findViewById(R.id.edit);
		text = (TextView) findViewById(R.id.text);

		connect();

		findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				send();
			}
		});
	}
	
	Socket socket = null;
	BufferedWriter writer = null;
	BufferedReader reader = null;

	public void connect() {

		
		AsyncTask<Void, String, Void> read = new AsyncTask<Void, String, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					socket = new Socket(ip, port);
					writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					publishProgress("@success");
				} catch (UnknownHostException e1) {
					Toast.makeText(Activity_Chat.this, "无法建立链接", Toast.LENGTH_SHORT).show();
				} catch (IOException e1) {
					Toast.makeText(Activity_Chat.this, "无法建立链接", Toast.LENGTH_SHORT).show();
				}
				try {
					String line;
					while ((line = reader.readLine())!= null) {
						publishProgress(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(String... values) {
				if (values[0].equals("@success")) {
					Toast.makeText(Activity_Chat.this, "链接成功！", Toast.LENGTH_SHORT).show();
					
				}
				String[] temp=values[0].split(",");
				if(temp.length>=2){
					text.append(temp[0]+"说："+temp[1]+"\n");
				}
				super.onProgressUpdate(values);
			}
		};
		read.execute();

	}

	public void send() {
		//User user=Config.getCachedUser(getApplicationContext());
		try {
			text.append("我说："+editText.getText().toString()+"\n");
			writer.write("456"+","+"123,"+editText.getText().toString()+"\n");
			writer.flush();
			editText.setText("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
