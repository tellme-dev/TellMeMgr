package com.hotel.service;

import com.hotel.model.CustomerCollection;

public interface CustomerCollectionService {

	int insert(CustomerCollection record);
	
	int countByItemId(int itemId);
}
