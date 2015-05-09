package com.school.shopping.entity;

public class Province {
	
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
	}
	@Override
	public String toString() {
		return "Province [id=" + id + ", proName=" + proName + "]";
	}
	
	

}
