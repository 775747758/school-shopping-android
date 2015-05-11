package com.school.shopping.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	public static String getMD5(String val){
		MessageDigest md5=null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md5.update(val.getBytes());
		byte[] m = md5.digest();//����
		return getString(m);
}
	private static String getString(byte[] b){
		StringBuffer sb = new StringBuffer();
		 for(int i = 0; i < b.length; i ++){
		  sb.append(b[i]);
		 }
		 return sb.toString();
}
}
