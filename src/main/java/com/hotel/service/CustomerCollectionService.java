package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.CustomerCollection;

public interface CustomerCollectionService {

	int insert(CustomerCollection record);
	
	int countByItemId(int itemId);
	
	int countByCustomerCollection(CustomerCollection record);
	
	int countByCustomer(int customerId);
	
	List<CustomerCollection> getPageCollectionByCustomer(Map<String, Object> map);
	
	int deleteByItem(int targetId);
	
	int deleteById(int id);
}
