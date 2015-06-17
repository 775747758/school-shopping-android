package com.school.shopping.entity;

public class User {

	private int id;
	private String uname;
	private String password;
	private String phone;
	private String realName;
	private String school;
	private String rank;
	private String qq;
	private int gender;
	private String city;
	private String deviceId;
	
	private String longitude;
	private String latitude;
	private String province;
	
	private int isShowQq;
	private int isShowPhone;
	
	
	
	
	
	public User(int id, String uname, String password, String phone, String realName, String school, String rank,
			String qq, int gender, String city, String deviceId, String longitude, String latitude, String province,
			int isShowQq, int isShowPhone) {
		super();
		this.id = id;
		this.uname = uname;
		this.password = password;
		this.phone = phone;
		this.realName = realName;
		this.school = school;
		this.rank = rank;
		this.qq = qq;
		this.gender = gender;
		this.city = city;
		this.deviceId = deviceId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.province = province;
		this.isShowQq = isShowQq;
		this.isShowPhone = isShowPhone;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", uname=" + uname + ", password=" + password + ", phone=" + phone + ", realName="
				+ realName + ", school=" + school + ", rank=" + rank + ", qq=" + qq + ", gender=" + gender + ", city="
				+ city + ", deviceId=" + deviceId + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", province=" + province + ", isShowQq=" + isShowQq + ", isShowPhone=" + isShowPhone + "]";
	}

	public int getIsShowQq() {
		return isShowQq;
	}

	public void setIsShowQq(int isShowQq) {
		this.isShowQq = isShowQq;
	}

	public int getIsShowPhone() {
		return isShowPhone;
	}

	public void setIsShowPhone(int isShowPhone) {
		this.isShowPhone = isShowPhone;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public User(){
		super();
	}

	


	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	

	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}




	
	
}
