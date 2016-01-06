package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.Item;
import com.hotel.modelVM.ItemVM;

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
    
    List<Item> selectItemByItemTagId(int itemTagId);
    
    int countByItemTagChild(int tagId);
    
    List<Item> selectByItemTagChildOrderByScore(Map<String, Object> map);
    
    List<Item> selectItemByHotelAndTagName(Map<String, Object> map);
    
    List<Item> selectByItemTagRootAndHotel(Map<String, Object> map);

	List<ItemVM> getItemVMByChildTagId(int childTagId, int num);
}
