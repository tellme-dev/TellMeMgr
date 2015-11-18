package com.hotel.service;

import java.util.List;

import com.hotel.model.Hotel;

public interface HotelService {

    public List<Hotel> getPageHotel(Hotel hotel);
    
    public int getPageHotelCount(Hotel hotel);
	
}
