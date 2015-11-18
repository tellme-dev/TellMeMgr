package com.hotel.dao;

import com.hotel.model.CustomerCollection;

@MyBatisRepository
public interface CustomerCollectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerCollection record);

    int insertSelective(CustomerCollection record);

    CustomerCollection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerCollection record);

    int updateByPrimaryKey(CustomerCollection record);
}