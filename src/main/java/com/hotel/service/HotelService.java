package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.model.Hotel;
import com.hotel.model.ItemTagAssociation;

public interface HotelService {
	
	List<Hotel> selectHotelList();
	
    public List<Hotel> getPageHotel(Map<String, Object> map);
    
    public int getPageHotelCount();
	
    List<ItemTagAssociation> getTagTypeItem(int tagType);
    
    int insert(Hotel record);
    
    int updateByPrimaryKeySelective(Hotel record);
    
    int deleteByHotelId(Map<String, Object> idMap);
    
    Hotel selectByPrimaryKey(Integer id);
}
