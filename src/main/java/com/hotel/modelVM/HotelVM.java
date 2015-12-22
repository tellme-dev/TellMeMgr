package com.hotel.modelVM;

import java.util.List;

import com.hotel.model.Hotel;

public class HotelVM extends Hotel {
	private List<ItemVM> itemVMs;
	private Integer roomId;
	
	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public List<ItemVM> getItemVMs() {
		return itemVMs;
	}

	public void setItemVMs(List<ItemVM> itemVMs) {
		this.itemVMs = itemVMs;
	}
	
}
