package com.school.shopping.vo;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

public class FriendVo implements Comparable<FriendVo>{
	
	private int uid;
	
	private String realName;
	
	private int gender;
	
	//因为获取拼音是很费时的，所以不能频繁调用，因此，每次初始化的时候就得到相应的拼音
	private String pingYin;
	
	public String getPingYin() {
		return pingYin;
	}

	public FriendVo() {
		super();
	}

	public FriendVo(int uid, String realName, int gender) {
		super();
		this.uid = uid;
		this.realName = realName;
		this.gender = gender;
		pingYin=PinyinHelper.convertToPinyinString(realName,"",PinyinFormat.WITHOUT_TONE);
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
		pingYin=PinyinHelper.convertToPinyinString(realName,"",PinyinFormat.WITHOUT_TONE);
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	@Override
	public int compareTo(FriendVo another) {
		return this.pingYin.compareToIgnoreCase(another.getPingYin());
	}
	
	

}
