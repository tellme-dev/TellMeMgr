package com.hotel.service;

import java.util.List;
import java.util.Map;

import com.hotel.common.ListResult;
import com.hotel.model.Hotel;
import com.hotel.model.ItemTagAssociation;
import com.hotel.modelVM.HotelParam;
import com.hotel.modelVM.HotelVM;

public interface HotelService {
	
	List<Hotel> selectHotelList();
	
    public List<Hotel> getPageHotel(Map<String, Object> map);
    
    public List<Hotel> getPageHotelByItemTag(Map<String, Object> map);
    
    public int getPageHotelCount();
    
    int getPageHotelCountByItemTag(int itemTagId);
	
    List<ItemTagAssociation> getTagTypeItem(int tagType);
    
    int insert(Hotel record);
    
    int updateByPrimaryKeySelective(Hotel record);
    
    int deleteByHotelId(Map<String, Object> idMap);
    
    Hotel selectByPrimaryKey(Integer id);
    
    HotelVM getHotelVMById(int id);

	HotelVM getHotelVMByAdId(Integer adId);
	
	List<Hotel> getPageHotelByCustomer(Map<String, Object> map);
	
	List<Hotel> selectPageHotelOrderInId(Map<String, Object> idMap);
	
	List<HotelParam> getRecommandHotelList(int num);

	List<HotelParam> fullTextSearchOfHotel(String text);

	ListResult<HotelVM> getHotelListByRegionId(int regionId);
}
