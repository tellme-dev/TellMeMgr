package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.RoomCheck;

@MyBatisRepository
public interface RoomCheckMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoomCheck record);

    int insertSelective(RoomCheck record);

    RoomCheck selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoomCheck record);

    int updateByPrimaryKey(RoomCheck record);
    
    int countHotelByCustomer(Integer customerId);

	List<RoomCheck> selectByMap(Map<String, Object> map);
}