package com.hotel.dao;

import java.util.List;
import java.util.Map;

import com.hotel.model.ItemDetail;

@MyBatisRepository
public interface ItemDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDetail record);

    int insertSelective(ItemDetail record);

    ItemDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDetail record);

    int updateByPrimaryKey(ItemDetail record);
    
    int batchInsert(List<ItemDetail> list);
    
    int deleteByItemId(Map<String, Object> idMap);
    
    List<ItemDetail> selectByItemId(Integer id);
}
