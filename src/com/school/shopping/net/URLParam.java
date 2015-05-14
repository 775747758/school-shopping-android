package com.school.shopping.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLParam {

	public StringBuffer _query=new StringBuffer();
	
	public URLParam(String query){
		if(null!=query){
			_query.append(query);
		}
	}
	
	public void addParam(String name,String value) throws UnsupportedEncodingException{
		
		if(_query.toString().contains("?"))
		{
			_query.append('&');
		}
		else{
			_query.append('?');
		}
		_query.append(name).append('=').append(URLDecoder.decode(value, "UTF-8"));
	}
	
	public void addParam_Encode(String name,String value) throws UnsupportedEncodingException{
		
		if(_query.toString().contains("?"))
		{
			_query.append('&');
		}
		else{
			_query.append('?');
		}
		_query.append(name).append('=').append(URLEncoder.encode(URLEncoder.encode(value, "UTF-8"), "UTF-8"));
		//_query.append(name).append('=').append(URLEncoder.encode(value, "UTF-8"));
	}
	
	public void addParam(String name ,int value){
		if(_query.toString().contains("?"))
		{
			_query.append('&');
		}
		else{
			_query.append('?');
		}
		_query.append(name).append('=').append(value+"");
		
	}
	
	//��ò�ѯ�ַ�
	public String getQueryStr(){
		return _query.toString();
	}
	
}
