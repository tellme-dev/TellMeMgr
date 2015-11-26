package com.hotel.dao;

import com.hotel.model.Bbs;

@MyBatisRepository
public interface BbsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bbs record);

    int insertSelective(Bbs record);

    Bbs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bbs record);

    int updateByPrimaryKey(Bbs record);
}