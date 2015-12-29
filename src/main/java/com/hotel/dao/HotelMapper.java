package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.Hotel;
import com.hotel.modelVM.HotelParam;
import com.hotel.modelVM.HotelVM;

@MyBatisRepository
public interface HotelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hotel record);

    int insertSelective(Hotel record);

    Hotel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hotel record);

    int updateByPrimaryKey(Hotel record);

	List<Hotel> selectHotelList();
    
    List<Hotel> getPageHotel(Map<String, Object> map);
    
    List<Hotel> getPageHotelByItemTag(Map<String, Object> map);
    
    int getPageHotelCount();
    
    int getPageHotelCountByItemTag(int itemTagId);
    
    int deleteByHotelId(Map<String, Object> idMap);
    
    List<Hotel> getPageHotel(Hotel hotel);
    
    HotelVM getHotelVMById(int id);

	HotelVM getHotelVMByMap(Map<String, Object> map);
	
	List<Hotel> getPageHotelByCustomer(Map<String, Object> map);
	
	List<Hotel> selectPageHotelOrderInId(Map<String, Object> idMap);
	
	List<HotelParam> getRecommandHotelListOfSQL(int num);
	List<HotelParam> fullTextSearchOfHotel(String text);

	int countByMap(Map<String, Object> map);

	List<HotelVM> selectHotelListByMap(Map<String, Object> map);
}
