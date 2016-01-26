package com.hotel.service;

import java.util.List;

import com.hotel.model.Room;

public interface RoomService {

	List<Room> loadByHotelId(Integer hotelId);

}
