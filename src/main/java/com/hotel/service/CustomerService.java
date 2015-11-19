package com.hotel.service;

import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
	int login(String loginName,String loginPassword);
}
