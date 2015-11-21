package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.Hotel;

import com.hotel.model.ItemTagAssociation;

public interface HotelService {
	
	List<Hotel> selectHotelList();
	
    public List<Hotel> getPageHotel(Map<String, Object> map);
    
    public int getPageHotelCount(Hotel hotel);
	
    List<ItemTagAssociation> getTagTypeItem(int tagType);
    
    int insert(Hotel record);
}
