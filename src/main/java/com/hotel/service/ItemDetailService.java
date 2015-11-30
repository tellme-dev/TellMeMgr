package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.ItemDetail;

public interface ItemDetailService {

	int batchInsert(List<ItemDetail> list);
	
	int deleteByItemId(Map<String, Object> idMap);
	
	List<ItemDetail> selectByItemId(int itemId);
}
