package com.school.shopping.entity;

import com.school.shopping.vo.FriendVo;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;

public class Province implements Comparable<Province>{
	
	private int id;
	private String proName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
		pingYin=PinyinHelper.convertToPinyinString(proName,"",PinyinFormat.WITHOUT_TONE);
	}
	@Override
	public String toString() {
		return "Province [id=" + id + ", proName=" + proName + "]";
	}
	
	
	//因为获取拼音是很费时的，所以不能频繁调用，因此，每次初始化的时候就得到相应的拼音
	private String pingYin;
	public String getPingYin() {
		return pingYin;
	}
	public void setPingYin(String pingYin) {
		this.pingYin = pingYin;
	}
	@Override
	public int compareTo(Province another) {
		return this.pingYin.compareToIgnoreCase(another.getPingYin());
	}
	
	
	

}
