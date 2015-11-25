package com.hotel.dao;

import com.hotel.model.ItemDetail;

@MyBatisRepository
public interface ItemDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDetail record);

    int insertSelective(ItemDetail record);

    ItemDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDetail record);

    int updateByPrimaryKey(ItemDetail record);
}