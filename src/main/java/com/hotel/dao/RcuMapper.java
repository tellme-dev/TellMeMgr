package com.hotel.dao;

import com.hotel.model.Rcu;

@MyBatisRepository
public interface RcuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rcu record);

    int insertSelective(Rcu record);

    Rcu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rcu record);

    int updateByPrimaryKey(Rcu record);
}