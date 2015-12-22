package com.hotel.dao;

import com.hotel.model.RcuCfgItem;

@MyBatisRepository
public interface RcuCfgItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RcuCfgItem record);

    int insertSelective(RcuCfgItem record);

    RcuCfgItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RcuCfgItem record);

    int updateByPrimaryKey(RcuCfgItem record);
}