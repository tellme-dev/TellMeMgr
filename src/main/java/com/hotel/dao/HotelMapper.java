package com.hotel.dao;

import java.util.List;

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
    
    List<Hotel> getPageHotel(Hotel hotel);
    
    int getPageHotelCount(Hotel hotel);
    
    HotelVM getHotelVMById(int id);
}
