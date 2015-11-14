package com.hotel.dao;

import com.hotel.model.CustomerBrowse;

@MyBatisRepository
public interface CustomerBrowseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerBrowse record);

    int insertSelective(CustomerBrowse record);

    CustomerBrowse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerBrowse record);

    int updateByPrimaryKey(CustomerBrowse record);
}