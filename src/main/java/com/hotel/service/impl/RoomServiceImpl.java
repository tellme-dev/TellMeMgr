package com.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.RoomMapper;
import com.hotel.model.Room;
import com.hotel.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	private RoomMapper roomMapper;

	@Override
	public List<Room> loadByHotelId(Integer hotelId) {
		// TODO Auto-generated method stub
		return roomMapper.selectRoomList(hotelId);
	}

}
