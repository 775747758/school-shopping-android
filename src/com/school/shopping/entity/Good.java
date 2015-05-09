package com.school.shopping.entity;

public class Good {
	private int id;
	private String goodName;
	private int type;
	private String price;
	private int isAdjust;//
	private int newLevel;//是否可以调整价格
	private String introduction;//商品介绍
	private int uid;//用户id
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public int getType() {
		return type;
	}
	public void setType(int i) {
		this.type = i;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getIsAdjust() {
		return isAdjust;
	}
	public void setIsAdjust(int isAdjust) {
		this.isAdjust = isAdjust;
	}
	public int getNewLevel() {
		return newLevel;
	}
	public void setNewLevel(int newLevel) {
		this.newLevel = newLevel;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@Override
	public String toString() {
		return "Good [id=" + id + ", goodName=" + goodName + ", type=" + type
				+ ", price=" + price + ", isAdjust=" + isAdjust + ", newLevel="
				+ newLevel + ", introduction=" + introduction + ", uid=" + uid
				+ "]";
	}
	
	
	
}
