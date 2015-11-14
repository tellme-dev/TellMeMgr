package com.hotel.dao;

import com.hotel.model.Org;

@MyBatisRepository
public interface OrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Org record);

    int insertSelective(Org record);

    Org selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);
}