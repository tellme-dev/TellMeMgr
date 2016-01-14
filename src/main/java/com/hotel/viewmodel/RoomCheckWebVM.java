package com.hotel.viewmodel;

import com.hotel.model.RoomCheck;

public class RoomCheckWebVM extends RoomCheck {
	private String customerName;
	private String customerMobile;
	private String hotelName;
	private String roomNumber;
	private String checkintime; //字符串格式的日期
	private String checkouttime;
	
	public String getCheckintime() {
		return checkintime;
	}
	public void setCheckintime(String checkintime) {
		this.checkintime = checkintime;
	}
	public String getCheckouttime() {
		return checkouttime;
	}
	public void setCheckouttime(String checkouttime) {
		this.checkouttime = checkouttime;
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
