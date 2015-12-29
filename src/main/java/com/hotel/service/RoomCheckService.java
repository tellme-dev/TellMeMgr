package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.model.RoomCheck;

@Service
public interface RoomCheckService {
	
	int countHotelByCustomer(Integer customerId);

	List<RoomCheck> loadRoomCheckListByCustomerId(int customerId);
}
