package com.hotel.service;

import java.util.List;

import com.hotel.model.Hotel;

import com.hotel.model.ItemTagAssociation;
import com.hotel.modelVM.HotelVM;

public interface HotelService {
	
	List<Hotel> selectHotelList();
	
    public List<Hotel> getPageHotel(Hotel hotel);
    
    public int getPageHotelCount(Hotel hotel);
	
    List<ItemTagAssociation> getTagTypeItem(int tagType);

	HotelVM getHotelVMById(int id);
}
