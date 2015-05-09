package com.school.shopping.vo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodVo implements Parcelable {

	
	private int id;
	private String goodName;
	private int type;
	private String price;
	private int isAdjust;//�Ƿ����С��
	private int newLevel;//�¾ɳ̶�
	private String introduction;//���
	private int uid;//�û�id
	private String latitude;
	private String longitude;
	private String uname;
	
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public GoodVo() {
		super();
	}
	
	// 带参构造器方法私用化，本构造器仅供类的方法createFromParcel调用  
    private GoodVo(Parcel source) {  
    	id = source.readInt();  
    	type = source.readInt();
    	isAdjust = source.readInt();  
    	newLevel = source.readInt();
    	uid = source.readInt();
    	goodName=source.readString();
    	price=source.readString();
    	introduction=source.readString();
    	latitude=source.readString();
    	longitude=source.readString();
    	uname=source.readString();
    }  
    
	
    
    
	public GoodVo(int id, String goodName, int type, String price,
			int isAdjust, int newLevel, String introduction, int uid,
			String latitude, String longitude, String uname) {
		super();
		this.id = id;
		this.goodName = goodName;
		this.type = type;
		this.price = price;
		this.isAdjust = isAdjust;
		this.newLevel = newLevel;
		this.introduction = introduction;
		this.uid = uid;
		this.latitude = latitude;
		this.longitude = longitude;
		this.uname = uname;
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
	public void setType(int type) {
		this.type = type;
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
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) { 
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeInt(isAdjust);
        dest.writeInt(newLevel);
        dest.writeInt(uid);
        dest.writeString(goodName);
        dest.writeString(price);
        dest.writeString(introduction);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(uname);
	}
	
	// 必须要创建一个名叫CREATOR的常量。  
    public static final Parcelable.Creator<GoodVo> CREATOR = new Parcelable.Creator<GoodVo>() {  
        @Override  
        public GoodVo createFromParcel(Parcel source) {  
            return new GoodVo(source);  
        }  
        //重写createFromParcel方法，创建并返回一个获得了数据的GoodVo对象  
        @Override  
        public GoodVo[] newArray(int size) {  
            return new GoodVo[size];  
        }  
    };




	@Override
	public String toString() {
		return "GoodVo [id=" + id + ", goodName=" + goodName + ", type=" + type
				+ ", price=" + price + ", isAdjust=" + isAdjust + ", newLevel="
				+ newLevel + ", introduction=" + introduction + ", uid=" + uid
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	} 
	
	
}
