package com.hotel.service;

import java.util.List;

import com.hotel.model.Item;

public interface ItemService {

	List<Item> getItemByHotel(int hotelId);
}
