package com.hotel.modelVM;

import com.hotel.model.Hotel;
import com.hotel.model.Item;
import com.hotel.model.ItemDetail;

public class ItemHotelVM {
	private Item item;
	private Hotel hotel;
	private ItemDetail itemDetail;
	
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
	public ItemDetail getItemDetail() {
		return itemDetail;
	}
	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
	}
}
