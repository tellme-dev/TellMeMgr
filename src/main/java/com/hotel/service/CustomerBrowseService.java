package com.hotel.service;

import com.hotel.model.CustomerBrowse;

public interface CustomerBrowseService {

	int insert(CustomerBrowse record);
	
	int countByItemId(int itemId);
	
	int countByCustomer(int customerId);
}
