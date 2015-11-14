package com.hotel.dao;

import com.hotel.model.AdDetail;

@MyBatisRepository
public interface AdDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdDetail record);

    int insertSelective(AdDetail record);

    AdDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdDetail record);

    int updateByPrimaryKey(AdDetail record);
}