package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.Item;
import com.hotel.modelVM.ItemVM;

public interface ItemService {

	List<Item> getItemByHotel(int hotelId);
	
	int addAndReturnId(Item item);
	
	int deleteByItemId(Map<String, Object> idMap);
	
	Item getItemById(Integer itemId);

	ItemVM getItemVMById(Integer itemId);
	
	int countByItemTagChild(int tagId);
	
	List<Item> selectByItemTagChildOrderByScore(Map<String, Object> map);
	
	List<Item> selectByItemTagRootAndHotel(Map<String, Object> map);

	List<ItemVM> getItemVMByChildTagId(int childTagId, int num);
	
	List<Item> selectItemByHotelAndTagName(Map<String, Object> map);
	
	Item selectByPrimaryKey(Integer id);

	List<ItemVM> getItemVMByTagId(int tagId, int num);
}
