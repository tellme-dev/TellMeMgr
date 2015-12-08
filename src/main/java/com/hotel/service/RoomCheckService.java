package com.hotel.service;

import org.springframework.stereotype.Service;

@Service
public interface RoomCheckService {
	
	int countHotelByCustomer(Integer customerId);
}
