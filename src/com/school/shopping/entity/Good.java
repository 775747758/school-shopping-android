package com.school.shopping.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Good implements Parcelable {

	
	private int id;
	private String goodName;
	private int type;
	private String price;
	private int isAdjust;//�Ƿ����С��
	private int newLevel;//�¾ɳ̶�
	private String introduction;//���
	private int uid;//�û�id
	

	public Good() {
		super();
	}
	
	// 带参构造器方法私用化，本构造器仅供类的方法createFromParcel调用  
    private Good(Parcel source) {  
    	id = source.readInt();  
    	type = source.readInt();
    	isAdjust = source.readInt();  
    	newLevel = source.readInt();
    	uid = source.readInt();
    	goodName=source.readString();
    	price=source.readString();
    	introduction=source.readString();
    }  
    
	
    
    
	public Good(int id, String goodName, int type, String price,
			int isAdjust, int newLevel, String introduction, int uid) {
		super();
		this.id = id;
		this.goodName = goodName;
		this.type = type;
		this.price = price;
		this.isAdjust = isAdjust;
		this.newLevel = newLevel;
		this.introduction = introduction;
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
	}
	
	// 必须要创建一个名叫CREATOR的常量。  
    public static final Parcelable.Creator<Good> CREATOR = new Parcelable.Creator<Good>() {  
        @Override  
        public Good createFromParcel(Parcel source) {  
            return new Good(source);  
        }  
        //重写createFromParcel方法，创建并返回一个获得了数据的GoodVo对象  
        @Override  
        public Good[] newArray(int size) {  
            return new Good[size];  
        }  
    };


	@Override
	public String toString() {
		return "Good [id=" + id + ", goodName=" + goodName + ", type=" + type + ", price=" + price + ", isAdjust="
				+ isAdjust + ", newLevel=" + newLevel + ", introduction=" + introduction + ", uid=" + uid + "]";
	}




	
	
	
}
