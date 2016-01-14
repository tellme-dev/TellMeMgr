package com.hotel.model;

public class Reply {
	private int id;
	private String customerName;
	private String customerPhoto;
	private String text;
	private String toCustomerName;
	private String toCustomerPhoto;
	
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
	public String getToCustomerName() {
		return toCustomerName;
	}
	public void setToCustomerName(String toCustomerName) {
		this.toCustomerName = toCustomerName;
	}
	public String getToCustomerPhoto() {
		return toCustomerPhoto;
	}
	public void setToCustomerPhoto(String toCustomerPhoto) {
		this.toCustomerPhoto = toCustomerPhoto;
	}
}
