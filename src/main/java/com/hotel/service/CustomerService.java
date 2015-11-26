package com.hotel.service;

import org.springframework.stereotype.Service;

import com.hotel.model.Customer;

@Service
public interface CustomerService {
	int login(String loginName,String loginPassword);

	Customer getCustomerByMobile(String mobile);

	int insert(Customer c);
}
