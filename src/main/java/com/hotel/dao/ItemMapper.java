package com.hotel.dao;

import java.util.List;
import java.util.Map;

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
    
    ItemVM selectItemVMById(int id);
    
    int addAndReturnId(Item item);
    
    List<ItemVM> selectItemVMByHotelId(int hotelId);
    
    int deleteByItemId(Map<String, Object> idMap);
}
