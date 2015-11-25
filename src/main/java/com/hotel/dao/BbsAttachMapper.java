package com.hotel.dao;

import com.hotel.model.BbsAttach;

@MyBatisRepository
public interface BbsAttachMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BbsAttach record);

    int insertSelective(BbsAttach record);

    BbsAttach selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BbsAttach record);

    int updateByPrimaryKey(BbsAttach record);
}