package com.hotel.service;

import java.util.List;

import com.hotel.model.Item;
import com.hotel.modelVM.ItemVM;

public interface ItemService {

	List<Item> getItemByHotel(int hotelId);

	Item getItemById(Integer itemId);

	ItemVM getItemVMById(Integer itemId);
}
