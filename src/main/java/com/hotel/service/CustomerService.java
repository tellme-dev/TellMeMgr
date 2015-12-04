package com.hotel.service;

import org.springframework.stereotype.Service;

import com.hotel.model.Customer;
import com.hotel.model.CustomerCollection;

@Service
public interface CustomerService {
	int login(String loginName,String loginPassword);

	Customer getCustomerByMobile(String mobile);
	
	Customer selectByPrimaryKey(Integer id);

	int insert(Customer c);

	void saveCollection(CustomerCollection cc);
	
	int updatePassword(Customer record);
	
	int setPassword(int id, String password);
}
