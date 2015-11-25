package com.hotel.dao;

import com.hotel.model.BbsCategory;

@MyBatisRepository
public interface BbsCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsCategory record);

    int insertSelective(BbsCategory record);

    BbsCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsCategory record);

    int updateByPrimaryKey(BbsCategory record);
}