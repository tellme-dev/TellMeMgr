package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.Hotel;
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
    
    int getPageHotelCount(Hotel hotel);
    
    int deleteByHotelId(Map<String, Object> idMap);

    List<Hotel> getPageHotel(Hotel hotel);
    
    HotelVM getHotelVMById(int id);
}
