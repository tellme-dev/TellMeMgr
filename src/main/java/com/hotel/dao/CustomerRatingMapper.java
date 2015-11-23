package com.hotel.dao;

import com.hotel.model.CustomerRating;

@MyBatisRepository
public interface CustomerRatingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerRating record);

    int insertSelective(CustomerRating record);

    CustomerRating selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerRating record);

    int updateByPrimaryKey(CustomerRating record);
}