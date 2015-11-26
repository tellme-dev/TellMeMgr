package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.Item;

public interface ItemService {

	List<Item> getItemByHotel(int hotelId);
	
	int addAndReturnId(Item item);
	
	int deleteByItemId(Map<String, Object> idMap);
}
