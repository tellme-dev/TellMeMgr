package com.hotel.dao;

import java.util.List;

import com.hotel.model.Item;

@MyBatisRepository
public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
    
    List<Item> getItemByHotel(int hotelId);
}