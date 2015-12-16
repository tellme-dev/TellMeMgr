package com.hotel.modelVM;

import com.hotel.model.Hotel;
import com.hotel.model.Item;

public class ItemHotelVM {
	private Item item;
	private Hotel hotel;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
}
