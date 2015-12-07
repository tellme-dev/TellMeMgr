package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.CustomerBrowse;

public interface CustomerBrowseService {

	int insert(CustomerBrowse record);
	
	int countByItemId(int itemId);
	
	int countByCustomer(int customerId);
	
	List<CustomerBrowse> getPageByCustomer(Map<String, Object> map);
}
