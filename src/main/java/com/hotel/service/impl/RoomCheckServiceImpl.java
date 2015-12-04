package com.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.RoomCheckMapper;
import com.hotel.service.RoomCheckService;
@Service
public class RoomCheckServiceImpl implements RoomCheckService {

	@Autowired RoomCheckMapper roomCheckMapper;

	@Override
	public int countHotelByCustomer(Integer customerId) {
		// TODO Auto-generated method stub
		return roomCheckMapper.countHotelByCustomer(customerId);
	}
}
