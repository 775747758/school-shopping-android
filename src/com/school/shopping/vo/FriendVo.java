package com.school.shopping.vo;

public class FriendVo {
	
	private int uid;
	
	private String realName;
	
	private int gender;
	
	

	public FriendVo() {
		super();
	}

	public FriendVo(int uid, String realName, int gender) {
		super();
		this.uid = uid;
		this.realName = realName;
		this.gender = gender;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
	
	

}
