package com.hotel.dao;

import com.hotel.model.RcuCfg;

@MyBatisRepository
public interface RcuCfgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RcuCfg record);

    int insertSelective(RcuCfg record);

    RcuCfg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RcuCfg record);

    int updateByPrimaryKey(RcuCfg record);
}