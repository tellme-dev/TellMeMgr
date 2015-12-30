package com.hotel.model;

public class Comment {
	private int id;
	private String customerName;
	private String customerPhoto;
	private String text;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhoto() {
		return customerPhoto;
	}
	public void setCustomerPhoto(String customerPhoto) {
		this.customerPhoto = customerPhoto;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
