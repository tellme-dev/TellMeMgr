package com.hotel.viewmodel;

import com.hotel.model.RoomCheck;

public class RoomCheckWebVM extends RoomCheck {
	private String customerName;
	private String customerMobile;
	private String hotelName;
	private String roomNumber;
	private String checkInTime; //字符串格式的日期
	private String checkOutTime;
	
	public String getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getCustomerName (){
		return customerName;
	}
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
	
	public String getCustomerMobile(){
		return customerMobile;
	}
	
	public void setCustomerMobile(String customerMobile){
		this.customerMobile = customerMobile;
	}

}
