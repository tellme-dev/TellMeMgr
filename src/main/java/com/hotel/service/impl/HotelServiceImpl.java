package com.hotel.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotel.dao.HotelMapper;
import com.hotel.model.Hotel;
import com.hotel.service.HotelService;

@Service("hotelService")
public class HotelServiceImpl implements HotelService {
	
	@Resource
	private HotelMapper hotelMapper;

	@Override
	public List<Hotel> selectHotelList() {
		// TODO Auto-generated method stub
		return hotelMapper.selectHotelList();
	}

}
